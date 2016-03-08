package com.epam.brest.course2015.domain;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 * Created by antonsavitsky on 09.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core.xml"})
public class CarTest {
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
/*
    @Test
    public void testGetProducerId(){
        car.setProducerId(1);
        assertEquals((Integer)1, car.getProducerId());
    }
*/
    @Test
    public void testGetDateOfCreation(){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
        LocalDate ld=fmt.parseLocalDate("10/10/2015");
        car.setDateOfCreation(ld);
        assertEquals(ld, car.getDateOfCreation());
    }
}
