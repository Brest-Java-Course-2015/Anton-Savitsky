package com.epam.brest.course2015.service;

import java.util.Date;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.ProducerDto;

import java.util.List;

/**
 * Created by antonsavitsky on 17.11.15.
 */
public interface CarProducerService {
    Car getCarById(Integer carId);
    Integer getCountOfCarsByProducerId(Integer producerId);
    List<Car> getListOfCarsByDateOfCreation(Date dateBefore, Date dateAfter);
    Integer addCar(Car car);
    void updateCar(Car car);
    void deleteCar(Integer carId);
    Integer getTotalCountCars();
    List<Car> getAllCars();
    Integer getCountCarsById(Integer carId);
    CarDto getCarsDto();
    CarDto getCarsByDateDto(Date dateBefore, Date dateAfter);

    Producer getProducerById(Integer producerId);
    Integer addProducer(Producer producer);
    void updateProducer(Producer producer);
    Integer getProducersTotalCount();
    void deleteProducer(Integer producerId);
    List<Producer> getAllProducers();
    ProducerDto getProducersDto();
}
