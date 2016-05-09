package com.epam.brest.course2015.websocket;

import com.epam.brest.course2015.test.Loggable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by antonsavitsky on 3/29/16.
 */
@Controller
public class CarWebsocketController {

    @Loggable
    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public Shout getCarById(Shout message){
        System.out.println("Received message: "+message.getMessage());
        Shout sendMessage=new Shout();
        sendMessage.setMessage("Hello "+message.getMessage());
        return sendMessage;
    }
}
