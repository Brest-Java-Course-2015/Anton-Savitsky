package com.epam.brest.course2015.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by antonsavitsky on 3/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-soap.xml"})
public class CarSoapServiceConsumerIT {

    @Autowired
    private CarSoapConsumer carSoapConsumer;

    @Test
    public void testGetCarById(){
        carSoapConsumer.getCarById(1);
    }
}
