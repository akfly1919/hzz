package com.akfly.hzz.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: LoggedIn
 * @Description: TODO
 * @Author
 * @Date 2021/1/21 21:50
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggedIn {

    boolean required() default true;
}
