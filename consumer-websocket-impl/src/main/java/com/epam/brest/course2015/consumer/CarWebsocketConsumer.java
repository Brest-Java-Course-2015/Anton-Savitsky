package com.epam.brest.course2015.consumer;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Created by antonsavitsky on 3/30/16.
 */
public class CarWebsocketConsumer {

    WebSocketClient transport = new StandardWebSocketClient();
    String url="ws://localhost:8888/jetty/websocket/endpoint";

    public void sendMessageToProvider(){


        StompSessionHandler handler=new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                System.out.println("Connected with session id: "+session.getSessionId());
                //session.send("/app/message", "Hey bro!");
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                System.out.println("Error!");
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                System.out.println("Error occured during transport!");
                exception.printStackTrace();
                System.out.println("Is connected: "+session.isConnected());
            }
        };
/*
        WebSocketHandler webSocketHandler=new AbstractWebSocketHandler() {
            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                System.out.println("AbstractWebSocketHandler: ");
                exception.printStackTrace();
            }

            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                System.out.println("Connected to server!!!");
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                System.out.println("Closed connection to server!!!");
            }
        };

*/
        //transport.doHandshake(webSocketHandler, url);

        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        stompClient.connect(url, handler);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After connect() executed");

    }


}
