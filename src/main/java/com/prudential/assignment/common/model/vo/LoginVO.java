package com.prudential.assignment.common.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录VO")
public class LoginVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("登录认证token")
    private String token;

}
