package com.epam.brest.course2015.service;

import java.util.Date;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.ProducerDto;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by antonsavitsky on 17.11.15.
 */
public interface CarService {
    Car getCarById(Integer carId);
    Integer getCountOfCarsByProducerId(Integer producerId);
    List<Car> getListOfCarsByDateOfCreation(LocalDate dateBefore, LocalDate dateAfter);
    Integer addCar(Car car);
    void updateCar(Car car);
    void deleteCar(Integer carId);
    List<Car> getAllCars();
    CarDto getCarsDto();
    CarDto getCarsByDateDto(LocalDate dateBefore, LocalDate dateAfter);
}
