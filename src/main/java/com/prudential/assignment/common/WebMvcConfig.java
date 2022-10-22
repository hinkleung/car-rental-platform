package com.prudential.assignment.common;

import com.prudential.assignment.common.interceptor.UserTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // register the userTokenInterceptor
        registry.addInterceptor(new UserTokenInterceptor())
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/bg/**")
                // static resources
                .excludePathPatterns("/**/*.html",
                        "/**/*.js",
                        "/**/*.css")
                // swagger
                .excludePathPatterns("/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs",
                        "/favicon.ico",
                        "/error");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
