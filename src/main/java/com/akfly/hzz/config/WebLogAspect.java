package com.akfly.hzz.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    ThreadLocal<LogDto> loginfo = new ThreadLocal<>();
    /**
     * 指定 controller 包下的注解
     * */
    @Pointcut("execution( * com.akfly.hzz.conroller.*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {

    }
    /**
     * 指定当前执行方法在logPointCut之前执行
     * */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        // 接收到请求，记录请求内容
        try{
            if("com.akfly.hzz.conroller.GlobalExceptionHandler".equalsIgnoreCase(joinPoint.getSignature().getDeclaringTypeName())){
                return;
            }
        LogDto logInfo=new LogDto();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logInfo.setRequestTime(LocalDateTime.now().toString());
        logInfo.setRequestUrl(request.getRequestURL().toString());
        logInfo.setRequestMethod(request.getMethod());
        logInfo.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logInfo.setArgs(Arrays.toString(joinPoint.getArgs()));

        loginfo.set(logInfo);
        }catch (Throwable e){

        }
    }
    /**
     * 指定在方法之后返回
     * */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
        try{
        loginfo.get().setReturns(ret.toString());
        }catch (Throwable e){

        }
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        try {
            logger.info("Controller日志统一输出:[请求开始时间:{},请求耗时:{},请求路径:{},请求类型:{},请求方法:{},请求参数:{},请求响应:{}]",
                    loginfo.get().getRequestTime(), (System.currentTimeMillis() - startTime), loginfo.get().getRequestUrl(), loginfo.get().getRequestMethod(), loginfo.get().getClassMethod(), loginfo.get().getArgs(), loginfo.get().getReturns());

        }catch (Throwable e){

        }finally {
            loginfo.remove();
        }
        return ob;
    }
}