package com.prudential.assignment.service.impl;

import com.prudential.assignment.common.model.request.CarAddRequest;
import com.prudential.assignment.common.model.request.CarUpdateRequest;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.vo.CarVO;
import com.prudential.assignment.repository.dao.CarDao;
import com.prudential.assignment.repository.entity.Car;
import com.prudential.assignment.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDao carDao;

    @Override
    public Response<List<CarVO>> list() {
        List<Car> cars = carDao.selectAll();
        return Response.success(convertFromCars(cars));
    }

    @Override
    public void add(CarAddRequest request) {
        Car car = new Car();
        car.setCarModel(request.getCarModel());
        car.setStock(request.getStock());
        carDao.insert(car);
    }

    @Override
    public void update(CarUpdateRequest request) {
        Car car = new Car();
        car.setId(request.getId());
        car.setCarModel(request.getCarModel());
        car.setStock(request.getStock());
        carDao.update(car);
    }

    @Override
    public void delete(Long id) {
        carDao.deleteById(id);
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
