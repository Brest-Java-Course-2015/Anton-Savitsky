package com.epam.brest.course2015.dao;

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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by antonsavitsky on 02.03.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-jpa-config.xml"})
@Transactional
public class TestCarDaoJpaImpl {
    public static Car testAddCar = new Car("newCar", convertToDate("23/06/2015"), new Producer(1), new byte[100]);
    public static Car testUpdateCar = new Car(0, "updatedCar", convertToDate("23/02/2013"), new Producer(2), new byte[100]);

    @Autowired
    private CarDao carDao;

    @Test
    public void testAddCar(){
        int i=carDao.addCar(testAddCar);
        System.out.println("i!!!:"+i);
        //Assert.isTrue(i == 3);
    }

    @Test
    public void testUpdateCar(){
        carDao.updateCar(testUpdateCar);
        Assert.isTrue(carDao.getCarById(testUpdateCar.getCarId()).getCarName().equals("updatedCar"));
    }

    @Test
    public void testGetCarById(){
        Car car=carDao.getCarById(1);
        Assert.isTrue(car.getCarId().equals(1));
    }

    @Test
    public void testDeleteCar(){
        carDao.deleteCar(1);
        Assert.isNull(carDao.getCarById(1));
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

    @Test
    public void testGetListOfCarsByDate(){
        carDao.getListOfCarsByDateOfCreation(convertToDate("12/10/2015"), convertToDate("15/10/2015"));
    }

    @Test
    public void testGetPagingList(){
        List<Car> list=carDao.getPagingList(1, 2);
        Assert.notNull(list);
        Assert.isTrue(list.size() == 2);
    }

    private static LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }

    @Test
    public void testSaveUpload() {
        byte[] data = new byte[100];
        carDao.saveUpload(data, 0);
        Assert.isTrue(carDao.getCarById(0).getPicture() == data);
        System.out.println(carDao.getCarById(0).getPicture());
    }
}
