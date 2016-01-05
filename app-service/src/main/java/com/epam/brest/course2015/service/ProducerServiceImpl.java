package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.CarDao;
import com.epam.brest.course2015.dao.ProducerDao;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.ProducerDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 17.11.15.
 */
@Transactional
public class ProducerServiceImpl implements ProducerService {
    private static final Logger LOGGER = LogManager.getLogger();

    private ProducerDao producerDao;

    @Autowired
    public void setProducerDao(ProducerDao producerDao) {
        this.producerDao=producerDao;
    }

    @Override
    public Producer getProducerById(Integer producerId) {
        Assert.notNull(producerId, "id should not be null!");
        Assert.isTrue(producerId>=0, "id should be not negative!");
        LOGGER.debug("getProducerById(): producerId={}",producerId);
        try {
            return producerDao.getProducerById(producerId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            LOGGER.debug("Producer with Id {} does not exist", producerId);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Integer addProducer(Producer producer) {
        Assert.notNull(producer);
        Assert.isNull(producer.getProducerId(),"Producer must be null before adding! ");
        LOGGER.debug("addProducer(): producer="+producer.toString());
        return producerDao.addProducer(producer);
    }

    @Override
    public void updateProducer(Producer producer) {
        Assert.notNull(producer, "producer must not be null!");
        Integer producerToUpdate=producer.getProducerId();
        Assert.notNull(producerToUpdate, "id must not be null!");
        try{
            producerDao.getProducerById(producerToUpdate);
        }catch (EmptyResultDataAccessException ex){
            throw new IllegalArgumentException("Producer doesn't exist!");
        }
        Assert.hasText(producer.getProducerName(), "name must contain some chars!");
        Assert.hasText(producer.getCountry(), "country must contain some chars!");
        LOGGER.debug("updateProducer(): producer:" + producer.toString());
        producerDao.updateProducer(producer);
    }

    @Override
    public Integer getProducersTotalCount() {
        LOGGER.debug("getProducerTotalCount()");
        return producerDao.getTotalCount();
    }

    @Override
    public void deleteProducer(Integer producerId) {
        LOGGER.debug("deleteProducer(): producerId={}",producerId);
        Assert.notNull(producerId, "id should be not null!");
        Assert.isTrue(producerId>=0,"id should be not negative!");
        try{
            producerDao.getProducerById(producerId);
        }catch (EmptyResultDataAccessException ex){
            throw new IllegalArgumentException("Producer must be existing to be deleted!");
        }
        producerDao.deleteProducer(producerId);
    }

    @Override
    public List<Producer> getAllProducers() {
        LOGGER.debug("getAllProducers()");
        return producerDao.getAllProducers();
    }

    @Override
    public ProducerDto getProducersDto() {
        LOGGER.debug("getProducersDto()");
        ProducerDto producerDto = new ProducerDto();
        producerDto.setTotal(producerDao.getTotalCount());
        if(producerDto.getTotal()>0){
            producerDto.setProducers(producerDao.getAllProducers());
        }
        else producerDto.setProducers(Collections.<Producer>emptyList());
        return producerDto;
    }
}
