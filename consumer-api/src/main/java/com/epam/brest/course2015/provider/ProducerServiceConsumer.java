package com.epam.brest.course2015.provider;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import java.util.List;

/**
 * Created by antonsavitsky on 3/12/16.
 */
public interface ProducerServiceConsumer {
        List<Producer> getAllProducers();
        Producer getProducerById(Integer id);
        Integer addProducer(Producer producer);
        void updateProducer(Producer producer);
        public void deleteProducer(Integer id);
        ProducerDto getProducersDto();
}
