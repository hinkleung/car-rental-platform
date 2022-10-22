package com.prudential.assignment.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("新增车型请求")
public class CarAddRequest {

    @ApiModelProperty("车型")
    @NotBlank(message = "车型不能为空")
    private String carModel;

    @ApiModelProperty("库存")
    @NotNull(message = "库存不能为空")
    private Integer stock;

}
