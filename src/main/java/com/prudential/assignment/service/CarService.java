package com.prudential.assignment.service;

import com.prudential.assignment.common.model.request.CarAddRequest;
import com.prudential.assignment.common.model.request.CarUpdateRequest;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.vo.CarVO;

import java.util.List;

public interface CarService {
    Response<List<CarVO>> list();

    void add(CarAddRequest request);

    void update(CarUpdateRequest request);

    void delete(Long id);
}
