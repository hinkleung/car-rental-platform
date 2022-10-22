package com.prudential.assignment.common.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ApiModel("预订车辆请求")
public class ReserveCarRequest {

    @ApiModelProperty("车型id")
    @NotNull(message = "未选择车型")
    private Long carId;

    @ApiModelProperty("开始时间 格式yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "预定开始时间不能为空")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间 格式yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "预定结束时间不能为空")
    private LocalDateTime endTime;

}
