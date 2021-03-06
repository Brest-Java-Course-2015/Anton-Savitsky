package com.epam.brest.course2015.transactions;

import com.epam.brest.course2015.dao.CarDao;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Created by antonsavitsky on 25.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-service-mock-test.xml"})
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CarTransactionsImplMockTest {
    private  Car testCar1;
    private Car testCar2;
    private Producer testProducer1;

    @Autowired
    private CarTransactions carTransactions;

    @Autowired
    private CarDao carMockDao;

    @Before
    public void setUp() throws ParseException {
        testCar1=new Car(1,"name1",convertToDate("11/11/2011"),new Producer(1));
        testCar2=new Car("name2",convertToDate("11/11/2012"),new Producer(1));
        testProducer1=new Producer(1, "producer1", "country1");
    }

    @After
    public void clean() {
        verify(carMockDao);
        reset(carMockDao);
    }

    @Test
    public void testGetCarById(){
        expect(carMockDao.getCarById(testCar1.getCarId())).andReturn(testCar1);
        replay(carMockDao);
        Car resultCar= carTransactions.getCarById(testCar1.getCarId());
        Assert.isTrue(resultCar==testCar1);
    }

    @Test
    public void testGetCountOfCarsByProducerId(){
        expect(carMockDao.getCountOfCarsByProducerId(testProducer1.getProducerId())).andReturn(1);
        replay(carMockDao);
        Integer c= carTransactions.getCountOfCarsByProducerId(testProducer1.getProducerId());
        Assert.isTrue(c==1);
    }

    @Test
    public void testAddCar(){
        expect(carMockDao.addCar(testCar2)).andReturn(0);
        replay(carMockDao);
        Integer id= carTransactions.addCar(testCar2);
        Assert.isTrue(id==0);
    }

    @Test
    public void testUpdateCar(){
        expect(carMockDao.getCarById(testCar1.getCarId())).andReturn(testCar1);
        carMockDao.updateCar(testCar1);
        expectLastCall().once();
        replay(carMockDao);
        carTransactions.updateCar(testCar1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNonExistingCar(){
        testCar2.setCarId(2);
        expect(carMockDao.getCarById(testCar2.getCarId())).andThrow(new EmptyResultDataAccessException(1));
        replay(carMockDao);
        carTransactions.updateCar(testCar2);
    }

    @Test
    public void testDeleteCar(){
        expect(carMockDao.getCarById(testCar1.getCarId())).andReturn(testCar1);
        carMockDao.deleteCar(testCar1.getCarId());
        replay(carMockDao);
        carTransactions.deleteCar(testCar1.getCarId());
    }

    @Test
    public void testGetCarsByDate(){
        expect(carMockDao.getListOfCarsByDateOfCreation(testCar1.getDateOfCreation(), testCar2.getDateOfCreation())).andReturn(Arrays.<Car>asList(testCar1,testCar2));
        replay(carMockDao);
        List<Car> resList= carTransactions.getListOfCarsByDateOfCreation(testCar1.getDateOfCreation(), testCar2.getDateOfCreation());
        Assert.isTrue(resList.get(0)==testCar1);
        Assert.isTrue(resList.get(1)==testCar2);
    }

    @Test
    public void testGetAllCars(){
        expect(carMockDao.getAllCars()).andReturn(Arrays.<Car>asList(testCar1,testCar2));
        replay(carMockDao);
        List<Car> resList= carTransactions.getAllCars();
        Assert.isTrue(resList.get(0)==testCar1);
        Assert.isTrue(resList.get(1)==testCar2);
    }

    @Test
    public void testGetCarsDto(){
        expect(carMockDao.getAllCars()).andReturn(Arrays.<Car>asList(testCar1,testCar2));
        replay(carMockDao);
        CarDto carDto= carTransactions.getCarsDto();
        Assert.isTrue(carDto.getTotal()==2);
        Assert.isTrue(carDto.getCars().get(0)==testCar1);
        Assert.isTrue(carDto.getCars().get(1)==testCar2);
    }

    @Test
    public void testGetCarsDtoByDate(){
        expect(carMockDao.getListOfCarsByDateOfCreation(testCar1.getDateOfCreation(), testCar2.getDateOfCreation())).andReturn(Arrays.<Car>asList(testCar1,testCar2));
        replay(carMockDao);
        CarDto carDto= carTransactions.getCarsDtoByDate(testCar1.getDateOfCreation(), testCar2.getDateOfCreation());
        Assert.isTrue(carDto.getCars().get(0)==testCar1);
        Assert.isTrue(carDto.getCars().get(1)==testCar2);
    }

    private static LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
