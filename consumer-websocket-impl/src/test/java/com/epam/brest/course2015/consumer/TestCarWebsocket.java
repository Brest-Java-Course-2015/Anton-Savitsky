package com.epam.brest.course2015.consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by antonsavitsky on 3/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-websocket.xml"})
public class TestCarWebsocket {

    private CarWebsocketConsumer carWebsocketConsumer;

    @Before
    public void setup(){
        carWebsocketConsumer=new CarWebsocketConsumer();
    }

    @Test
    public void testSendMessageToProvider(){
        carWebsocketConsumer.sendMessageToProvider();
    }

}
