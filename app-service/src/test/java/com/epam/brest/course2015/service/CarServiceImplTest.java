package com.epam.brest.course2015.service;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by antonsavitsky on 27.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-service.xml"})
@Transactional()
public class CarServiceImplTest {
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    @Autowired
    private CarService carService;

    @Test
    public void testGetCarById() throws Exception {
        int carId=1;
        Car car= carService.getCarById(carId);
        Assert.notNull(car);
        Assert.isTrue(car.getCarId().equals(carId));
    }

    @Test
    public void testGetCountOfCarsByProducerId() throws ParseException {
        int producerId=0;
        int countOfCarsBefore=carService.getCountOfCarsByProducerId(producerId);
        carService.addCar(new Car("qw", producerId, DATE_FORMAT.parse("12/12/2015")));
        int countOfCarsAfter=carService.getCountOfCarsByProducerId(producerId);
        Assert.notNull(countOfCarsBefore);
        Assert.notNull(countOfCarsAfter);
        Assert.isTrue(countOfCarsBefore==countOfCarsAfter-1);
    }

    @Test
    public void testAddCar() throws ParseException {
        Car car=new Car("8gh", 0, DATE_FORMAT.parse("13/2/2015"));
        int sizeBefore = carService.getAllCars().size();
        carService.addCar(car);
        Assert.isTrue(sizeBefore + 1 == carService.getAllCars().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddCarNotNullId() throws ParseException {
        carService.addCar(new Car(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullCar() throws ParseException {
        carService.addCar(new Car());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullName() throws ParseException {
        Car car=new Car("", 2, DATE_FORMAT.parse("12/10/2015"));
        carService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullDate(){
        Car car=new Car("5gt", 2, null);
        carService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullProducerId() throws ParseException {
        Car car=new Car(null, "5gt", null, DATE_FORMAT.parse("12/10/2015"));
        carService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullCarId() throws ParseException {
        Car car=new Car(null, "5gt", 2, DATE_FORMAT.parse("12/10/2015"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullName() throws ParseException {
        Car car=new Car(1, "", 2, DATE_FORMAT.parse("12/10/2015"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullProducerId() throws ParseException {
        Car car=new Car(1, "5gt", null, DATE_FORMAT.parse("12/10/2015"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNonExistingCar() throws ParseException {
        Car car=new Car(10, "456", 3, DATE_FORMAT.parse("23/11/2014"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullDate() throws ParseException {
        Car car=new Car(1, "5gt", 2, null);
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteCarWithNullId() throws ParseException {
        carService.deleteCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistingCar() throws ParseException {
        Integer carId=10;
        carService.deleteCar(carId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetListOfCarsByWrongDateOfCreationInterval() throws ParseException {
        carService.getListOfCarsByDateOfCreation(DATE_FORMAT.parse("22/10/2015"), DATE_FORMAT.parse("21/10/2015"));
    }

    @Test
    public void testGetCarsDtoByDate() throws ParseException {
        CarDto dto = carService.getCarsByDateDto(DATE_FORMAT.parse("12/10/2015"), DATE_FORMAT.parse("15/10/2015"));
        assertNotNull(dto);
        assertNotNull(dto.getCars());
        assertNotNull(dto.getTotal());
        assertTrue(dto.getCars().size()==2);
        assertEquals(dto.getCars().get(0).getClass(), Car.class);
    }

    @Test
    public void testGetCarsDto(){
        CarDto dto = carService.getCarsDto();
        assertNotNull(dto);
        assertNotNull(dto.getCars());
        assertNotNull(dto.getTotal());
        assertEquals(dto.getCars().get(0).getClass(), Car.class);
    }
}
