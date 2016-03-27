package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by antonsavitsky on 3/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-soap.xml"})
public class CarSoapServiceConsumerIT {

    private static final Car testUpdateCar=
            new Car(1, "updatedCar", convertToDate("2015-10-10"),
                    new Producer(0, "updateProducer", "updateCountry"));

    @Autowired
    private CarSoapConsumer carSoapConsumer;

    @Test
    public void testGetCarById(){
        carSoapConsumer.getCarById(1);
    }

    @Test
    public void testUpdateCar(){
        carSoapConsumer.updateCar(testUpdateCar);
    }

    public static LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
