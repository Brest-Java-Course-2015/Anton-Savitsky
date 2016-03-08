package com.epam.brest.course2015.dao.jpa;

import com.epam.brest.course2015.dao.CarDao;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by antonsavitsky on 02.03.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-jpa-config.xml"})
@Transactional
public class TestCarJpaDao {
    public static Car testAddCar = new Car("newCar", convertToDate("23/06/2015"), new Producer());
    public static Car testUpdateCar = new Car(1,"updatedCar", convertToDate("23/02/2013"), new Producer());

    @Autowired
    private CarDao carDao;

    @Test
    public void testAddCar(){
        int i=carDao.addCar(testAddCar);
        Assert.isTrue(i == 3);
    }

    @Test
    public void testUpdateCar(){
        carDao.updateCar(testUpdateCar);
        //Assert.isTrue(carDao.);
    }

    @Test
    public void testGetCarById(){
        Car car=carDao.getCarById(1);
        Assert.isTrue(car.getCarId().equals(1));
    }

    @Test
    public void testDeleteCar(){
        carDao.deleteCar(1);
        System.out.println(carDao.getCarById(1));
    }

    @Test
    public void testGetTotalCount(){
        Integer i=carDao.getTotalCount();
        Assert.notNull(i);
        Assert.isTrue(i.equals(3));
    }

    @Test
    public void testGetCountOfCarsByProdId(){
        Integer i=carDao.getCountOfCarsByProducerId(0);
        Assert.notNull(i);
        Assert.isTrue(i.equals(3));
    }

    @Test
    public void testGetAllCars(){
        List<Car> cars=carDao.getAllCars();
        Assert.notNull(cars);
        Assert.isTrue(cars.size()==3);
    }

    @Test
    public void testGetCountById(){
        Integer i=carDao.getCountCarsById(0);
        Assert.notNull(i);
        Assert.isTrue(i.equals(1));
    }

    private static LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
