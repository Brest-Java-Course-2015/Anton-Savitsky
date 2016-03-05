package com.epam.brest.course2015.dao.jpa;

import com.epam.brest.course2015.dao.CarDao;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.test.Loggable;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by antonsavitsky on 02.03.16.
 */
@Repository
@Transactional
public class CarJpaDao implements CarDao{
    @Value("${car.getTotalCount}")
    private String getTotalCount;
    @Value("${car.getNumOfCarsByProdId}")
    private String getNumOfCarsByProdId;
    @Value("${car.getAll}")
    private String getAll;
    @Value("${car.getCountById}")
    private String getCountById;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Loggable
    public Integer getTotalCount() {
        return ((Long)entityManager
                .createQuery(getTotalCount)
                .getSingleResult()).intValue();
    }

    @Override
    public List<Car> getPagingList(Integer min,
                                   Integer max) {
        return null;
    }

    @Loggable
    @Override
    public Car getCarById(Integer carId) {
        return entityManager.find(Car.class, carId);
    }

    @Override
    @Loggable
    public Integer getCountOfCarsByProducerId(Integer producerId) {
        return ((Long)entityManager
                .createQuery(getNumOfCarsByProdId)
                .setParameter("producerId",producerId)
                .getSingleResult()).intValue();
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(LocalDate dateBefore,
                                                   LocalDate dateAfter) {
        //entityManager.createQuery()
        return null;
    }


    @Override
    @Loggable
    public Integer addCar(Car car) {
        entityManager.persist(car);
        return entityManager.merge(car).getCarId();
    }


    @Override
    @Loggable
    public void updateCar(Car car) {
        entityManager.merge(car);
    }


    @Override
    @Loggable
    public void deleteCar(Integer carId) {
        entityManager.remove(getCarById(carId));
    }


    @Override
    @Loggable
    public List<Car> getAllCars() {
        return entityManager.createQuery(getAll, Car.class).getResultList();
    }


    @Override
    @Loggable
    public Integer getCountCarsById(Integer carId) {
        return ((Long)entityManager
                .createQuery(getCountById)
                .setParameter("carId", carId)
                .getSingleResult()).intValue();
    }
}
