package com.project.hrm.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.project.hrm.controllers.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        logger.info("Before method execution: {}", joinPoint.getSignature().getName());
        logger.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

//    @After("execution(* com.project.erp.services.*.*(..))")
//    public void logAfterMethodExecution(JoinPoint joinPoint) {
//        logger.info("After method execution: {} is successfully", joinPoint.getSignature().getName());
//    }

//    @AfterReturning(pointcut = "execution(* com.project.hrm.services.*.*(..))", returning = "result")
//    public void logAfterReturning(JoinPoint joinPoint, Object result) {
//        logger.info("After returning - Method: {} returned: {}", joinPoint.getSignature().getName(), result);
//    }

//    @AfterThrowing(pointcut = "execution(* com.project.hrm.services.*.*(..))", throwing = "exception")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
//        logger.error("Exception - Method: {} threw an exception: {}", joinPoint.getSignature().getName(), exception.getMessage());
//    }

//    @Around("execution(* com.project.erp.services.*.*(..))")
//    public Object logAroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        logger.info("Method: {} is about to execute.", joinPoint.getSignature().getName());
//
//        Object result;
//        try {
//            result = joinPoint.proceed(); // Gọi phương thức gốc
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            logger.info("Method: {} executed in {} ms.", joinPoint.getSignature().getName(), elapsedTime);
//        } catch (Exception e) {
//            logger.error("Method: {} threw an exception: {}", joinPoint.getSignature().getName(), e.getMessage());
//            throw e;
//        }
//        return result;
//    }



}
