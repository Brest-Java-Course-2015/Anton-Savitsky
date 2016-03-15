package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
import com.epam.brest.course2015.provider.CarServiceConsumer;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by antonsavitsky on 3/14/16.
 */
public class CarRestConsumer implements CarServiceConsumer {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${restServicesPrefix}")
    private String restServicesPrefix;

    @Loggable
    @Override
    public void updateCar(Car car) {
        try {
            restTemplate.put(new URI(restServicesPrefix+"/car"), car);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }

    @Override
    @Loggable
    public Car getCarById(Integer id) {
        ResponseEntity<Car> responseEntity=restTemplate
                .getForEntity(restServicesPrefix + "/car/{id}", Car.class, id);
        return responseEntity.getBody();
    }

    @Override
    public Integer addCar(Car car) {
        return null;
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(String dateBefore, String dateAfter) {
        return null;
    }

    @Override
    public void deleteCar(Integer id) {

    }

    @Override
    public CarDto getCarsDto() {
        return null;
    }

    @Override
    public CarDto getCarsDtoByDate(String dateBefore, String dateAfter) {
        return null;
    }

    @Override
    public CarPagingDto getInitPaging(Integer from, Integer to) {
        return null;
    }

    @Override
    public CarPagingDto getNextPage(Integer from, Integer to) {
        return null;
    }
}
