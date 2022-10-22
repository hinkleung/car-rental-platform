package com.prudential.assignment.service;

import com.prudential.assignment.common.model.request.ReservationPageRequest;
import com.prudential.assignment.common.model.request.ReserveCarRequest;
import com.prudential.assignment.common.model.request.ReturnCarRequest;
import com.prudential.assignment.common.model.response.PageResponse;
import com.prudential.assignment.common.model.vo.CarVO;
import com.prudential.assignment.common.model.vo.MyReservationVO;
import com.prudential.assignment.common.model.vo.ReservationVO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    List<CarVO> getReservableCars(LocalDateTime startTime, LocalDateTime endTime);

    void reserveCar(ReserveCarRequest reserveCarRequest);

    PageResponse<List<MyReservationVO>> getMyReservationPage(ReservationPageRequest request);

    void returnCar(ReturnCarRequest returnCarRequest);

    PageResponse<List<ReservationVO>> page(ReservationPageRequest request);
}
