package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.provider.ProducerServiceConsumer;

import java.util.List;

/**
 * Created by antonsavitsky on 3/14/16.
 */
public class ProducerRestConsumer implements ProducerServiceConsumer {
    @Override
    public List<Producer> getAllProducers() {
        return null;
    }

    @Override
    public Producer getProducerById(Integer id) {
        return null;
    }

    @Override
    public Integer addProducer(Producer producer) {
        return null;
    }

    @Override
    public void updateProducer(Producer producer) {

    }

    @Override
    public void deleteProducer(Integer id) {

    }

    @Override
    public ProducerDto getProducersDto() {
        return null;
    }
}
