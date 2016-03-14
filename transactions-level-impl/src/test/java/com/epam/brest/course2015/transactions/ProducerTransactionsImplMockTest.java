package com.epam.brest.course2015.transactions;

import com.epam.brest.course2015.dao.ProducerDao;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Created by antonsavitsky on 27.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service-mock-test.xml"})
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProducerTransactionsImplMockTest {
    private Producer testProducer1;
    private Producer testProducer2;

    @Autowired
    private ProducerTransactions producerTransactions;

    @Autowired
    private ProducerDao producerMockDao;

    @Before
    public void setUp() throws ParseException {
        testProducer1=new Producer(1,"name1","country1");
        testProducer2=new Producer("name2","country2");
    }

    @After
    public void clean() {
        verify(producerMockDao);
        reset(producerMockDao);
    }

    @Test
    public void testGetProducerById(){
        expect(producerMockDao.getProducerById(testProducer1.getProducerId())).andReturn(testProducer1);
        replay(producerMockDao);
        Producer resultProducer= producerTransactions.getProducerById(testProducer1.getProducerId());
        Assert.isTrue(resultProducer==testProducer1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProducerByNonExistingId(){
        expect(producerMockDao.getProducerById(testProducer1.getProducerId())).andThrow(new EmptyResultDataAccessException(1));
        replay(producerMockDao);
        producerTransactions.getProducerById(testProducer1.getProducerId());
    }

    @Test
    public void testAddProducer(){
        expect(producerMockDao.addProducer(testProducer2)).andReturn(testProducer2.getProducerId());
        replay(producerMockDao);
        producerTransactions.addProducer(testProducer2);
    }

    @Test
    public void testUpdateProducer(){
        expect(producerMockDao.getProducerById(testProducer1.getProducerId())).andReturn(testProducer1);
        producerMockDao.updateProducer(testProducer1);
        expectLastCall().once();
        replay(producerMockDao);
        producerTransactions.updateProducer(testProducer1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNonExistingProducer(){
        expect(producerMockDao.getProducerById(testProducer1.getProducerId())).andThrow(new EmptyResultDataAccessException(1));
        replay(producerMockDao);
        producerTransactions.updateProducer(testProducer1);
    }

    @Test
    public void testDeleteProducer(){
        expect(producerMockDao.getProducerById(testProducer1.getProducerId())).andReturn(testProducer1);
        producerMockDao.deleteProducer(testProducer1.getProducerId());
        expectLastCall().once();
        replay(producerMockDao);
        producerTransactions.deleteProducer(testProducer1.getProducerId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistingProducer(){
        expect(producerMockDao.getProducerById(testProducer1.getProducerId())).andThrow(new EmptyResultDataAccessException(1));
        replay(producerMockDao);
        producerTransactions.deleteProducer(testProducer1.getProducerId());
    }

    @Test
    public void testGetProducersTotalCount(){
        expect(producerMockDao.getTotalCount()).andReturn(2);
        replay(producerMockDao);
        Assert.isTrue(producerTransactions.getProducersTotalCount()==2);
    }

    @Test
    public void testGetAllProducers(){
        expect(producerMockDao.getAllProducers()).andReturn(Arrays.<Producer>asList(testProducer1,testProducer2));
        replay(producerMockDao);
        List<Producer> resList= producerTransactions.getAllProducers();
        Assert.isTrue(resList.get(0)==testProducer1);
        Assert.isTrue(resList.get(1)==testProducer2);
    }

    @Test
    public void testGetProducersDto(){
        expect(producerMockDao.getAllProducers()).andReturn(Arrays.<Producer>asList(testProducer1,testProducer2));
        replay(producerMockDao);
        ProducerDto producerDto= producerTransactions.getProducersDto();
        Assert.isTrue(producerDto.getTotal()==2);
        Assert.isTrue(producerDto.getProducers().get(0)==testProducer1);
        Assert.isTrue(producerDto.getProducers().get(1)==testProducer2);
    }

    @Test
    public void testGetNullProducersDto(){
        expect(producerMockDao.getAllProducers()).andReturn(Collections.<Producer>emptyList());
        replay(producerMockDao);
        Assert.isTrue(producerTransactions.getProducersDto().getTotal()==0);
    }
}
