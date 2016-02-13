package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarPagingDto;
import org.joda.time.LocalDate;
import org.springframework.cglib.core.Local;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public interface CarDao {
    Integer getTotalCount();
    List<Car> getPagingList(Integer min, Integer max);
    Car getCarById(Integer carId);
    Integer getCountOfCarsByProducerId(Integer producerId);
    List<Car> getListOfCarsByDateOfCreation(LocalDate dateBefore, LocalDate dateAfter);
    Integer addCar(Car car);
    void updateCar(Car car);
    void deleteCar(Integer carId);
    List<Car> getAllCars();
    Integer getCountCarsById(Integer carId);
}
