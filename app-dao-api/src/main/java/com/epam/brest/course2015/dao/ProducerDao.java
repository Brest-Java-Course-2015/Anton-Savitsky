package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Producer;

import java.util.List;

/**
 * Created by antonsavitsky on 02.12.15.
 */
public interface ProducerDao {
    Producer getProducerById(Integer producerId);
    Integer addProducer(Producer producer);
    void updateProducer(Producer producer);
    Integer getTotalCount();
    void deleteProducer(Integer producerId);
    Integer countCarsByProducerId(Integer producerId);
    void deleteAllCarsWithProducerId(Integer producerId);
    List<Producer> getAllProducers();
}
