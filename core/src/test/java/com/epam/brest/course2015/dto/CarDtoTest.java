package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by antonsavitsky on 16.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core.xml"})
public class CarDtoTest {
    @Autowired
    private CarDto carDto;

    @Test
    public void testGetTotal(){
        carDto.setTotal(2);
        assertEquals(carDto.getTotal(), (Integer)2);
    }
    @Test
    public void testGetCars(){
        List<Car> carList=Arrays.<Car>asList(new Car(1), new Car(2));
        carDto.setCars(carList);
        assertEquals(carDto.getCars(),carList);
    }
}
