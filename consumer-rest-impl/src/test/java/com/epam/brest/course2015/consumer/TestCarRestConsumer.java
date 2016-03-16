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

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by antonsavitsky on 3/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-rest-consumer-config.xml"})
@Transactional
public class TestCarRestConsumer {

    private static Car updateCar;
    private static Car addCar;

    @Autowired
    private CarServiceConsumer carServiceConsumer;

    @Before
    public void setUp(){
        updateCar=new Car(0, "updatedName",
                convertToDate("19/10/2015"), new Producer(1));
        addCar=new Car("addedName",
                convertToDate("16/10/2015"), new Producer(1));
    }

    @Test
    public void testGetCarById(){
        Car car=carServiceConsumer.getCarById(0);
        Assert.notNull(car);
        Assert.isTrue(car.getCarId()==0);
    }


    @Test
    public void testUpdateCar(){
        carServiceConsumer.updateCar(updateCar);
    }


    @Test
    public void testGetAllCars(){
        List<Car> list=carServiceConsumer.getAllCars();
        Assert.notNull(list);
        Assert.isTrue(list.size()==3);
    }

    @Test
    public void testDeleteCar(){
        int before=carServiceConsumer.getAllCars().size();
        carServiceConsumer.deleteCar(1);
        int after=carServiceConsumer.getAllCars().size();
        Assert.isTrue(before==(after+1));
    }

    @Test
    public void testGetCarsDto(){
        carServiceConsumer.getCarsDto();
    }

    @Test
    public void testGetCarsDtoByDate(){
        carServiceConsumer.getCarsDtoByDate("10/10/2015","16/10/2015");
    }

    @Test
    public void testGetInitPaging(){
        carServiceConsumer.getInitPaging(0,2);
    }

    @Test
    public void testAddCar(){
        carServiceConsumer.addCar(addCar);
    }

    @Test
    public void testGetNextPage(){
        carServiceConsumer.getNextPage(0,2);
    }

    public LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
