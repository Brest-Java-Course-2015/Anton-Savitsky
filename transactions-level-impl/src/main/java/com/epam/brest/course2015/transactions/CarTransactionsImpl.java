package com.epam.brest.course2015.transactions;

import com.epam.brest.course2015.dao.CarDao;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
import com.epam.brest.course2015.test.Loggable;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * Created by antonsavitsky on 17.11.15.
 */
//Transactions are supported for all methods defined in this class
@Transactional
public class CarTransactionsImpl implements CarTransactions {
    @Value("${car.IdNotNull}")
    private String carIdNotNull;
    @Value("${car.IdNotNegative}")
    private String carIdNotNegative;
    @Value("${car.NameNotNull}")
    private String carNameNotNull;
    @Value("${producer.IdNotNull}")
    private String producerIdNotNull;
    @Value("${producer.IdNotNegative}")
    private String producerIdNotNegative;
    @Value("${car.CarNotNull}")
    private String carNotNull;
    @Value("${car.IdNull}")
    private String carIdNull;
    @Value("${car.DateNotNull}")
    private String dateNotNull;

    @Autowired
    private CarDao carDao;

    @Loggable
    @Override
    public Car getCarById(Integer carId) {
        Assert.notNull(carId, carIdNotNull);
        Assert.isTrue(carId >= 0, carIdNotNegative);
        try {
            return carDao.getCarById(carId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Loggable
    @Override
    public Integer getCountOfCarsByProducerId(Integer producerId) {
        Assert.notNull(producerId, producerIdNotNull);
        Assert.isTrue(producerId >= 0, producerIdNotNegative);
        try {
            return carDao.getCountOfCarsByProducerId(producerId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Loggable
    @Override
    public List<Car> getListOfCarsByDateOfCreation(LocalDate dateBefore, LocalDate dateAfter) {
        Assert.notNull(dateBefore, dateNotNull);
        Assert.notNull(dateAfter, dateNotNull);
        Assert.isTrue(dateAfter.isAfter(dateBefore), "DateAfter must be after DateBefore!");
        return carDao.getListOfCarsByDateOfCreation(dateBefore,dateAfter);
    }

    @Loggable
    @Override
    public Integer addCar(Car car) throws DataAccessException {
        Assert.notNull(car, carNotNull);
        Assert.isNull(car.getCarId(), carIdNull);
        Assert.notNull(car.getProducer().getProducerId(), producerIdNotNull);
        Assert.hasText(car.getCarName(), carNameNotNull);
        Assert.notNull(car.getDateOfCreation(), dateNotNull);
        return carDao.addCar(car);
    }

    @Loggable
    @Override
    public void updateCar(Car car) {
        Assert.notNull(car, carNotNull);
        Assert.notNull(car.getCarId(), carIdNotNull);
        Integer carIdToUpdate=car.getCarId();
        try{
            carDao.getCarById(carIdToUpdate);
        } catch (EmptyResultDataAccessException erdae){
            throw new IllegalArgumentException();
        }
        Assert.hasText(car.getCarName(), carNameNotNull);
        Assert.notNull(car.getDateOfCreation(), dateNotNull);
        Assert.notNull(car.getProducer().getProducerId(), producerIdNotNull);
        carDao.updateCar(car);
    }

    @Loggable
    @Override
    public void deleteCar(Integer carId) {
        try {
            carDao.getCarById(carId);
        } catch (DataAccessException dae) {
            throw new IllegalArgumentException();
        }
        Assert.notNull(carId, carIdNotNull);
        carDao.deleteCar(carId);
    }

    @Loggable
    @Override
    public List<Car> getAllCars() {
        return carDao.getAllCars();
    }

    @Loggable
    @Override
    public CarDto getCarsDto(){
        CarDto carDto = new CarDto();
        List<Car> carList=carDao.getAllCars();
        if(carList.size()>0){
            carDto.setCars(carList);
            carDto.setTotal(carList.size());
        }
        else {
            carDto.setCars(Collections.<Car>emptyList());
            carDto.setTotal(0);
        }
        return carDto;
    }

    @Loggable
    @Override
    public CarDto getCarsDtoByDate(LocalDate dateBefore, LocalDate dateAfter) {
        CarDto carDto=new CarDto();
        List<Car> carList=getListOfCarsByDateOfCreation(dateBefore, dateAfter);
        if(carList.size()>0){
            carDto.setCars(carList);
            carDto.setTotal(carList.size());
        } else {
            carDto.setCars(Collections.<Car>emptyList());
            carDto.setTotal(0);
        }
        return carDto;
    }


    @Loggable
    @Override
    public CarPagingDto getCarPagingDto(Integer min, Integer max){
        List<Car> selectedCarList=carDao.getPagingList(min, max);
        Integer totalRows=carDao.getTotalCount();
        return new CarPagingDto(selectedCarList, totalRows);
    }


    @Loggable
    @Override
    public CarPagingDto getCarsByPage(Integer min, Integer max){
        return new CarPagingDto(carDao.getPagingList(min, max), 0);//changed since 11/02
    }
}
