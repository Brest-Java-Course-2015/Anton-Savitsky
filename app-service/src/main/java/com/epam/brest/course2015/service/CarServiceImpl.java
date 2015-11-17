package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.CarDao;
import com.epam.brest.course2015.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 17.11.15.
 */
@Transactional
public class CarServiceImpl implements CarService{
    private static final Logger LOGGER = LogManager.getLogger();

    private CarDao carDao;

    public void setCarDao(CarDao carDao) {
        this.carDao=carDao;
    }

    @Override
    public Car getCarById(Integer carId) {
        LOGGER.debug("getCarById(): carId={}",carId);
        Assert.notNull(carId, "carId should not be null!");
        return carDao.getCarById(carId);
    }

    @Override
    public Car getCarByName(String carName) {
        return null;
    }

    @Override
    public Integer getCountOfCarsByProducerId(Integer producerId) {
        return null;
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(Date dateBefore, Date dateAfter) {
        return null;
    }

    @Override
    public Integer addCar(Car car) {
        return null;
    }

    @Override
    public void updateCar(Car car) {

    }

    @Override
    public void deleteCar(Integer carId) {

    }

    @Override
    public Integer getTotalCountCars() {
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }
}
