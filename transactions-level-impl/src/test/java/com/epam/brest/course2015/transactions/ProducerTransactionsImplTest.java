package com.epam.brest.course2015.transactions;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by antonsavitsky on 27.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-service.xml"})
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProducerTransactionsImplTest {
    @Autowired
    private ProducerTransactions producerTransactions;

    @Test
    public void testGetProducerById() throws Exception {
        int producerId=0;
        Producer producer= producerTransactions.getProducerById(producerId);
        Assert.notNull(producer);
        Assert.isTrue(producer.getProducerId()==0);
    }

    @Test
    public void testGetProducersDto(){
        ProducerDto producerdto= producerTransactions.getProducersDto();
        Assert.notNull(producerdto);
        Assert.notNull(producerdto.getProducers());
        Assert.isTrue(producerdto.getProducers().get(0).getClass().equals(Producer.class));
    }

    @Test
    public void testDeleteProducer() {
        int sizeBeforeDeleting= producerTransactions.getProducersTotalCount();
        if(sizeBeforeDeleting>0)
            producerTransactions.deleteProducer(0);
        else {
            return;}
        Assert.isTrue(sizeBeforeDeleting-1== producerTransactions.getProducersTotalCount());
    }

    @Test
    public void testAddProducer(){
        Producer producer=new Producer("ferrari","italy");
        producerTransactions.addProducer(producer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddProducerNotNullId(){
        producerTransactions.addProducer(new Producer(1,"ferrari","italy"));
    }

    @Test
    public void testUpdateProducer() {
        Producer producer;
        if (producerTransactions.getProducersTotalCount() > 0){
            producerTransactions.updateProducer(producer=new Producer(producerTransactions.getAllProducers().get(0).getProducerId(), "updatedName", "updatedCountry"));
        }else{
            return;
        }
        Assert.isTrue(producer.getProducerId().equals(producerTransactions.getAllProducers().get(0).getProducerId()));
        Assert.isTrue(producer.getCountry().equals(producerTransactions.getAllProducers().get(0).getCountry()));
        Assert.isTrue(producer.getProducerName().equals(producerTransactions.getAllProducers().get(0).getProducerName()));
    }
}
