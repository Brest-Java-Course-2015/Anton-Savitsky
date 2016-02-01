package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by antonsavitsky on 09.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-dao.xml"})
@Transactional()
public class CarDaoImplTest  {
    @Autowired
    private CarDao carDao;

    @Test
    public void testGetCarById(){
        int carId = 1;
        Car car = carDao.getCarById(carId);
        assertNotNull(car);
        assertTrue(car.getCarId().equals(carId));
    }

    @Test
    public void testGetCountCarsById(){
        int countCars=carDao.getCountCarsById(1);
        assertNotNull(countCars);
        assertTrue(countCars==1);
    }

    @Test
    public void testGetCountOfCarsByProducerId(){
        int expectedCountOfCars=3;
        int producerId=0;
        Integer countOfCars=carDao.getCountOfCarsByProducerId(producerId);
        assertNotNull(countOfCars);
        assertTrue(countOfCars.equals(expectedCountOfCars));
    }

    @Test
    public void testGetListOfCarsByDateOfCreation(){
        LocalDate dateBefore=convertToDate("12/10/2015");
        LocalDate dateAfter=convertToDate("15/10/2015");
        LocalDate expectedDate1=convertToDate("14/10/2015");
        LocalDate expectedDate2=convertToDate("13/10/2015");
        List<Car> listOfCars=carDao.getListOfCarsByDateOfCreation(dateBefore, dateAfter);
        assertNotNull(listOfCars);
        assertTrue(listOfCars.get(0).getDateOfCreation().compareTo(expectedDate1)==0);
        assertTrue(listOfCars.get(1).getDateOfCreation().compareTo(expectedDate2)==0);
    }

    @Test
    public void testAddCar() throws Exception {
        Car car=new Car("9rt", 0, convertToDate("11/12/2015"));
        Integer carId = carDao.addCar(car);
        assertNotNull(carId);
        Car addedCar = carDao.getCarById(carId);
        assertNotNull(addedCar);
        assertTrue(carId.equals(addedCar.getCarId()));
        assertTrue(car.getCarName().equals(addedCar.getCarName()));
        assertTrue(car.getProducerId().equals(addedCar.getProducerId()));
        assertNotNull(car.getDateOfCreation());
    }

    @Test
    public void testGetAllCars() throws Exception {
        List<Car> cars = carDao.getAllCars();
        assertTrue(cars.size() == 3);
    }

    @Test
    public void testUpdateCar(){
        Car car=carDao.getCarById(2);
        assertNotNull(car);
        car.setCarName("6yu");
        carDao.updateCar(car);
        Car updatedCar = carDao.getCarById(car.getCarId());
        assertTrue(car.getCarId().equals(updatedCar.getCarId()));
        assertTrue(car.getCarName().equals(updatedCar.getCarName()));
        assertTrue(car.getProducerId().equals(updatedCar.getProducerId()));
        assertTrue(car.getDateOfCreation().equals(updatedCar.getDateOfCreation()));
    }

    @Test
    public void testGetTotalCountCars(){
        List<Car> cars=carDao.getAllCars();
        assertNotNull(cars);
        int total=carDao.getTotalCountCars();
        assertTrue(cars.size()==total);
    }

    @Test
    public void testDeleteCar() throws Exception {
        int sizeBeforeDelete = carDao.getTotalCountCars();
        assertTrue(sizeBeforeDelete > 0);
        carDao.deleteCar(1);
        assertTrue((sizeBeforeDelete - 1) == carDao.getAllCars().size());
    }

    private static LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
