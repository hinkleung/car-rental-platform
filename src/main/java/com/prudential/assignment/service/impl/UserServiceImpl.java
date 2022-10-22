package com.prudential.assignment.service.impl;

import com.prudential.assignment.common.exception.BusinessException;
import com.prudential.assignment.common.model.request.LoginRequest;
import com.prudential.assignment.common.model.request.RegisterRequest;
import com.prudential.assignment.common.model.response.ResponseCode;
import com.prudential.assignment.common.model.vo.LoginVO;
import com.prudential.assignment.common.model.vo.UserVO;
import com.prudential.assignment.common.utils.JWTUtils;
import com.prudential.assignment.common.utils.PasswordUtil;
import com.prudential.assignment.repository.dao.UserDao;
import com.prudential.assignment.repository.entity.User;
import com.prudential.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterRequest registerRequest) {
        // encode password
        String salt = PasswordUtil.generateRandomSalt();
        String encodePassword = PasswordUtil.encodePassword(registerRequest.getPassword(), salt);
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(encodePassword);
        user.setNickName(registerRequest.getNickName());
        user.setSalt(salt);
        try {
            // username is a unique key
            userDao.insert(user);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ResponseCode.USERNAME_IS_EXIST);
        }
    }

    @Override
    public LoginVO login(LoginRequest loginRequest) {
        User user = userDao.selectByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new BusinessException(ResponseCode.USERNAME_IS_NOT_EXIST);
        }
        // verify password
        boolean verifyPassword = PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPassword(), user.getSalt());
        if (!verifyPassword) {
            throw new BusinessException(ResponseCode.PASSWORD_ERROR);
        }
        String jwt = JWTUtils.generate(user.getUsername(), user.getId());
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setToken(jwt);
        return loginVO;
    }

    @Override
    public List<UserVO> list() {
        ZoneId zoneId = ZoneId.systemDefault();
        List<User> users = userDao.selectAll();
        List<UserVO> resultList = new ArrayList<>();
        for (User user : users) {
            UserVO userVO = new UserVO();
            userVO.setUserId(user.getId());
            userVO.setUsername(user.getUsername());
            userVO.setNickName(user.getNickName());
            LocalDateTime registerTime = Instant.ofEpochMilli(user.getCreateTime()).atZone(zoneId).toLocalDateTime();
            userVO.setRegisterTime(registerTime);
            resultList.add(userVO);
        }
        return resultList;
    }

}
