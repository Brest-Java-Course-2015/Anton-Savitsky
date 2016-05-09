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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
    @Loggable
    public List<Car> getAllCars() {
        return restTemplate.getForObject(restServicesPrefix+"/car",List.class);
    }

    @Override
    @Loggable
    public Car getCarById(Integer id) {
        ResponseEntity<Car> responseEntity=restTemplate
                .getForEntity(restServicesPrefix + "/car/{id}", Car.class, id);
        return responseEntity.getBody();
    }

    @Override
    @Loggable
    public Integer addCar(Car car) {
        return restTemplate.postForObject(restServicesPrefix+"/car", car, Car.class).getCarId();
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(String dateBefore, String dateAfter) {
        return null;
    }

    @Override
    @Loggable
    public void deleteCar(Integer id) {
        restTemplate.delete(restServicesPrefix+"/car/{id}", id);
    }

    @Override
    @Loggable
    public CarDto getCarsDto() {
        return restTemplate.getForObject(restServicesPrefix+"/car/dto", CarDto.class);
    }

    @Override
    @Loggable
    public CarDto getCarsDtoByDate(String dateBefore, String dateAfter) {
        return restTemplate.getForObject(restServicesPrefix+
                "/car/dto/date?dateBefore={dateBefore}&dateAfter={dateAfter}",
                CarDto.class, dateBefore, dateAfter);
    }

    @Override
    @Loggable
    public CarPagingDto getInitPaging(Integer from, Integer to) {
        return restTemplate.getForObject(restServicesPrefix+
                "/car/initpaging?from={from}&to={to}", CarPagingDto.class,
                from, to);
    }

    @Override
    @Loggable
    public CarPagingDto getNextPage(Integer from, Integer to) {
        return restTemplate.getForObject(restServicesPrefix+
                "/car/nextpage?from={from}&to={to}", CarPagingDto.class,
                from, to);
    }

    @Override
    @Loggable
    public void saveUpload(CommonsMultipartFile data, Integer id){
        System.out.println("File: "+data.getBytes());
        restTemplate.postForObject(restServicesPrefix+"/car/upload?id={id}", data.getBytes(), byte[].class, id);
    }
}
