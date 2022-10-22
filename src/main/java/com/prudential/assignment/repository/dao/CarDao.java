package com.prudential.assignment.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.prudential.assignment.repository.entity.Car;
import com.prudential.assignment.repository.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDao {

    @Autowired
    private CarMapper carMapper;

    public List<Car> selectAll() {
        return carMapper.selectList(Wrappers.lambdaQuery());
    }

    public Car selectById(Long id) {
        return carMapper.selectById(id);
    }

    public List<Car> selectByIds(List<Long> carIds) {
        LambdaQueryWrapper<Car> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Car::getId, carIds);
        return carMapper.selectList(queryWrapper);
    }

    public int insert(Car car) {
        return carMapper.insert(car);
    }

    public int update(Car car) {
        return carMapper.updateById(car);
    }

    public int deleteById(Long id) {
        return carMapper.deleteById(id);
    }

}
