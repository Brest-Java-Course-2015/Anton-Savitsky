package com.epam.brest.course2015.transactions;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by antonsavitsky on 27.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-service.xml"})
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CarTransactionsImplTest {
    @Autowired
    private CarTransactions carTransactions;

    @Test
    public void testGetCarById() throws Exception {
        int carId=1;
        Car car= carTransactions.getCarById(carId);
        Assert.notNull(car);
        Assert.isTrue(car.getCarId().equals(carId));
    }

    @Test
    public void testGetCountOfCarsByProducerId() throws ParseException {
        int producerId=0;
        int countOfCarsBefore= carTransactions.getCountOfCarsByProducerId(producerId);
        carTransactions.addCar(new Car("qw", convertToDate("12/12/2015"), new Producer(producerId)));
        int countOfCarsAfter= carTransactions.getCountOfCarsByProducerId(producerId);
        Assert.notNull(countOfCarsBefore);
        Assert.notNull(countOfCarsAfter);
        Assert.isTrue(countOfCarsBefore==countOfCarsAfter-1);
    }

    @Test
    public void testAddCar() throws ParseException {
        Car car=new Car("8gh", convertToDate("13/2/2015"), new Producer(0));
        int sizeBefore = carTransactions.getAllCars().size();
        carTransactions.addCar(car);
        Assert.isTrue(sizeBefore + 1 == carTransactions.getAllCars().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddCarNotNullId() throws ParseException {
        carTransactions.addCar(new Car(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullName() throws ParseException {
        Car car=new Car("", convertToDate("12/10/2015"), new Producer(1));
        carTransactions.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullDate(){
        Car car=new Car("5gt", null, new Producer(1));
        carTransactions.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarWithNullProducerId() throws ParseException {
        Car car=new Car("5gt", convertToDate("12/10/2015"), new Producer());
        carTransactions.addCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullCarId() throws ParseException {
        Car car=new Car(null, "5gt", convertToDate("12/10/2015"));
        carTransactions.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullName() throws ParseException {
        Car car=new Car("", convertToDate("12/10/2015"), new Producer(1));
        carTransactions.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullProducerId() throws ParseException {
        Car car=new Car(1, "5gt", convertToDate("12/10/2015"), new Producer());
        carTransactions.updateCar(car);
    }

    /*
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNonExistingCar() throws ParseException {
        Car car=new Car(10, "456", convertToDate("23/11/2014"), new Producer(1));
        carTransactions.updateCar(car);
    }*/

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCarWithNullDate() throws ParseException {
        Car car=new Car(1, "5gt", null, new Producer(1));
        carTransactions.updateCar(car);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteCarWithNullId() throws ParseException {
        carTransactions.deleteCar(null);
    }

    /*@Test(expected = IllegalArgumentException.class)
    public void testDeleteNonExistingCar() {
        Integer carId=10;
        carTransactions.deleteCar(carId);
    }*/

    @Test(expected = IllegalArgumentException.class)
    public void testGetListOfCarsByWrongDateOfCreationInterval() throws ParseException {
        carTransactions.getListOfCarsByDateOfCreation(convertToDate("22/10/2015"), convertToDate("21/10/2015"));
    }

    @Test
    public void testGetCarsDtoByDate() throws ParseException {
        CarDto dto = carTransactions.getCarsDtoByDate(convertToDate("12/10/2015"), convertToDate("15/10/2015"));
        assertNotNull(dto);
        assertNotNull(dto.getCars());
        assertNotNull(dto.getTotal());
        assertTrue(dto.getCars().size()==2);
        assertEquals(dto.getCars().get(0).getClass(), Car.class);
    }


    @Test
    public void testGetCarsDto(){
        CarDto dto = carTransactions.getCarsDto();
        assertNotNull(dto);
        assertNotNull(dto.getCars());
        assertNotNull(dto.getTotal());
        assertEquals(dto.getCars().get(0).getClass(), Car.class);
    }


    private static LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }


    @Test
    public void testGetPagingList(){
        CarPagingDto carPagingDto= carTransactions.getCarPagingDto(0,2);
    }

    @Test
    public void testGetCarsByPage(){
        carTransactions.getCarsByPage(0,2);
    }
}
