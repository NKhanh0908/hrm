package com.project.hrm.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.project.hrm.controllers..*(..))")
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

    @Pointcut("execution(* com.project.hrm.repositories..*(..))")
    public void repositoryMethods() {}

    @Around("repositoryMethods()")
    public Object logRepositoryExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        return logMethodExecution(joinPoint, "REPOSITORY");
    }

    private Object logMethodExecution(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String traceId = UUID.randomUUID().toString().substring(0, 8);

        // Set trace ID for correlation
        MDC.put("traceId", traceId);
        MDC.put("layer", layer);

        StopWatch stopWatch = new StopWatch();

        try {
            // Log method entry
            log.info("[{}] Entering {}.{} with args: {}",
                    layer, className, methodName, Arrays.toString(joinPoint.getArgs()));

            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();

            // Log successful exit
            log.info("[{}] {}.{} executed in {}ms", layer, className, methodName, stopWatch.getTotalTimeMillis());


            return result;

        } catch (Exception e) {
            stopWatch.stop();

            // Log exception
            log.error("[{}] Exception in {}.{} after {}ms: {}",
                    layer, className, methodName, stopWatch.getTotalTimeMillis(), e.getMessage(), e);

            throw e;

        } finally {
            // Clean up MDC
            MDC.remove("traceId");
            MDC.remove("layer");
        }
    }

    @AfterThrowing(pointcut = "repositoryMethods()",
            throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.error("Unhandled exception in {}.{}: {}",
                className, methodName, exception.getMessage(), exception);
    }

}
