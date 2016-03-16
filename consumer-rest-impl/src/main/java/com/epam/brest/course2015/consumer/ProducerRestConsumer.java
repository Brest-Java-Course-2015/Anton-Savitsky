package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.provider.ProducerServiceConsumer;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by antonsavitsky on 3/14/16.
 */
public class ProducerRestConsumer implements ProducerServiceConsumer {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${restServicesPrefix}")
    private String restServicesPrefix;

    @Override
    @Loggable
    public List<Producer> getAllProducers() {
        return restTemplate.getForObject(restServicesPrefix+"/producer",
                List.class);
    }

    @Loggable
    @Override
    public Producer getProducerById(Integer id) {
        return restTemplate.getForObject(restServicesPrefix+"/producer/{id}",
                Producer.class, id);
    }

    @Loggable
    @Override
    public Integer addProducer(Producer producer) {
        return restTemplate.postForObject(restServicesPrefix+"/producer",
                producer, Producer.class).getProducerId();
    }

    @Loggable
    @Override
    public void updateProducer(Producer producer) {
        restTemplate.put(restServicesPrefix+"/producer", producer);
    }

    @Loggable
    @Override
    public void deleteProducer(Integer id) {
        restTemplate.delete(restServicesPrefix+"/producer/{id}",id);
    }

    @Loggable
    @Override
    public ProducerDto getProducersDto() {
        return restTemplate.getForObject(restServicesPrefix+"/producer/dto", ProducerDto.class);
    }
}
