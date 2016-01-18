package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.test.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public class CarDaoImpl implements CarDao {
    @Value("${car.selectCarById}")
    private String selectCarById;
    @Value("${car.selectCarByName}")
    private String selectCarByName;
    @Value("${car.countOfCarsByProducerId}")
    private String countOfCarsByProducerId;
    @Value("${car.selectCarsByDateOfCreation}")
    private String selectCarsByDateOfCreation;
    @Value("${car.insertCar}")
    private String insertCar;
    @Value("${car.updateCar}")
    private String updateCar;
    @Value("${car.deleteCar}")
    private String deleteCar;
    @Value("${car.countAllCars}")
    private String countAllCars;
    @Value("${car.selectAll}")
    private String selectAll;
    @Value("${car.countCarsById}")
    private String countCarsById;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<Car> carMapper = new BeanPropertyRowMapper<>(Car.class);

    @Override
    @Loggable
    public Integer getTotalCountCars(){
        SqlParameterSource parameterSource=new MapSqlParameterSource();
        Integer totalCountOfCars=namedParameterJdbcTemplate.queryForObject(countAllCars, parameterSource,Integer.class);
        return totalCountOfCars;
    }

    @Override
    @Loggable
    public List<Car> getAllCars(){
        List<Car> listOfCars=namedParameterJdbcTemplate.query(selectAll, carMapper);
        return listOfCars;
    }

    @Override
    @Loggable
    public Car getCarById(Integer carId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        return namedParameterJdbcTemplate.queryForObject(selectCarById, parameterSource,carMapper);
    }

    @Override
    @Loggable
    public Integer getCountCarsById(Integer carId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        return namedParameterJdbcTemplate.queryForObject(countCarsById, parameterSource, Integer.class);
    }

    @Override
    @Loggable
    public Integer getCountOfCarsByProducerId(Integer producerId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        return namedParameterJdbcTemplate.queryForObject(countOfCarsByProducerId,parameterSource, Integer.class);
    }

    @Override
    @Loggable
    public List<Car> getListOfCarsByDateOfCreation(Date dateBefore, Date dateAfter) {
        MapSqlParameterSource parameterSource =
                new MapSqlParameterSource("dateBefore", dateBefore);
        parameterSource.addValue("dateAfter", dateAfter);
        return namedParameterJdbcTemplate.query(selectCarsByDateOfCreation, parameterSource, carMapper);
    }

    @Override
    @Loggable
    public Integer addCar(Car car) throws DataAccessException {
        KeyHolder keyHolder= new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(car);
        namedParameterJdbcTemplate.update(insertCar, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    @Loggable
    public void updateCar(Car car){
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(car);
        namedParameterJdbcTemplate.update(updateCar, parameterSource);
    }

    @Override
    @Loggable
    public void deleteCar(Integer carId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        namedParameterJdbcTemplate.update(deleteCar, parameterSource);
    }

}
