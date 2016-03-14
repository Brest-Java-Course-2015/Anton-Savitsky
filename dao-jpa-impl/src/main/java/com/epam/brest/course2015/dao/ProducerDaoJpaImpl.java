package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by antonsavitsky on 3/8/16.
 */
@Repository
public class ProducerDaoJpaImpl implements ProducerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${producer.getAll}")
    private String getAll;
    @Value("${producer.getTotal}")
    private String getTotal;
    @Value("${producer.getCountOfCarsByProducerId}")
    private String getCountOfCarsByProducerId;
    @Value("${producer.getCarsByProducerId}")
    private String getCarsByProducerId;
    @Value("${producer.deleteCarsWithProducerId}")
    private String deleteCarsWithProducerId;


    @Override
    @Loggable
    public Producer getProducerById(Integer producerId) {
        Producer producer=entityManager.find(Producer.class, producerId);
        producer.setCountOfCars(countCarsByProducerId(producerId));
        return producer;
    }

    @Override
    @Loggable
    public Integer addProducer(Producer producer) {
        entityManager.persist(producer);
        return producer.getProducerId();
    }

    @Override
    @Loggable
    public void updateProducer(Producer producer) {
        List<Car> cars=entityManager.createQuery(getCarsByProducerId)
                .setParameter("producerId", producer.getProducerId()).getResultList();
        producer.setCars(cars);
        entityManager.merge(producer);
    }

    @Override
    @Loggable
    public Integer getTotalCount() {
        return ((Long)entityManager
                .createQuery(getTotal)
                .getSingleResult()).intValue();
    }

    @Override
    @Loggable
    public void deleteProducer(Integer producerId) {
        deleteAllCarsWithProducerId(producerId);
        entityManager.remove(entityManager.find(Producer.class, producerId));
    }

    @Override
    @Loggable
    public Integer countCarsByProducerId(Integer producerId) {
        return ((Long)entityManager.createQuery(getCountOfCarsByProducerId)
                .setParameter("producerId",producerId).getSingleResult()).intValue();
    }

    @Override
    @Loggable
    public void deleteAllCarsWithProducerId(Integer producerId) {
        entityManager.createQuery(deleteCarsWithProducerId)
                .setParameter("producerId",producerId).executeUpdate();
    }

    @Override
    @Loggable
    public List<Producer> getAllProducers() {
        List<Producer> list=entityManager.createQuery(getAll, Producer.class).getResultList();
        for(Producer p: list){
            p.setCountOfCars(countCarsByProducerId(p.getProducerId()));
        }
        return list;
    }
}
