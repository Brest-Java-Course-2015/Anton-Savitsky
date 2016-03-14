package com.epam.brest.course2015.transactions;

import com.epam.brest.course2015.dao.ProducerDao;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.Collections;
import java.util.List;

/**
 * Created by antonsavitsky on 17.11.15.
 */
//Transactions are supported for all methods defined in this class
@Transactional
public class ProducerTransactionsImpl implements ProducerTransactions {
    @Value("${producer.IdNotNull}")
    private String producerIdNotNull;
    @Value("${producer.IdNotNegative}")
    private String producerIdNotNegative;
    @Value("${producer.ProducerNotNull}")
    private String producerNotNull;
    @Value("${producer.IdNull}")
    private String producerIdNull;
    @Value("${producer.NameNotNull}")
    private String producerNameNotNull;
    @Value("${producer.CountryNotNull}")
    private String producerCountryNotNull;

    private ProducerDao producerDao;
    @Autowired
    public void setProducerDao(ProducerDao producerDao) {
        this.producerDao = producerDao;
    }

    @Loggable
    @Override
    public Producer getProducerById(Integer producerId) {
        Assert.notNull(producerId, producerIdNotNull );
        Assert.isTrue(producerId >= 0, producerIdNotNegative);
        try {
            return producerDao.getProducerById(producerId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Loggable
    @Override
    public Integer addProducer(Producer producer) {
        Assert.notNull(producer);
        Assert.isNull(producer.getProducerId(), producerIdNull);
        Assert.hasText(producer.getProducerName(), producerNameNotNull);
        Assert.hasText(producer.getCountry(), producerCountryNotNull);
        return producerDao.addProducer(producer);
    }

    @Loggable
    @Override
    public void updateProducer(Producer producer) {
        Assert.notNull(producer, producerNotNull);
        Integer producerToUpdate=producer.getProducerId();
        Assert.notNull(producerToUpdate, producerIdNotNull);
        try{
            producerDao.getProducerById(producerToUpdate);
        }catch (EmptyResultDataAccessException ex){
            throw new IllegalArgumentException();
        }
        Assert.hasText(producer.getProducerName(), producerNameNotNull);
        Assert.hasText(producer.getCountry(), producerCountryNotNull);
        producerDao.updateProducer(producer);
    }

    @Loggable
    @Override
    public Integer getProducersTotalCount() {
        return producerDao.getTotalCount();
    }

    @Loggable
    @Override
    public void deleteProducer(Integer producerId) {
        Assert.notNull(producerId, producerIdNotNull);
        Assert.isTrue(producerId >= 0, producerIdNotNegative);
        try{
            producerDao.getProducerById(producerId);
        }catch (EmptyResultDataAccessException ex){
            throw new IllegalArgumentException();
        }
        producerDao.deleteProducer(producerId);
    }

    @Loggable
    @Override
    public List<Producer> getAllProducers() {
        return producerDao.getAllProducers();
    }

    @Loggable
    @Override
    public ProducerDto getProducersDto() {
        ProducerDto producerDto = new ProducerDto();
        List<Producer> producerList=producerDao.getAllProducers();
        if(producerList.size()>0){
            producerDto.setProducers(producerList);
            producerDto.setTotal(producerList.size());
        }
        else {
            producerDto.setProducers(Collections.<Producer>emptyList());
            producerDto.setTotal(0);
        }
        return producerDto;
    }
}
