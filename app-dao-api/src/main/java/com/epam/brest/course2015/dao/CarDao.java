package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public interface CarDao {
    Car getCarById(Integer carId);
    Car getCarByName(String carName);
    Integer getCountOfCarsByProducerId(Integer producerId);
    List<Car> getListOfCarsByDateOfCreation(Date dateBefore, Date dateAfter);
    Integer addCar(Car car);
    void updateCar(Car car);
    void deleteCar(Integer carId);
    Integer getTotalCountCars();
    List<Car> getAllCars();
}
