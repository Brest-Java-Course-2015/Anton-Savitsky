package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Producer;
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
@ContextConfiguration(locations = {"classpath:test-spring-dao.xml"})
@Transactional
public class ProducerDaoJdbcImplTest {
    @Autowired
    private ProducerDao producerDao;

    @Test
    public void testGetProducerById(){
        Producer producer=producerDao.getProducerById(1);
        assertNotNull(producer);
        assertTrue(producer.getProducerId()==1);
    }

    @Test
    public void testAddProducer(){
        Producer producer=new Producer("toyota","korea");
        int id=producerDao.addProducer(producer);
        assertNotNull(id);
    }

    @Test
    public void testUpdateProducer(){
        Producer producer = new Producer(0, "newname!", "newcountry!");
        producerDao.updateProducer(producer);
        Producer updatedProducer=producerDao.getProducerById(0);
        assertNotNull(updatedProducer);
        assertTrue(updatedProducer.getProducerId()==0);
    }

    @Test
    public void testDeleteProducer(){
        int prodId=0;
        int sizeBefore=producerDao.getTotalCount();
        producerDao.deleteProducer(prodId);
        assertTrue(producerDao.countCarsByProducerId(prodId)==0);
        assertTrue(sizeBefore-1==producerDao.getTotalCount());
    }

    @Test
    public void testCountCarsByProducerId(){
        int count=producerDao.countCarsByProducerId(0);
        assertTrue(count == 3);
    }

    @Test
    public void testGetAllProducers(){
        List<Producer> producers=producerDao.getAllProducers();
        assertNotNull(producers);
        assertTrue(producers.size()==2);
    }
}
