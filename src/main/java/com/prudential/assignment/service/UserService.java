package com.prudential.assignment.service;

import com.prudential.assignment.common.model.request.LoginRequest;
import com.prudential.assignment.common.model.request.RegisterRequest;
import com.prudential.assignment.common.model.vo.LoginVO;
import com.prudential.assignment.common.model.vo.UserVO;

import java.util.List;

public interface UserService {

    void register(RegisterRequest registerRequest);

    LoginVO login(LoginRequest loginRequest);

    List<UserVO> list();

}
