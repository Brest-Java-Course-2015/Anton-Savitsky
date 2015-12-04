package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by antonsavitsky on 02.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional()
public class ProducerDaoImplTest {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ProducerDao producerDao;

    @Test
    public void testGetProducerById(){
        LOGGER.debug("testGetProducerById()");
        Producer producer=producerDao.getProducerById(0);
        assertNotNull(producer);
        LOGGER.debug("we've got producer={}",producer.toString());
        assertTrue(producer.getProducerId()==0);
    }

    @Test
    public void testAddProducer(){
        LOGGER.debug("testAddProducer()");
        Producer producer=new Producer("toyota","Korea");
        int id=producerDao.addProducer(producer);
        assertNotNull(producer);
        LOGGER.debug("Id of added producer:{}",id);
    }

    @Test
    public void testUpdateProducer(){
        LOGGER.debug("testUpdateProducer()");
        Producer producer = new Producer(0, "newname!", "newcountry!");
        producerDao.updateProducer(producer);
        Producer updatedProducer=producerDao.getProducerById(0);
        assertNotNull(updatedProducer);
        assertTrue(updatedProducer.getProducerId()==0);
    }

    @Test
    public void testDeleteProducer(){
        LOGGER.debug("testing deleteProducer()");
        int prodId=0;
        int sizeBefore=producerDao.getTotalCount();
        producerDao.deleteProducer(prodId);
        assertTrue(producerDao.countCarsByProducerId(prodId)==0);
        LOGGER.debug("asserted true: countCarsByProducerId(prodId)==0 after deleting producer: producerId={}",prodId);
        assertTrue(sizeBefore-1==producerDao.getTotalCount());
        LOGGER.debug("asserted true: count of producers decreased by 1 after deleting producer");
    }

    // to be refactored soon
    @Test
    public void testCountCarsByProducerId(){
        LOGGER.debug("testing countCarsByProducerId()");
        int count=producerDao.countCarsByProducerId(0);
        LOGGER.debug("Count of cars={}",count);
        assertTrue(count == 3);
    }

    @Test
    public void testGetAllProducers(){
        LOGGER.debug("testing getAllProducers()");
        List<Producer> producers=producerDao.getAllProducers();
        assertNotNull(producers);
        assertTrue(producers.size()==1);
        LOGGER.debug("producers.size=1 as expected");
    }
}
