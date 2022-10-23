package com.prudential.assignment.service.impl;

import java.util.ArrayList;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.prudential.assignment.common.BaseEntity;
import com.prudential.assignment.common.RequestContext;
import com.prudential.assignment.common.exception.BusinessException;
import com.prudential.assignment.common.model.request.ReservationPageRequest;
import com.prudential.assignment.common.model.request.ReserveCarRequest;
import com.prudential.assignment.common.model.request.ReturnCarRequest;
import com.prudential.assignment.common.model.response.PageResponse;
import com.prudential.assignment.common.model.response.ResponseCode;
import com.prudential.assignment.common.model.vo.CarVO;
import com.prudential.assignment.common.model.vo.MyReservationVO;
import com.prudential.assignment.common.model.vo.ReservationVO;
import com.prudential.assignment.enums.ReservationStatus;
import com.prudential.assignment.repository.dao.CarDao;
import com.prudential.assignment.repository.dao.ReservationDao;
import com.prudential.assignment.repository.dao.UserDao;
import com.prudential.assignment.repository.entity.Car;
import com.prudential.assignment.repository.entity.Reservation;
import com.prudential.assignment.repository.entity.User;
import com.prudential.assignment.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private CarDao carDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<CarVO> getReservableCars(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.compareTo(LocalDateTime.now()) < 0) {
            throw new BusinessException(ResponseCode.INVALID_PARAM, "endTime is expired");
        }
        List<Car> cars = carDao.selectAll();
        if (CollectionUtils.isEmpty(cars)) {
            return Collections.emptyList();
        }

        // 让订单跟参数时间作交集，将库存减掉
        List<Reservation> reservations = reservationDao.selectIntersectionByTime(startTime, endTime);
        if (CollectionUtils.isEmpty(reservations)) {
            return this.convertFromCars(cars).stream().filter(c -> c.getStock() > 0).collect(Collectors.toList());
        }

        // group and sum
        Map<Long, Long> carNumMap = reservations.stream().collect(Collectors.groupingBy(Reservation::getCarId, Collectors.counting()));

        List<CarVO> carVOList = new ArrayList<>();
        for (Car car : cars) {
            if (car.getStock() > 0) {
                int num = car.getStock() - carNumMap.getOrDefault(car.getId(), 0L).intValue();
                if (num > 0) {
                    CarVO carVO = convertFromCar(car);
                    carVO.setStock(num);
                    carVOList.add(carVO);
                }
            }
        }
        return carVOList;
    }

    @Override
    public void reserveCar(ReserveCarRequest reserveCarRequest) {
        LocalDateTime startTime = reserveCarRequest.getStartTime();
        LocalDateTime endTime = reserveCarRequest.getEndTime();
        Long userId = RequestContext.getUserId();
        Long carId = reserveCarRequest.getCarId();

        // check time
        if (endTime.compareTo(LocalDateTime.now()) < 0) {
            throw new BusinessException(ResponseCode.INVALID_PARAM, "endTime is expired");
        }

        Car car = carDao.selectById(carId);
        if (car == null) {
            throw new BusinessException(ResponseCode.INVALID_PARAM, "this car is not exist");
        }

        // check car stock
        int usedNum = reservationDao.selectCountByTimeAndCarId(startTime, endTime, carId);
        if (car.getStock() - usedNum <= 0) {
            throw new BusinessException(ResponseCode.CAR_STOCK_IS_NOT_ENOUGH_FOR_APPLY);
        }

        Reservation reservation = new Reservation();
        reservation.setCarId(carId);
        reservation.setUserId(userId);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus(ReservationStatus.CREATED.name());
        reservationDao.insert(reservation);
    }

    @Override
    public PageResponse<List<MyReservationVO>> getMyReservationPage(ReservationPageRequest request) {
        IPage<Reservation> page = reservationDao.selectPage(request, RequestContext.getUserId());
        if (page.getTotal() == 0 || CollectionUtils.isEmpty(page.getRecords())) {
            return PageResponse.success(new ArrayList<>(), 0);
        }
        List<Reservation> records = page.getRecords();
        List<Long> carIds = records.stream().map(Reservation::getCarId).distinct().collect(Collectors.toList());
        List<Car> cars;
        if (!CollectionUtils.isEmpty(carIds)) {
            cars = carDao.selectByIds(carIds);
        } else {
            cars = new ArrayList<>();
        }
        Map<Long, String> carMap = cars.stream().collect(Collectors.toMap(BaseEntity::getId, Car::getCarModel, (a, b) -> a));

        List<MyReservationVO> resultVOList = new ArrayList<>();
        for (Reservation record : records) {
            MyReservationVO vo = convertFromReservation(record);
            vo.setCarModel(carMap.getOrDefault(record.getCarId(), null));
            resultVOList.add(vo);
        }
        return PageResponse.success(resultVOList, page.getTotal());
    }

    @Override
    public PageResponse<List<ReservationVO>> page(ReservationPageRequest request) {
        IPage<Reservation> page = reservationDao.selectPage(request, null);
        if (page.getTotal() == 0 || CollectionUtils.isEmpty(page.getRecords())) {
            return PageResponse.success(new ArrayList<>(), 0);
        }
        List<Reservation> records = page.getRecords();
        List<Long> carIds = records.stream().map(Reservation::getCarId).distinct().collect(Collectors.toList());
        List<Car> cars;
        if (!CollectionUtils.isEmpty(carIds)) {
            cars = carDao.selectByIds(carIds);
        } else {
            cars = new ArrayList<>();
        }
        Map<Long, Car> carMap = cars.stream().collect(Collectors.toMap(BaseEntity::getId, i -> i, (a, b) -> a));

        List<Long> userIds = records.stream().map(Reservation::getUserId).distinct().collect(Collectors.toList());
        List<User> users;
        if (!CollectionUtils.isEmpty(userIds)) {
            users = userDao.selectByIds(userIds);
        } else {
            users = new ArrayList<>();
        }
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(BaseEntity::getId, i -> i, (a, b) -> a));

        List<ReservationVO> resultVOList = new ArrayList<>();
        for (Reservation record : records) {
            ReservationVO vo = new ReservationVO();
            vo.setId(record.getId());
            vo.setStartTime(record.getStartTime());
            vo.setEndTime(record.getEndTime());
            vo.setReturnTime(record.getReturnTime());
            vo.setStatus(record.getStatus());

            vo.setCarId(record.getCarId());
            if (carMap.containsKey(record.getCarId())) {
                vo.setCarModel(carMap.get(record.getCarId()).getCarModel());
            }

            vo.setUserId(record.getUserId());
            if (userMap.containsKey(record.getUserId())) {
                User user = userMap.get(record.getUserId());
                vo.setUsername(user.getUsername());
                vo.setNickName(user.getNickName());
            }
            resultVOList.add(vo);
        }
        return PageResponse.success(resultVOList, page.getTotal());
    }

    @Override
    public void returnCar(ReturnCarRequest returnCarRequest) {
        Long reservationId = returnCarRequest.getReservationId();
        Reservation reservation = reservationDao.selectById(reservationId);
        if (reservation == null) {
            throw new BusinessException(ResponseCode.INVALID_PARAM, "reservation record is not exist");
        }
        if (!RequestContext.getUserId().equals(reservation.getUserId())) {
            throw new BusinessException(ResponseCode.FORBIDDEN);
        }

        Reservation updateEntity = new Reservation();
        updateEntity.setStatus(ReservationStatus.FINISHED.name());
        updateEntity.setReturnTime(LocalDateTime.now());
        updateEntity.setId(reservationId);
        reservationDao.update(updateEntity);
    }

    private MyReservationVO convertFromReservation(Reservation reservation) {
        MyReservationVO reservationVO = new MyReservationVO();
        reservationVO.setId(reservation.getId());
//        reservationVO.setCarModel();
        reservationVO.setStartTime(reservation.getStartTime());
        reservationVO.setEndTime(reservation.getEndTime());
        reservationVO.setReturnTime(reservation.getReturnTime());
        reservationVO.setStatus(reservation.getStatus());
        return reservationVO;
    }

    private List<CarVO> convertFromCars(List<Car> cars) {
        List<CarVO> carVOList = new ArrayList<>();
        for (Car car : cars) {
            carVOList.add(convertFromCar(car));
        }
        return carVOList;
    }

    private CarVO convertFromCar(Car car) {
        CarVO carVO = new CarVO();
        carVO.setId(car.getId());
        carVO.setCarModel(car.getCarModel());
        carVO.setStock(car.getStock());
        return carVO;
    }


}
