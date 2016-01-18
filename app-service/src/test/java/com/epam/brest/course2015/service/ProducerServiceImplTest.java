package com.epam.brest.course2015.service;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by antonsavitsky on 27.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional()
public class ProducerServiceImplTest {
    @Autowired
    private ProducerService producerService;

    @Test
    public void testGetProducerById() throws Exception {
        int producerId=0;
        Producer producer= producerService.getProducerById(producerId);
        Assert.notNull(producer);
        Assert.isTrue(producer.getProducerId()==0);
    }

    @Test
    public void testGetProducersDto(){
        ProducerDto producerdto=producerService.getProducersDto();
        Assert.notNull(producerdto);
        Assert.notNull(producerdto.getProducers());
        Assert.isTrue(producerdto.getProducers().get(0).getClass().equals(Producer.class));
    }

    @Test
    public void testDeleteProducer() {
        int sizeBeforeDeleting=producerService.getProducersTotalCount();
        if(sizeBeforeDeleting>0)
            producerService.deleteProducer(0);
        else {
            return;}
        Assert.isTrue(sizeBeforeDeleting-1==producerService.getProducersTotalCount());
    }

    @Test
    public void testAddProducer(){
        Producer producer=new Producer("ferrari","italy");
        producerService.addProducer(producer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddProducerNotNullId(){
        producerService.addProducer(new Producer(1,"ferrari","italy"));
    }

    @Test
    public void testUpdateProducer() {
        Producer producer;
        if (producerService.getProducersTotalCount() > 0){
            producerService.updateProducer(producer=new Producer(producerService.getAllProducers().get(0).getProducerId(), "updatedName", "updatedCountry"));
        }else{
            return;
        }
        Assert.isTrue(producer.getProducerId().equals(producerService.getAllProducers().get(0).getProducerId()));
        Assert.isTrue(producer.getCountry().equals(producerService.getAllProducers().get(0).getCountry()));
        Assert.isTrue(producer.getProducerName().equals(producerService.getAllProducers().get(0).getProducerName()));
    }
}
