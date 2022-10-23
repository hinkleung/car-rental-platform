package com.prudential.assignment.common.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("注册请求体")
public class RegisterRequest {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "username can't be null and empty")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "password can't be null and empty")
    private String password;

    @ApiModelProperty("昵称")
    private String nickName;


}
