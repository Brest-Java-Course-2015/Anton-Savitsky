package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.CarDao;
import com.epam.brest.course2015.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 17.11.15.
 */
@Transactional
public class CarServiceImpl implements CarService{
    @Value("${getCarById.carIdNotNull}")
    private String carIdNotNull;
    @Value("${getCarById.carIdPositive}")
    private String carIdPositive;
    @Value("${getCarByName.carNameNotNull}")
    private String carNameNotNull;
    @Value("${getCountOfCarsByProducerId.producerIdNotNull}")
    private String producerIdNotNull;
    @Value("${getCountOfCarsByProducerId.producerIdPositive}")
    private String producerIdPositive;
    @Value("${addCar.carNotNull}")
    private String carNotNull;
    @Value("${addCar.carIdNull}")
    private String carIdNull;
    @Value("${addCar.carNameNotNull}")
    private String getCarNameNotNull;
    @Value("${addCar.dateNotNull}")
    private String dateNotNull;

    private static final Logger LOGGER = LogManager.getLogger();

    private CarDao carDao;

    public void setCarDao(CarDao carDao) {
        this.carDao=carDao;
    }

    @Override
    public Car getCarById(Integer carId) {
        Assert.notNull(carId, carIdNotNull);
        Assert.isTrue(carId>0, carIdPositive);
        LOGGER.debug("service: getCarById(): carId={}",carId);
        try {
            return carDao.getCarById(carId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            LOGGER.debug("Car with Id {} does not exist", carId);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Integer getCountCarsById(Integer carId){
        Assert.notNull(carId, carIdNotNull);
        Assert.isTrue(carId>0, carIdPositive);
        LOGGER.debug("service: getCountCarsById(): carId={}",carId);
        try {
            return carDao.getCountCarsById(carId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            LOGGER.debug("Car with Id {} does not exist", carId);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Integer getCountOfCarsByProducerId(Integer producerId) {
        Assert.notNull(producerId, producerIdNotNull);
        Assert.isTrue(producerId>0, producerIdPositive);
        LOGGER.debug("service: getCountOfCarsByProducerId(): producerId={}", producerId);
        try {
            return carDao.getCountOfCarsByProducerId(producerId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            LOGGER.debug("Car with producerId {} does not exist", producerId);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(Date dateBefore, Date dateAfter) {
        return null;
    }

    @Override
    public Integer addCar(Car car) throws DataAccessException {
        Assert.notNull(car, carNotNull);
        Assert.isNull(car.getCarId(), carIdNull);
        LOGGER.debug("service: addCar(): car:  ", car.getCarId());
        Assert.hasText(car.getCarName(), carNameNotNull);
        Assert.notNull(car.getDateOfCreation(), dateNotNull);
        return carDao.addCar(car);
    }

    @Override
    public void updateCar(Car car) {

    }

    @Override
    public void deleteCar(Integer carId) {

    }

    @Override
    public Integer getTotalCountCars() {
        LOGGER.debug("service: getTotalCountCars()");
        return carDao.getTotalCountCars();
    }

    @Override
    public List<Car> getAllCars() {
        LOGGER.debug("service: getAllCars()");
        return carDao.getAllCars();
    }
}
