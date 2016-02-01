package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by antonsavitsky on 16.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core.xml"})
public class ProducerDtoTest {
    @Autowired
    private ProducerDto producerDto;

    @Test
    public void testGetTotal(){
        producerDto.setTotal(2);
        assertEquals(producerDto.getTotal(), (Integer)2);
    }
    @Test
    public void testGetCProducers(){
        List<Producer> producerList= Arrays.<Producer>asList(new Producer(1), new Producer(2));
        producerDto.setProducers(producerList);
        assertEquals(producerDto.getProducers(),producerList);
    }
}
