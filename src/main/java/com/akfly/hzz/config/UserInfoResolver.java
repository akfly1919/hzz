package com.akfly.hzz.config;

import com.akfly.hzz.annotation.VerifyToken;
import com.akfly.hzz.constant.CommonConstant;
import com.akfly.hzz.vo.CustomerbaseinfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


@Slf4j
public class UserInfoResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(VerifyToken.class) != null && parameter.getParameterType() == CustomerbaseinfoVo.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        log.info("MerchantArgumentResolver init...");
        CustomerbaseinfoVo vo = (CustomerbaseinfoVo) request.getAttribute(CommonConstant.USER_INFO);
        if (vo != null) {
            return vo;
        }
        return null;
    }
}
