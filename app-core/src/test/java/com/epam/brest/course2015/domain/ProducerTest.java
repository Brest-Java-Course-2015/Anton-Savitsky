package com.epam.brest.course2015.domain;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by antonsavitsky on 02.12.15.
 */
import static org.junit.Assert.*;

public class ProducerTest {
    private Producer producer;

    @Before
    public void setUp(){
        producer=new Producer();
    }

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
