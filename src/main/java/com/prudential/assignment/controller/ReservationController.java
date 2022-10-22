package com.prudential.assignment.controller;

import com.prudential.assignment.common.model.request.ReservationPageRequest;
import com.prudential.assignment.common.model.request.ReserveCarRequest;
import com.prudential.assignment.common.model.request.ReturnCarRequest;
import com.prudential.assignment.common.model.response.PageResponse;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.vo.CarVO;
import com.prudential.assignment.common.model.vo.MyReservationVO;
import com.prudential.assignment.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "Api——租车接口")
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @ApiOperation("查询可预订的车型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true)
    })
    @GetMapping("/getReservableCars")
    public Response<List<CarVO>> getReservableCars(@RequestParam("startTime")
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   LocalDateTime startTime,
                                                   @RequestParam("endTime")
                                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                   LocalDateTime endTime) {

        return Response.success(reservationService.getReservableCars(startTime, endTime));
    }

    @ApiOperation("查询我的预定记录（分页）")
    @GetMapping("/getMyReservations")
    public PageResponse<List<MyReservationVO>> getMyReservations(ReservationPageRequest request) {
        return reservationService.getMyReservationPage(request);
    }

    @ApiOperation("预定")
    @PostMapping("/reserveCar")
    public Response<Void> reserveCar(@RequestBody @Valid ReserveCarRequest reserveCarRequest) {
        reservationService.reserveCar(reserveCarRequest);
        return Response.success();
    }

    @ApiOperation("还车")
    @PostMapping("/returnCar")
    public Response<Void> returnCar(@RequestBody @Valid ReturnCarRequest returnCarRequest) {
        reservationService.returnCar(returnCarRequest);
        return Response.success();
    }


}
