package com.prudential.assignment.common.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("车型VO")
public class CarVO {

    @ApiModelProperty("车型id")
    private Long id;

    @ApiModelProperty("车型")
    private String carModel;

    @ApiModelProperty("车型剩余库存")
    private Integer stock;

}
