package com.epam.brest.course2015.provider;

import com.epam.brest.course2015.transactions.CarTransactions;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by antonsavitsky on 15.01.16.
 */
@Aspect
public class SubscribeForUpdatesHandler {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CarTransactions carTransactions;

    private static final Logger LOGGER = LogManager.getLogger(SubscribeForUpdatesHandler.class.getName());

    @After("execution(* *(..)) && @annotation(SubscribeForUpdates)")
    public void after(JoinPoint joinPoint) throws Throwable {
        String methodname = MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getName();
        Object returnval;
        switch(methodname){
            case "updateCar": simpMessagingTemplate.convertAndSend("/topic/update", carTransactions.getCarsDto());break;
            case "deleteCar": simpMessagingTemplate.convertAndSend("/topic/update",carTransactions.getCarsDto()); break;
            case "addCar": simpMessagingTemplate.convertAndSend("/topic/update",carTransactions.getCarsDto()); break;
            default: break;
        }

        /*switch(methodname){
            case "updateCar": LOGGER.info("updateCar"); break;
            case "deleteCar": LOGGER.info("deleteCar"); break;
            case "addCar":LOGGER.info("addCar"); break;
            default: break;
        }*/


    }
}
