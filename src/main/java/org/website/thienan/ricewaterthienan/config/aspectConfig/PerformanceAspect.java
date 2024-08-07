package org.website.thienan.ricewaterthienan.config.aspectConfig;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    @Pointcut("within(org.website.thienan.ricewaterthienan.controller.*.*)")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object measureControllerMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();
        Object returnValue = proceedingJoinPoint.proceed();
        long end = System.nanoTime();
        String methodName = proceedingJoinPoint.getSignature().getName();
        log.info("Execution of function ->  {}() took {} ms", methodName, TimeUnit.NANOSECONDS.toMillis(end - start));
        return returnValue;
    }
}
