package com.akfly.hzz.annotation;  /**
 * @title: VerifyToken
 * @projectName hzz
 * @description token校验注解
 * @author MLL
 * @date 2021/1/21 21:50
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: VerifyToken
 * @Description: TODO
 * @Author
 * @Date 2021/1/21 21:50
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyToken {

    boolean required() default true;
}
