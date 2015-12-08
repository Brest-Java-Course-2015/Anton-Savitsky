package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import static com.epam.brest.course2015.domain.Car.CarFields.*;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public class CarDaoImpl implements CarDao {
    private static final Logger LOGGER = LogManager.getLogger();

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
    public Integer getTotalCountCars(){
        LOGGER.debug("getTotalCountCars()");
        SqlParameterSource parameterSource=new MapSqlParameterSource();
        return namedParameterJdbcTemplate.queryForObject(countAllCars, parameterSource,Integer.class);
    }

    @Override
    public List<Car> getAllCars(){
        LOGGER.debug("getAllCars()");
        return namedParameterJdbcTemplate.query(selectAll, carMapper);
    }

    @Override
    public Car getCarById(Integer carId) {
        LOGGER.debug("getCarById()");
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        return namedParameterJdbcTemplate.queryForObject(selectCarById, parameterSource,carMapper);
    }

    @Override
    public Integer getCountCarsById(Integer carId) {
        LOGGER.debug("getCountCarsById(): carId = {}", carId);
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        return namedParameterJdbcTemplate.queryForObject(countCarsById, parameterSource, Integer.class);
    }

    @Override
    public Integer getCountOfCarsByProducerId(Integer producerId) {
        LOGGER.debug("getCountOfCarsByProducerId()");
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        return namedParameterJdbcTemplate.queryForObject(countOfCarsByProducerId,parameterSource, Integer.class);
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(java.util.Date dateBefore, java.util.Date dateAfter) {
        LOGGER.debug("getListOfCarsByDateOfCreation()");
        MapSqlParameterSource parameterSource =
                new MapSqlParameterSource("dateBefore", new java.sql.Date(dateBefore.getTime()));
        parameterSource.addValue("dateAfter", new java.sql.Date(dateAfter.getTime()));
        return namedParameterJdbcTemplate.query(selectCarsByDateOfCreation, parameterSource, carMapper);
    }

    @Override
    public Integer addCar(Car car) throws DataAccessException {
        LOGGER.debug("addCar(): carName={}", car.getCarName());
        KeyHolder keyHolder= new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(car);
        namedParameterJdbcTemplate.update(insertCar, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateCar(Car car){
        LOGGER.debug("updateCar(): carName={}", car.getCarName());
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(car);
        namedParameterJdbcTemplate.update(updateCar, parameterSource);
    }

    @Override
    public void deleteCar(Integer carId) {
        LOGGER.debug("deleteCar(): carId={}", carId);
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        namedParameterJdbcTemplate.update(deleteCar, parameterSource);
    }

}
