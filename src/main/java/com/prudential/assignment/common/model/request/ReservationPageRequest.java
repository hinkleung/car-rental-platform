package com.prudential.assignment.common.model.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("预定记录分页请求")
public class ReservationPageRequest extends PageParam {

    @ApiModelProperty(value = "车型id", required = false)
    private Long carId;

    @ApiModelProperty(value = "开始时间 格式yyyy-MM-dd HH:mm:ss", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间 格式yyyy-MM-dd HH:mm:ss", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
