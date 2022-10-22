package com.prudential.assignment.controller;

import com.prudential.assignment.common.model.request.LoginRequest;
import com.prudential.assignment.common.model.request.RegisterRequest;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.vo.LoginVO;
import com.prudential.assignment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(tags = "Api——用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Response<Void> register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.register(registerRequest);
        return Response.success();
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Response<LoginVO> login(@RequestBody @Valid LoginRequest loginRequest) {
        return Response.success(userService.login(loginRequest));
    }

}
