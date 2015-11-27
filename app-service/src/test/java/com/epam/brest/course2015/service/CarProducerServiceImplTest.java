package com.epam.brest.course2015.service;

import com.epam.brest.course2015.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class CarProducerServiceImplTest {
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CarProducerService carProducerService;

    @Test
    public void testGetCarById() throws Exception {
        int carId=1;
        LOGGER.debug("test: getCarById()");
        Car car= carProducerService.getCarById(carId);
        Assert.notNull(car);
        Assert.isTrue(car.getCarId().equals(carId));
        LOGGER.debug(car.toString());
    }

    @Test
    public void testGetCountOfCarsByProducerId(){
        int producerId=1;
        LOGGER.debug("test: getCountOfCarsByProducerId()");
        int countOfCars= carProducerService.getCountOfCarsByProducerId(producerId);
        Assert.notNull(countOfCars);
        Assert.isTrue(countOfCars==2);
    }

    @Test
    public void testAddCar() throws ParseException {
        Car car=new Car("8gh", 2, DATE_FORMAT.parse("13/2/2015"));
        LOGGER.debug("test: addCar()");
        int sizeBefore = carProducerService.getAllCars().size();
        carProducerService.addCar(car);
        Assert.isTrue(sizeBefore + 1 == carProducerService.getAllCars().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddCarNotNullId() throws ParseException {
        LOGGER.debug("test: AddCarNotNullId case");
        carProducerService.addCar(new Car(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullCar() throws ParseException {
        LOGGER.debug("test: AddNullCar case");
        carProducerService.addCar(new Car());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullName() throws ParseException {
        LOGGER.debug("test: AddCarWithNullName");
        Car car=new Car("", 2, DATE_FORMAT.parse("12/10/2015"));
        carProducerService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullDate(){
        LOGGER.debug("test: AddCarWithNullDate");
        Car car=new Car("5gt", 2, null);
        carProducerService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullProducerId() throws ParseException {
        LOGGER.debug("test: AddCarWithNullProducerId");
        Car car=new Car(null, "5gt", null, DATE_FORMAT.parse("12/10/2015"));
        carProducerService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullCarId() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullCarId");
        Car car=new Car(null, "5gt", 2, DATE_FORMAT.parse("12/10/2015"));
        carProducerService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullName() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullName");
        Car car=new Car(1, "", 2, DATE_FORMAT.parse("12/10/2015"));
        carProducerService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullProducerId() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullProducerId");
        Car car=new Car(1, "5gt", null, DATE_FORMAT.parse("12/10/2015"));
        carProducerService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNonExistingCar() throws ParseException {
        LOGGER.debug("test: UpdateNonExistingCar");
        Car car=new Car(10, "456", 3, DATE_FORMAT.parse("23/11/2014"));
        carProducerService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullDate() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullDate");
        Car car=new Car(1, "5gt", 2, null);
        carProducerService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteCarWithNullId() throws ParseException {
        LOGGER.debug("test: DeleteCarWithNullId()");
        carProducerService.deleteCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistingCar() throws ParseException {
        LOGGER.debug("test: DeleteNonExistingCar()");
        Integer carId=10;
        carProducerService.deleteCar(carId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetListOfCarsByWrongDateOfCreationInterval() throws ParseException {
        LOGGER.debug("test: getListOfCarsByDateOfCreation()");
        carProducerService.getListOfCarsByDateOfCreation(DATE_FORMAT.parse("22/10/2015"), DATE_FORMAT.parse("21/10/2015"));
    }
}
