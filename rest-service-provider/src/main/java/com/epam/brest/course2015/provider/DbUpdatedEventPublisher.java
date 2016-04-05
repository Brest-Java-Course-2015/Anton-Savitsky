package com.epam.brest.course2015.provider;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.io.IOException;

/**
 * Created by antonsavitsky on 4/4/16.
 */
public class DbUpdatedEventPublisher implements Runnable, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher=publisher;
    }

    public void publish(){
        DbUpdatedEvent dbUpdatedEvent=new DbUpdatedEvent(this);
        publisher.publishEvent(dbUpdatedEvent);
    }

    @Override
    public void run() {
        publish();
    }
}
