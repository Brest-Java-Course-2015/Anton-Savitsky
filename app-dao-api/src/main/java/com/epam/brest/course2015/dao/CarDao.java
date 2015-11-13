package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public interface CarDao {
    public Car getCarById(Integer carId);
    public Car getCarByName(String carName);
    public Integer getCountOfCarsByProducerId(Integer producerId);
    public List<Car> getListOfCarsByDateOfCreation(Date dateBefore, Date dateAfter);
    public Integer addCar(Car car);
}
