package com.epam.brest.course2015.transactions;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;

import java.util.List;

/**
 * Created by antonsavitsky on 27.12.15.
 */
public interface ProducerTransactions {
    Producer getProducerById(Integer producerId);
    Integer addProducer(Producer producer);
    void updateProducer(Producer producer);
    Integer getProducersTotalCount();
    void deleteProducer(Integer producerId);
    List<Producer> getAllProducers();
    ProducerDto getProducersDto();
}
