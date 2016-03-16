package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.provider.ProducerServiceConsumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by antonsavitsky on 3/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-rest-consumer-config.xml"})
@Transactional
public class TestProducerRestConsumer {

    private static Producer updateProducer;
    private static Producer addProducer;

    @Autowired
    private ProducerServiceConsumer producerServiceConsumer;

    @Before
    public void setUp(){
        updateProducer=new Producer(1, "updatedProducer", "updatedProducer");
        addProducer=new Producer("addedProducer","addedCountry");
    }

    @Test
    public void testGetAllProducers(){
        producerServiceConsumer.getAllProducers();
    }

    @Test
    public void testAddProducer(){
        producerServiceConsumer.addProducer(addProducer);
    }

    @Test
    public void testUpdateProducer(){
        producerServiceConsumer.updateProducer(updateProducer);
    }

    @Test
    public void testDeleteProducer(){
        producerServiceConsumer.deleteProducer(1);
    }

    @Test
    public void testGetProducerById(){
        producerServiceConsumer.getProducerById(0);
    }

    @Test
    public void testGetProducersDto(){
        producerServiceConsumer.getProducersDto();
    }

}
