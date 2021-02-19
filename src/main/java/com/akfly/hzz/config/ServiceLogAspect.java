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
public class ServiceLogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    ThreadLocal<LogDto> loginfo = new ThreadLocal<>();
    @Pointcut("execution( * com.akfly.hzz.service.*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {

    }
    @Before(value = "logPointCut()")
    public void addBeforeLogger(JoinPoint joinPoint) {
        try{
        LogDto logInfo=new LogDto();
        logInfo.setRequestTime(LocalDateTime.now().toString());
        logInfo.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logInfo.setArgs(Arrays.toString(joinPoint.getArgs()));
        loginfo.set(logInfo);
        }catch (Exception e){}
    }

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
            logger.debug("Service日志统一输出:[请求开始时间:{},请求耗时:{},请求方法:{},请求参数:{},请求响应:{}]",
                    loginfo.get().getRequestTime(), (System.currentTimeMillis() - startTime),loginfo.get().getClassMethod(), loginfo.get().getArgs(), loginfo.get().getReturns());

        }catch (Throwable e){

        }finally {
            loginfo.remove();
        }
        return ob;
    }

}
