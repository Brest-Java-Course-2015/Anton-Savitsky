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
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProducerService producerService;

    @Test
    public void testGetProducerById() throws Exception {
        int producerId=0;
        LOGGER.debug("test: getProducerById()");
        Producer producer= producerService.getProducerById(producerId);
        Assert.notNull(producer);
        Assert.isTrue(producer.getProducerId()==0);
        LOGGER.debug(producer.toString());
    }

    @Test
    public void testGetProducersDto(){
        LOGGER.debug("test: getProducersDto()");
        ProducerDto producerdto=producerService.getProducersDto();
        Assert.notNull(producerdto);
        Assert.notNull(producerdto.getProducers());
        Assert.isTrue(producerdto.getProducers().get(0).getClass().equals(Producer.class));
    }

    @Test
    public void testDeleteProducer() {
        LOGGER.debug("test: deleteProducer()");
        int sizeBeforeDeleting=producerService.getProducersTotalCount();
        if(sizeBeforeDeleting>0)
            producerService.deleteProducer(0);
        else {
            LOGGER.debug("Error: there must be at least one producer!");
            return;}
        Assert.isTrue(sizeBeforeDeleting-1==producerService.getProducersTotalCount());
    }

    @Test
    public void testAddProducer(){
        LOGGER.debug("test: addProducer()");
        Producer producer=new Producer("ferrari","italy");
        producerService.addProducer(producer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddProducerNotNullId(){
        LOGGER.debug("test: addProducer() for a failure when adding producer with not null id");
        producerService.addProducer(new Producer(1,"ferrari","italy"));
    }

    @Test
    public void testUpdateProducer() {
        LOGGER.debug("test: updateProducer()");
        Producer producer;
        if (producerService.getProducersTotalCount() > 0){
            producerService.updateProducer(producer=new Producer(producerService.getAllProducers().get(0).getProducerId(), "updatedName", "updatedCountry"));
        }else{
            LOGGER.debug("There must be at least one producer to be updated!");
            return;
        }
        Assert.isTrue(producer.getProducerId().equals(producerService.getAllProducers().get(0).getProducerId()));
        Assert.isTrue(producer.getCountry().equals(producerService.getAllProducers().get(0).getCountry()));
        Assert.isTrue(producer.getProducerName().equals(producerService.getAllProducers().get(0).getProducerName()));
    }
}
