package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.provider.CarServiceConsumer;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.util.Assert;

/**
 * Created by antonsavitsky on 3/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-rest-consumer-config.xml"})
public class TestCarRestConsumer {

    private static Car updateCar;

    @Autowired
    private CarServiceConsumer carServiceConsumer;

    @Before
    public void setUp(){
        updateCar=new Car(0, "updatedName", convertToDate("19/10/2015"), new Producer(1));
    }

    @Test
    public void testGetCarById(){
        Car car=carServiceConsumer.getCarById(0);
        Assert.notNull(car);
        Assert.isTrue(car.getCarId()==0);
        //restTemplate.getForEntity("http://localhost:8081/rest/car/{id}", Car.class, 0);
    }

    @Test
    public void testUpdateCar(){
        carServiceConsumer.updateCar(updateCar);
    }

    public LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
