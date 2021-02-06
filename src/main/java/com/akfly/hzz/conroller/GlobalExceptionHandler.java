package com.akfly.hzz.conroller;

import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzExceptionEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value= {MissingServletRequestParameterException.class,MethodArgumentNotValidException.class, BindException.class,IllegalArgumentException.class, ConstraintViolationException.class})
    @ResponseBody
    public BaseRspDto<String> errorHandler(Exception e){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        rsp.setCode(HzzExceptionEnum.PARAM_INVALID.getErrorCode());
        rsp.setMsg(HzzExceptionEnum.PARAM_INVALID.getErrorMsg());
        return rsp;
    }
}
