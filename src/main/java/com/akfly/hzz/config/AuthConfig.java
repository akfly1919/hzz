package com.akfly.hzz.config;

import com.akfly.hzz.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author MLL
 * @title: AuthConfig
 * @projectName hzz
 * @description 登录校验拦截器配置类
 * @date 2021/1/22 11:15
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration registration = registry.addInterceptor(new AuthInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/hzz/user/login",
                "/hzz/user/logout",
                "/hzz/user/sendMsgCode",
                "/hzz/user/register"
        );
    }

}