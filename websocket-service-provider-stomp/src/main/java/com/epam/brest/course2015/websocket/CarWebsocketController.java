package com.epam.brest.course2015.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by antonsavitsky on 3/29/16.
 */
@Controller
public class CarWebsocketController {

    @MessageMapping("/message")
    public void getCarById(Shout message){
        System.out.println("Received message: "+message.getMessage());
    }

    @SubscribeMapping("/submessage")
    public Shout subscibe(Shout message){
        return message;
    }
}
