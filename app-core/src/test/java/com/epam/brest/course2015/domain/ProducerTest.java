package com.epam.brest.course2015.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by antonsavitsky on 02.12.15.
 */
import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core.xml"})
public class ProducerTest {
    @Autowired
    private Producer producer;

    @Test
    public void testGetProducerId(){
        producer.setProducerId(1);
        assertEquals(producer.getProducerId(),(Integer)1);
    }

    @Test
    public void testGetProducerName(){
        producer.setProducerName("Toyota");
        assertEquals(producer.getProducerName(), "Toyota");
    }

    @Test
    public void testGetCountry(){
        producer.setCountry("Korea");
        assertEquals(producer.getCountry(), "Korea");
    }
}
