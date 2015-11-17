package com.epam.brest.course2015.service;

import com.epam.brest.course2015.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by antonsavitsky on 16.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional()
public class CarServiceImplTest {
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");


    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CarService carService;

    @Test
    public void testGetCarById() throws Exception {
        int carId=1;
        LOGGER.debug("test: getCarById()");
        Car car=carService.getCarById(carId);
        Assert.notNull(car);
        Assert.isTrue(car.getCarId().equals(carId));
        LOGGER.debug(car.toString());
    }
}
