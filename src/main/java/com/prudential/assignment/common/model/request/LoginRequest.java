package com.prudential.assignment.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("登录请求体")
public class LoginRequest {

    @ApiModelProperty("账号名")
    @NotBlank(message = "username can't be null and empty")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "password can't be null and empty")
    private String password;

}
