package com.epam.brest.course2015.service;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
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
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CarService carService;

    @Test
    public void testGetCarById() throws Exception {
        int carId=1;
        LOGGER.debug("test: getCarById()");
        Car car= carService.getCarById(carId);
        Assert.notNull(car);
        Assert.isTrue(car.getCarId().equals(carId));
        LOGGER.debug(car.toString());
    }

    @Test
    public void testGetCountOfCarsByProducerId() throws ParseException {
        LOGGER.debug("test: getCountOfCarsByProducerId()");
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
        LOGGER.debug("test: addCar()");
        int sizeBefore = carService.getAllCars().size();
        carService.addCar(car);
        Assert.isTrue(sizeBefore + 1 == carService.getAllCars().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddCarNotNullId() throws ParseException {
        LOGGER.debug("test: AddCarNotNullId case");
        carService.addCar(new Car(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullCar() throws ParseException {
        LOGGER.debug("test: AddNullCar case");
        carService.addCar(new Car());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullName() throws ParseException {
        LOGGER.debug("test: AddCarWithNullName");
        Car car=new Car("", 2, DATE_FORMAT.parse("12/10/2015"));
        carService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullDate(){
        LOGGER.debug("test: AddCarWithNullDate");
        Car car=new Car("5gt", 2, null);
        carService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullProducerId() throws ParseException {
        LOGGER.debug("test: AddCarWithNullProducerId");
        Car car=new Car(null, "5gt", null, DATE_FORMAT.parse("12/10/2015"));
        carService.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullCarId() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullCarId");
        Car car=new Car(null, "5gt", 2, DATE_FORMAT.parse("12/10/2015"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullName() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullName");
        Car car=new Car(1, "", 2, DATE_FORMAT.parse("12/10/2015"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullProducerId() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullProducerId");
        Car car=new Car(1, "5gt", null, DATE_FORMAT.parse("12/10/2015"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNonExistingCar() throws ParseException {
        LOGGER.debug("test: UpdateNonExistingCar");
        Car car=new Car(10, "456", 3, DATE_FORMAT.parse("23/11/2014"));
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullDate() throws ParseException {
        LOGGER.debug("test: UpdateCarWithNullDate");
        Car car=new Car(1, "5gt", 2, null);
        carService.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteCarWithNullId() throws ParseException {
        LOGGER.debug("test: DeleteCarWithNullId()");
        carService.deleteCar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistingCar() throws ParseException {
        LOGGER.debug("test: DeleteNonExistingCar()");
        Integer carId=10;
        carService.deleteCar(carId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetListOfCarsByWrongDateOfCreationInterval() throws ParseException {
        LOGGER.debug("test: getListOfCarsByDateOfCreation()");
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
