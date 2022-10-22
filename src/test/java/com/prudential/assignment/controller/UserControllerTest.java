package com.prudential.assignment.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prudential.assignment.common.constants.Constants;
import com.prudential.assignment.common.exception.BusinessException;
import com.prudential.assignment.common.model.request.LoginRequest;
import com.prudential.assignment.common.model.request.RegisterRequest;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.response.ResponseCode;
import com.prudential.assignment.common.model.vo.LoginVO;
import com.prudential.assignment.common.utils.JWTUtils;
import com.prudential.assignment.common.utils.PasswordUtil;
import com.prudential.assignment.repository.dao.UserDao;
import com.prudential.assignment.repository.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserController userController;
    @Autowired
    private UserDao userDao;


    @Autowired
    private MockMvc mockMvc;


    @Test
    @Rollback()
    @Transactional()
    void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("chen" + System.currentTimeMillis());
        registerRequest.setPassword("chen5678");
        registerRequest.setNickName(registerRequest.getUsername());
        Response<Void> response = userController.register(registerRequest);
        Assert.assertTrue(response.ifSuccess());

        User user = userDao.selectByUsername(registerRequest.getUsername());
        Assert.assertNotNull(user);

        boolean verifyPassword = PasswordUtil.verifyPassword(registerRequest.getPassword(), user.getPassword(), user.getSalt());
        Assert.assertTrue(verifyPassword);
    }

    @Test
    void registerExistUsername() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("chen1");
        registerRequest.setPassword("chen5678");
        registerRequest.setNickName(registerRequest.getUsername());
//        Response<Void> response = userController.register(registerRequest);
//        Assert.assertFalse(response.isSuccess());
//        Assert.assertEquals(ResponseCode.USERNAME_IS_EXIST.getCode(), response.getCode());

        String requestJson = objectMapper.writeValueAsString(registerRequest);

        Response<Void> response = Response.error(ResponseCode.USERNAME_IS_EXIST);
        String resultJson = objectMapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .content(requestJson) //传json参数
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultJson))
                .andDo(print());
    }

    @Test
    @Rollback()
    @Transactional()
    void login() {
        // register
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("chen" + System.currentTimeMillis());
        registerRequest.setPassword("chen5678");
        registerRequest.setNickName(registerRequest.getUsername());
        Response<Void> response = userController.register(registerRequest);
        Assert.assertTrue(response.ifSuccess());
        User user = userDao.selectByUsername(registerRequest.getUsername());
        Assert.assertNotNull(user);

        // login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword(registerRequest.getPassword());
        Response<LoginVO> loginResponse = userController.login(loginRequest);

        // assert response
        Assert.assertTrue(loginResponse.ifSuccess());
        Assert.assertNotNull(loginResponse.getData());

        LoginVO data = loginResponse.getData();
        Assert.assertEquals(data.getUserId(), user.getId());

        // verify jwt
        String token = data.getToken();
        Assert.assertTrue(JWTUtils.verify(token));

        // decode jwt
        DecodedJWT decode = JWTUtils.decode(token);
        Long userId = decode.getClaim(Constants.REQUEST_CONTENT_USER_ID).asLong();
        String username = decode.getClaim(Constants.REQUEST_CONTENT_USERNAME).asString();
        Assert.assertEquals(userId, user.getId());
        Assert.assertEquals(username, user.getUsername());
    }

    @Test
    void loginWithErrorUsername() {
        // login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("errorUsername");
        loginRequest.setPassword("123");
        Assert.assertThrows(BusinessException.class, () -> {
            userController.login(loginRequest);
        });
    }

    @Test
    @Transactional
    void loginWithErrorPassword() {
        // register
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("chen" + System.currentTimeMillis());
        registerRequest.setPassword("chen5678");
        registerRequest.setNickName(registerRequest.getUsername());
        userController.register(registerRequest);

        // login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest.getUsername());
        loginRequest.setPassword(registerRequest.getPassword() + "123");
        Assert.assertThrows(BusinessException.class, () -> {
            userController.login(loginRequest);
        });
    }




}