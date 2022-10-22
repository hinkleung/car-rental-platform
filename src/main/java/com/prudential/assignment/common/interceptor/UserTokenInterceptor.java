package com.prudential.assignment.common.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.prudential.assignment.common.RequestContext;
import com.prudential.assignment.common.constants.Constants;
import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.response.ResponseCode;
import com.prudential.assignment.common.utils.JWTUtils;
import com.prudential.assignment.common.utils.JSONUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class UserTokenInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求，在访问controller调用之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("进入到拦截器，被拦截。。。");
//        System.out.println(request.getRequestURL().toString());

        String token = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (StringUtils.isEmpty(token)) {
            returnErrorResponse(response, Response.error(ResponseCode.UNAUTHORIZED));
            return false;
        }
        if (token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }

        // verify the token
        boolean verify = JWTUtils.verify(token);
        if (!verify) {
            returnErrorResponse(response, Response.error(ResponseCode.UNAUTHORIZED));
        }

        // decode token and set user context
        DecodedJWT decodedJWT = JWTUtils.decode(token);
        Claim claim = decodedJWT.getClaim(Constants.REQUEST_CONTENT_USER_ID);
        Long userId = claim.asLong();
        RequestContext.setRequestContent(Constants.REQUEST_CONTENT_USER_ID, userId.toString());
        return true;
    }

    public void returnErrorResponse(HttpServletResponse response,
                                    Response result) {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JSONUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 请求访问controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContext.resetRequestContent();
//        System.out.println("拦截器完成，消除请求上下文");
    }
}
