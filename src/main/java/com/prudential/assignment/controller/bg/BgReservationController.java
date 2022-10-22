package com.prudential.assignment.controller.bg;

import com.prudential.assignment.common.model.request.ReservationPageRequest;
import com.prudential.assignment.common.model.response.PageResponse;
import com.prudential.assignment.common.model.vo.ReservationVO;
import com.prudential.assignment.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "后台——租车订单管理")
@RestController
@RequestMapping("/bg/reservation")
public class BgReservationController {

    @Autowired
    private ReservationService reservationService;

    @ApiOperation("列表")
    @GetMapping("/list")
    public PageResponse<List<ReservationVO>> list(ReservationPageRequest request) {
        return reservationService.page(request);
    }

}
