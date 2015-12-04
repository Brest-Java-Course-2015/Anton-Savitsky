package com.epam.brest.course2015.service;

import com.epam.brest.course2015.dao.CarDao;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class CarProducerServiceImpl implements CarProducerService {
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
        LOGGER.debug("getCarById(): carId={}",carId);
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
        LOGGER.debug("getCountCarsById(): carId={}",carId);
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
        LOGGER.debug("getCountOfCarsByProducerId(): producerId={}", producerId);
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
        LOGGER.debug("getListOfCarsByDateOfCreation(): dateBefore={}, dateAfter={}", dateBefore,dateAfter);
        Assert.notNull(dateBefore, dateNotNull);
        Assert.notNull(dateAfter, dateNotNull);
        Assert.isTrue(dateAfter.after(dateBefore), "DateAfter must be after DateBefore!");
        return carDao.getListOfCarsByDateOfCreation(dateBefore,dateAfter);
    }

    @Override
    public Integer addCar(Car car) throws DataAccessException {
        LOGGER.debug("addCar(): car={}",car.getCarId());
        Assert.notNull(car, carNotNull);
        Assert.isNull(car.getCarId(), carIdNull);
        Assert.notNull(car.getProducerId(), producerIdNotNull);
        Assert.hasText(car.getCarName(), carNameNotNull);
        Assert.notNull(car.getDateOfCreation(), dateNotNull);
        return carDao.addCar(car);
    }

    @Override
    public void updateCar(Car car) {
        LOGGER.debug("updateCar(): carId={}  ", car.getCarId());
        Assert.notNull(car, carNotNull);
        Assert.notNull(car.getCarId(), carIdNotNull);
        Integer carIdToUpdate=car.getCarId();
        try{
            carDao.getCarById(carIdToUpdate);
        } catch (DataAccessException dae){
            throw new IllegalArgumentException("Car doesn't exist!");
        }
        Assert.hasText(car.getCarName(), carNameNotNull);
        Assert.notNull(car.getDateOfCreation(), dateNotNull);
        Assert.notNull(car.getProducerId(), producerIdNotNull);
        carDao.updateCar(car);
    }

    @Override
    public void deleteCar(Integer carId) {
        LOGGER.debug("deleteCar(): carId={}", carId);
        try{
            carDao.getCarById(carId);
        } catch (DataAccessException dae){
            throw new IllegalArgumentException("Car doesn't exist!");
        }
        Assert.notNull(carId, carIdNotNull);
        carDao.deleteCar(carId);
    }

    @Override
    public Integer getTotalCountCars() {
        LOGGER.debug("getTotalCountCars()");
        return carDao.getTotalCountCars();
    }

    @Override
    public List<Car> getAllCars() {
        LOGGER.debug("getAllCars()");
        return carDao.getAllCars();
    }

    @Override
    public CarDto getCarsDto(){
        LOGGER.debug("getCarsDto()");
        CarDto carDto = new CarDto();
        carDto.setTotal(carDao.getTotalCountCars());
        if(carDto.getTotal()>0){
            carDto.setCars(carDao.getAllCars());
        }
        else carDto.setCars(Collections.<Car>emptyList());
        return carDto;
    }

    @Override
    public CarDto getCarsByDateDto(Date dateBefore, Date dateAfter) {
        LOGGER.debug("getCarsByDateDto()");
        CarDto carDto=new CarDto();
        carDto.setTotal(getListOfCarsByDateOfCreation(dateBefore, dateAfter).size());
        if(carDto.getTotal()>0){
            carDto.setCars(getListOfCarsByDateOfCreation(dateBefore,dateAfter));
        } else {
            carDto.setCars(Collections.<Car>emptyList());
        }
        return carDto;
    }
}
