package com.prudential.assignment.controller.bg;

import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.vo.UserVO;
import com.prudential.assignment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "后台——用户管理")
@RestController
@RequestMapping("/bg/user")
public class BgUserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查询用户列表")
    @GetMapping("/list")
    public Response<List<UserVO>> list() {
        return Response.success(userService.list());
    }

}
