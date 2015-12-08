package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hsqldb.rights.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by antonsavitsky on 09.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional()
public class CarDaoImplTest  {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private CarDao carDao;
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void testGetCarById(){
        LOGGER.debug("test: getCarById()");
        int carId = 1;
        Car car = carDao.getCarById(carId);
        LOGGER.debug(car.toString());
        assertNotNull(car);
        assertTrue(car.getCarId().equals(carId));
    }

    @Test
    public void testGetCountCarsById(){
        LOGGER.debug("test: getCountCarsById()");
        int countCars=carDao.getCountCarsById(1);
        assertNotNull(countCars);
        assertTrue(countCars==1);
    }

    @Test
    public void testGetCountOfCarsByProducerId(){
        LOGGER.debug("test: getCountOfCarsByProducerId()");
        int expectedCountOfCars=3;
        int producerId=0;
        Integer countOfCars=carDao.getCountOfCarsByProducerId(producerId);
        LOGGER.debug("expectedCountOfCars: "+expectedCountOfCars);
        assertNotNull(countOfCars);
        LOGGER.debug("countOfCars: "+countOfCars);
        assertTrue(countOfCars.equals(expectedCountOfCars));
    }

    @Test
    public void testGetListOfCarsByDateOfCreation(){
        LOGGER.debug("test: getListOfCarsByDateOfCreation()");
        Date dateBefore= null;
        Date dateAfter= null;
        Date expectedDate1=null;
        Date expectedDate2=null;
        try {
            dateBefore = DATE_FORMAT.parse("12/10/2015");
            dateAfter = DATE_FORMAT.parse("15/10/2015");
            expectedDate1=DATE_FORMAT.parse("14/10/2015");
            expectedDate2=DATE_FORMAT.parse("13/10/2015");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Car> listOfCars=carDao.getListOfCarsByDateOfCreation(dateBefore, dateAfter);
        LOGGER.debug("listofcars: ");
        for(int i=0;i<listOfCars.size();i++)
            LOGGER.debug(listOfCars.get(i).toString());
        assertNotNull(listOfCars);
        assertTrue(listOfCars.get(0).getDateOfCreation().compareTo(expectedDate1)==0);
        assertTrue(listOfCars.get(1).getDateOfCreation().compareTo(expectedDate2)==0);
    }

    @Test
    public void testAddCar() throws Exception {
        Car car=new Car("9rt", 0, DATE_FORMAT.parse("11/12/2015"));
        LOGGER.debug("test: addCar()");
        Integer carId = carDao.addCar(car);
        assertNotNull(carId);
        Car addedCar = carDao.getCarById(carId);
        assertNotNull(addedCar);
        LOGGER.debug("added car: carId={}",carId);
        LOGGER.debug(addedCar.toString());
        assertTrue(carId.equals(addedCar.getCarId()));
        assertTrue(car.getCarName().equals(addedCar.getCarName()));
        assertTrue(car.getProducerId().equals(addedCar.getProducerId()));
        assertNotNull(car.getDateOfCreation());
    }

    @Test
    public void testGetAllCars() throws Exception {
        LOGGER.debug("test: getAllCars()");
        List<Car> cars = carDao.getAllCars();
        assertTrue(cars.size() == 3);
    }

    @Test
    public void testUpdateCar(){
        LOGGER.debug("test: updateCar()");
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
        LOGGER.debug("test: getTotalCountCars()");
        List<Car> cars=carDao.getAllCars();
        assertNotNull(cars);
        int total=carDao.getTotalCountCars();
        assertTrue(cars.size()==total);
        LOGGER.debug(total);
    }

    @Test
    public void testDeleteCar() throws Exception {
        LOGGER.debug("test :deleteCar()");
        int sizeBeforeDelete = carDao.getTotalCountCars();
        assertTrue(sizeBeforeDelete > 0);
        carDao.deleteCar(1);
        assertTrue((sizeBeforeDelete - 1) == carDao.getAllCars().size());
    }
}
