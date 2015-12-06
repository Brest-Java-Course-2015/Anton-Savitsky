package com.epam.brest.course2015.service;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.ProducerDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.rights.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    public void testGetCountOfCarsByProducerId() throws ParseException {
        LOGGER.debug("test: getCountOfCarsByProducerId()");
        int producerId;
        int countOfCarsBefore;
        if(carProducerService.getAllProducers().size()>0) {
            producerId = carProducerService.getAllProducers().get(0).getProducerId();
            countOfCarsBefore= carProducerService.getCountOfCarsByProducerId(producerId);
        }
        else {
            LOGGER.debug("There must be at least 1 producer!");
            return;
        }
        carProducerService.addCar(new Car("qw", producerId, DATE_FORMAT.parse("12/12/2015")));
        int countOfCarsAfter=carProducerService.getCountOfCarsByProducerId(producerId);
        Assert.notNull(countOfCarsBefore);
        Assert.notNull(countOfCarsAfter);
        Assert.isTrue(countOfCarsBefore==countOfCarsAfter-1);
    }

    @Test
    public void testAddCar() throws ParseException {
        Car car=new Car("8gh", carProducerService.getAllProducers().get(0).getProducerId(), DATE_FORMAT.parse("13/2/2015"));
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
        try {
            carProducerService.addCar(car);
        }catch(IllegalArgumentException ex){
            LOGGER.debug(ex.getMessage());
        }
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

    @Test
    public void testGetCarsDtoByDate() throws ParseException {
        CarDto dto = carProducerService.getCarsByDateDto(DATE_FORMAT.parse("12/10/2015"), DATE_FORMAT.parse("15/10/2015"));
        assertNotNull(dto);
        assertNotNull(dto.getCars());
        assertNotNull(dto.getTotal());
        assertTrue(dto.getCars().size()==2);
        assertEquals(dto.getCars().get(0).getClass(), Car.class);
    }

    @Test
    public void testGetCarsDto(){
        CarDto dto = carProducerService.getCarsDto();
        assertNotNull(dto);
        assertNotNull(dto.getCars());
        assertNotNull(dto.getTotal());
        assertEquals(dto.getCars().get(0).getClass(), Car.class);
    }

    //producer's methods testing
    @Test
    public void testGetProducerById() throws Exception {
        int producerId=0;
        LOGGER.debug("test: getProducerById()");
        Producer producer= carProducerService.getProducerById(producerId);
        Assert.notNull(producer);
        Assert.isTrue(producer.getProducerId()==0);
        LOGGER.debug(producer.toString());
    }

    @Test
    public void testGetProducersDto(){
        LOGGER.debug("test: getProducersDto()");
        ProducerDto producerdto=carProducerService.getProducersDto();
        Assert.notNull(producerdto);
        Assert.notNull(producerdto.getProducers());
        Assert.isTrue(producerdto.getProducers().get(0).getClass().equals(Producer.class));
    }

    @Test
    public void testDeleteProducer() {
        LOGGER.debug("test: deleteProducer()");
        int sizeBeforeDeleting=carProducerService.getProducersTotalCount();
        if(sizeBeforeDeleting>0)
        carProducerService.deleteProducer(0);
        else {
            LOGGER.debug("Error: there must be at least one producer!");
            return;}
        Assert.isTrue(sizeBeforeDeleting-1==carProducerService.getProducersTotalCount());
    }

    @Test
    public void testAddProducer(){
        LOGGER.debug("test: addProducer()");
        Producer producer=new Producer("ferrari","italy");
        carProducerService.addProducer(producer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddProducerNotNullId(){
        LOGGER.debug("test: addProducer() for a failure when adding producer with not null id");
        carProducerService.addProducer(new Producer(1,"ferrari","italy"));
    }

    @Test
    public void testUpdateProducer() {
        LOGGER.debug("test: updateProducer()");
        Producer producer;
        if (carProducerService.getProducersTotalCount() > 0){
            carProducerService.updateProducer(producer=new Producer(carProducerService.getAllProducers().get(0).getProducerId(), "updatedName", "updatedCountry"));
        }else{
            LOGGER.debug("There must be at least one producer to be updated!");
            return;
        }
        Assert.isTrue(producer.getProducerId().equals(carProducerService.getAllProducers().get(0).getProducerId()));
        Assert.isTrue(producer.getCountry().equals(carProducerService.getAllProducers().get(0).getCountry()));
        Assert.isTrue(producer.getProducerName().equals(carProducerService.getAllProducers().get(0).getProducerName()));
    }

}
