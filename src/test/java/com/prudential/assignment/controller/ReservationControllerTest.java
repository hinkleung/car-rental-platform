package com.prudential.assignment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.prudential.assignment.common.RequestContext;
import com.prudential.assignment.common.constants.Constants;
import com.prudential.assignment.common.model.request.ReservationPageRequest;
import com.prudential.assignment.common.model.request.ReserveCarRequest;
import com.prudential.assignment.common.model.request.ReturnCarRequest;
import com.prudential.assignment.common.model.response.PageResponse;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.vo.CarVO;
import com.prudential.assignment.common.model.vo.MyReservationVO;
import com.prudential.assignment.enums.ReservationStatus;
import com.prudential.assignment.repository.dao.CarDao;
import com.prudential.assignment.repository.entity.Car;
import com.prudential.assignment.repository.entity.Reservation;
import com.prudential.assignment.repository.mapper.CarMapper;
import com.prudential.assignment.repository.mapper.ReservationMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ReservationControllerTest {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CarDao carDao;
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private ReservationController reservationController;

    @Before
    void setUp() {
        // init user context
        RequestContext.setRequestContent(Constants.REQUEST_CONTENT_USER_ID, "1");
        RequestContext.setRequestContent(Constants.REQUEST_CONTENT_USERNAME, "chenqingliang");
    }

    @Test
    void getReservableCars() {
        // 无交集
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextYearStart = now.plusYears(1);
        LocalDateTime nextYearEnd = now.plusYears(1).plusHours(2);
        Response<List<CarVO>> response = reservationController.getReservableCars(nextYearStart, nextYearEnd);
        Assert.assertTrue(response.ifSuccess());
        Assert.assertFalse(CollectionUtils.isEmpty(response.getData()));

        List<CarVO> carVos = response.getData();

        List<Car> cars = carDao.selectAll();
        Assert.assertEquals(carVos.size(), cars.size());

        Map<Long, CarVO> voMap = carVos.stream().collect(Collectors.toMap(CarVO::getId, i -> i));
        for (Car car : cars) {
            CarVO carVO = voMap.get(car.getId());
            Assert.assertNotNull(carVO);
            Assert.assertEquals(car.getCarModel(), carVO.getCarModel());
            Assert.assertEquals(car.getStock(), carVO.getStock());
        }
    }

    @Test
    @Transactional
    void getReservableCarsWithIntersection() {
        // 无交集
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextYearStart = now.plusYears(1);
        LocalDateTime nextYearEnd = now.plusYears(1).plusHours(2);
        Response<List<CarVO>> response = reservationController.getReservableCars(nextYearStart, nextYearEnd);
        Assert.assertTrue(response.ifSuccess());
        Assert.assertFalse(CollectionUtils.isEmpty(response.getData()));

        List<CarVO> carVos = response.getData();
        Map<Long, CarVO> voMap = carVos.stream().collect(Collectors.toMap(CarVO::getId, i -> i));

        // 制造交集
        ReserveCarRequest reserveCarRequest = new ReserveCarRequest();
        reserveCarRequest.setCarId(2L);
        reserveCarRequest.setStartTime(nextYearStart.plusHours(1));
        reserveCarRequest.setEndTime(nextYearEnd);
        Response<Void> reserveCarRes = reservationController.reserveCar(reserveCarRequest);
        Assert.assertTrue(reserveCarRes.ifSuccess());

        // 比较结果
        Response<List<CarVO>> reservableCarsRes = reservationController.getReservableCars(nextYearStart, nextYearEnd);
        List<CarVO> data = reservableCarsRes.getData();
        Map<Long, CarVO> voMap2 = data.stream().collect(Collectors.toMap(CarVO::getId, i -> i));

        Integer previousNum = voMap.get(2L).getStock();
        Integer curNum = voMap2.get(2L).getStock();
        Assert.assertEquals((int) curNum, previousNum - 1);
    }

    @Test
    @Transactional
    void reserve() {
        ReserveCarRequest reserveCarRequest = new ReserveCarRequest();
        reserveCarRequest.setCarId(2L);
        LocalDateTime now = LocalDateTime.now();
        reserveCarRequest.setStartTime(now);
        reserveCarRequest.setEndTime(now.plusHours(4));
        Response<Void> response = reservationController.reserveCar(reserveCarRequest);
        Assert.assertTrue(response.ifSuccess());

        ReservationPageRequest reservationPageRequest = new ReservationPageRequest();
        reservationPageRequest.setCarId(2L);
        reservationPageRequest.setCurrentPage(1);
        reservationPageRequest.setPageSize(10);
        reservationPageRequest.setStartTime(now);
        reservationPageRequest.setEndTime(now.plusHours(5));
        PageResponse<List<MyReservationVO>> myReservations = reservationController.getMyReservations(reservationPageRequest);
        Assert.assertTrue(myReservations.ifSuccess());

        List<MyReservationVO> data = myReservations.getData();
        Assert.assertFalse(data.isEmpty());
    }

    @Test
    @Transactional
    void returnCar() {
        ReserveCarRequest reserveCarRequest = new ReserveCarRequest();
        reserveCarRequest.setCarId(2L);
        LocalDateTime now = LocalDateTime.now();
        reserveCarRequest.setStartTime(now);
        reserveCarRequest.setEndTime(now.plusHours(4));
        Response<Void> response = reservationController.reserveCar(reserveCarRequest);
        Assert.assertTrue(response.ifSuccess());

        // 获取最近一条
        LambdaQueryWrapper<Reservation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Reservation::getUserId, RequestContext.getUserId())
                .orderByDesc(Reservation::getCreateTime)
                .last("limit 1");
        Reservation reservation = reservationMapper.selectOne(queryWrapper);
        Assert.assertNotNull(reservation);
        Assert.assertEquals(reservation.getStatus(), ReservationStatus.CREATED.name());

        // 执行归还
        ReturnCarRequest returnCarRequest = new ReturnCarRequest();
        returnCarRequest.setReservationId(reservation.getId());
        Response<Void> returnCarResponse = reservationController.returnCar(returnCarRequest);
        Assert.assertTrue(returnCarResponse.ifSuccess());

        Reservation selectById = reservationMapper.selectById(reservation.getId());
        Assert.assertEquals(selectById.getStatus(), ReservationStatus.FINISHED.name());
    }
}