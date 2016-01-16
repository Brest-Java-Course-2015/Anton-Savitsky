package com.epam.brest.course2015.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by antonsavitsky on 09.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core.xml"})
public class CarTest {
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private Car car;

    @Test
    public void testGetCarId(){
        car.setCarId(1);
        assertEquals((Integer)1, car.getCarId());
    }

    @Test
    public void testGetCarName() {
        car.setCarName("5dhr");
        assertEquals("5dhr", car.getCarName());
    }

    @Test
    public void testGetProducerId(){
        car.setProducerId(1);
        assertEquals((Integer)1, car.getProducerId());
    }

    @Test
    public void testGetDateOfCreation(){
        Date date=null;
        try {
            date=DATE_FORMAT.parse("11/12/2015");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        car.setDateOfCreation(date);
        assertEquals(date, car.getDateOfCreation());
    }
}
