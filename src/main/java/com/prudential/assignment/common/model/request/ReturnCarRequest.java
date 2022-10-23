package com.prudential.assignment.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("归还车辆请求")
public class ReturnCarRequest {

    @ApiModelProperty(value = "预定记录id", required = true)
    @NotNull(message = "未选中预定记录")
    private Long reservationId;

}
