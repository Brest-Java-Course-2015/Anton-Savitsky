package com.epam.brest.course2015.provider;

import com.epam.brest.course2015.transactions.CarTransactions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * Created by antonsavitsky on 4/4/16.
 */
public class DbUpdatedEventHandler implements ApplicationListener<DbUpdatedEvent> {
    //@Autowired
    //private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CarTransactions carTransactions;

    @Override
    public void onApplicationEvent(DbUpdatedEvent event) {
        //simpMessagingTemplate.convertAndSend("/topic/update", carTransactions.getCarsDto());
    }
}
