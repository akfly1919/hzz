package com.akfly.hzz.config;

import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzExceptionEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value= {MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public BaseRspDto<String> errorHandler(Exception e){
        BaseRspDto<String> rsp = new BaseRspDto<String>();
        rsp.setCode(HzzExceptionEnum.PARAM_INVALID.getErrorCode());
        rsp.setMsg(HzzExceptionEnum.PARAM_INVALID.getErrorMsg());
        return rsp;
    }
}
