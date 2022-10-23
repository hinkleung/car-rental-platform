package com.prudential.assignment.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("更新车型请求")
public class CarUpdateRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("车型名称")
    @NotBlank(message = "车型名称不能为空")
    private String carModel;

    @ApiModelProperty("库存")
    @NotNull(message = "库存不能为空")
    private Integer stock;

}
