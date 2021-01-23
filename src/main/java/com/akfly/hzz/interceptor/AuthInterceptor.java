package com.akfly.hzz.interceptor;  /**
 * @title: AuthInterceptor
 * @projectName hzz
 * @description 用户登录拦截器
 * @author MLL
 * @date 2021/1/21 21:53
 */

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.CustomerbaseinfoService;
import com.akfly.hzz.util.JsonUtils;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: AuthInterceptor
 * @Description: TODO
 * @Author
 * @Date 2021/1/21 21:53
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private CustomerbaseinfoService customerbaseinfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (! (handler instanceof HandlerMethod)) {
            return true;
        }
        log.info("拦截器拦截并校验客户端token={}, path={}", token, request.getServletPath());
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(VerifyToken.class)) {
            VerifyToken verifyToken = method.getAnnotation(VerifyToken.class);
            if (verifyToken.required()) {
                if (StringUtils.isBlank(token)) {
                    BaseRspDto rsp = new BaseRspDto();
                    rsp.setCode(HzzExceptionEnum.USER_NOT_LOGIN.getErrorCode());
                    rsp.setMsg(HzzExceptionEnum.USER_NOT_LOGIN.getErrorMsg());
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JsonUtils.toJson(rsp));
                    return false;
                }
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    log.error("token解码失败", e);
                    return false;
                }
                CustomerbaseinfoVo vo = customerbaseinfoService.getUserInfoById(userId);
                log.info("拦截器获取到用户信息 vo={}", JsonUtils.toJson(vo));
                request.setAttribute(CommonConstant.USER_INFO, vo);
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(vo.getCbiPassword())).build();
                jwtVerifier.verify(token);
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        log.error("111111111");
        if (request.getAttribute(CommonConstant.USER_INFO) == null) {
            log.error("33333333333");
            //BaseRspDto rsp = new BaseRspDto();
            //rsp.setCode(HzzExceptionEnum.USER_NOT_LOGIN.getErrorCode());
            //rsp.setMsg(HzzExceptionEnum.USER_NOT_LOGIN.getErrorMsg());
            throw new HzzBizException(HzzExceptionEnum.USER_NOT_LOGIN);
        }
    }

}
