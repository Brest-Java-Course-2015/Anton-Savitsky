package com.epam.brest.course2015.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Created by antonsavitsky on 4/3/16.
 */
@Component
public class MessagingApplicationListener implements ApplicationListener<ContextRefreshedEvent>, Notifiable {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
    /*private final NotifierFactory notifierFactory;
    private final MessageSendingOperations<String> messagingTemplate;
    private Notifier notifier;

    @Autowired
    public MessagingApplicationListener(NotifierFactor notifierFactory, MessageSendingOperations<String> messagingTemplate) {
        this.notifierFactory = notifierFactory;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (notifier == null) {
            notifier = notifierFactory.create(this);
            notifier.start();
        }
    }

    public void notify(NotifyEvent event) {
        messagingTemplate.convertAndSend("/topic/greetings", new Greeting("Hello, " + event.subject + "!"));
    }

    @PreDestroy
    private void stopNotifier() {
        if (notifier != null) {
            notifier.stop();
        }
    }*/
}