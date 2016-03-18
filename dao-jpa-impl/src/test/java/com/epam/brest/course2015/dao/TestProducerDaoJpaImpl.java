package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * Created by antonsavitsky on 3/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-jpa-config.xml"})
@Transactional
public class TestProducerDaoJpaImpl {

    private static Producer testAddProducer=new Producer("newProducer", "newCountry");
    private static Producer testUpdateProducer=new Producer(0,"updatedProducer", "updatedCountry", Collections.<Car>emptyList());

    @Autowired
    private ProducerDao producerDao;

    @Test
    public void testGetAll(){
        List<Producer> producerList=producerDao.getAllProducers();
        Assert.notNull(producerList);
        Assert.isTrue(producerList.size()==2);
    }

    @Test
    public void testGetProducerById(){
        Producer producer=producerDao.getProducerById(0);
        Assert.notNull(producer);
        Assert.isTrue(producer.getProducerId().equals(0));
    }

    @Test
    public void testGetTotal(){
        Assert.isTrue(producerDao.getTotalCount().equals(2));
    }


    @Test
    public void testAddProducer(){
        Integer number=producerDao.addProducer(testAddProducer);
        //Assert.isTrue(number.equals(2));
    }


    @Test
    public void testUpdate(){
        producerDao.updateProducer(testUpdateProducer);
        Assert.isTrue(producerDao.getProducerById(testUpdateProducer.getProducerId())
                .getProducerName().equals("updatedProducer"));
    }

    @Test
    public void testDeleteProducer(){
        Integer before=producerDao.getTotalCount();
        producerDao.deleteProducer(0);
        Assert.isTrue(before.equals(producerDao.getTotalCount()+1));
    }

    @Test
    public void testGetCountOfCarsByProducerId(){
        Assert.isTrue(producerDao.countCarsByProducerId(0).equals(3));
    }

    @Test
    public void testDeleteAllCarsWithProducerId(){
        producerDao.deleteAllCarsWithProducerId(0);
    }
}
