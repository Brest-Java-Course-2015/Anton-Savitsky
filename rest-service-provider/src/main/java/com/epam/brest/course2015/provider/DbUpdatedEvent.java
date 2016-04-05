package com.epam.brest.course2015.provider;

import org.springframework.context.ApplicationEvent;

/**
 * Created by antonsavitsky on 4/4/16.
 */
public class DbUpdatedEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DbUpdatedEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "DbUpdatedEvent";
    }
}
