package com.epam.brest.course2015.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by antonsavitsky on 15.01.16.
 */
@Aspect
public class MyLogger {
    private static final Logger LOGGER = LogManager.getLogger(MyLogger.class.getName());

    @Around("execution(* *(..)) && @annotation(Loggable)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String classname = joinPoint.getSignature().getDeclaringTypeName();
        String methodname = MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getName();
        Object[] args = joinPoint.getArgs();
        Object returnval;
        long execTime, startTime;
        try {
            startTime = System.currentTimeMillis();
            returnval = joinPoint.proceed();
            execTime = System.currentTimeMillis() - startTime;
        } catch (Exception exception) {
            LOGGER.error("{} #{}({}): {}",
                    classname,
                    methodname,
                    args,
                    exception);
            LOGGER.catching(Level.ERROR, exception.getCause());
            throw exception;
        }
        if (returnval == null) {
            LOGGER.info("{} #{}({}): void in {} ms",
                    classname,
                    methodname,
                    args,
                    execTime);
        }else {
            String returnClass = returnval.getClass().getSimpleName();
            LOGGER.info("{} #{}({}): {}:{} in {} ms",
                    classname,
                    methodname,
                    args,
                    returnClass,
                    returnval,
                    execTime);
        }
        return returnval;
    }
}
