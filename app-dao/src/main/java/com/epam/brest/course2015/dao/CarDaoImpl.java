package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CarDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Integer getTotalCountCars(){
        LOGGER.debug("getTotalCountCars()");
        return jdbcTemplate.queryForObject(countAllCars, Integer.class);
    }

    @Override
    public List<Car> getAllCars(){
        LOGGER.debug("getAllCars()");
        return jdbcTemplate.query(selectAll, new CarRowMapper());
    }

    @Override
    public Car getCarById(Integer carId) {
        LOGGER.debug("getCarById()");
        return jdbcTemplate.queryForObject(selectCarById, new Integer[]{carId}, new CarRowMapper());
    }

    @Override
    public Integer getCountCarsById(Integer carId) {
        LOGGER.debug("getCountCarsById(): carId = {}", carId);
        return jdbcTemplate.queryForObject(countCarsById, new Integer[]{carId}, Integer.class);
    }

    @Override
    public Integer getCountOfCarsByProducerId(Integer producerId) {
        LOGGER.debug("getCountOfCarsByProducerId()");
        return jdbcTemplate.queryForObject(countOfCarsByProducerId, new Integer[]{producerId}, Integer.class);
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(java.util.Date dateBefore, java.util.Date dateAfter) {
        LOGGER.debug("getListOfCarsByDateOfCreation()");
        return jdbcTemplate.query(selectCarsByDateOfCreation, new java.sql.Date[]{new java.sql.Date(dateBefore.getTime()), new java.sql.Date(dateAfter.getTime())}, new CarRowMapper());
    }

    @Override
    public Integer addCar(Car car) throws DataAccessException {
        LOGGER.debug("addCar(): carName={}", car.getCarName());
        KeyHolder keyHolder= new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertCar, getParametersMap(car),keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateCar(Car car){
        LOGGER.debug("updateCar(): carName={}", car.getCarName());
        jdbcTemplate.update(updateCar, new Object[]{car.getCarName(), car.getProducerId(), car.getDateOfCreation(), car.getCarId()});
    }

    @Override
    public void deleteCar(Integer carId) {
        LOGGER.debug("deleteCar(): carId={}", carId);
        jdbcTemplate.update(deleteCar, carId);
    }

    private MapSqlParameterSource getParametersMap(Car car) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(CAR_NAME.getValue(), car.getCarName());
        parameterSource.addValue(PRODUCER_ID.getValue(), car.getProducerId());
        parameterSource.addValue(DATE_OF_CREATION.getValue(), car.getDateOfCreation());
        return parameterSource;
    }

    private class CarRowMapper implements RowMapper<Car> {
        @Override
        public Car mapRow(ResultSet resultSet, int i) throws SQLException {
            Car car = new Car(resultSet.getInt(CAR_ID.getValue()),
                    resultSet.getString(CAR_NAME.getValue()),
                    resultSet.getInt(PRODUCER_ID.getValue()),
                    resultSet.getDate(DATE_OF_CREATION.getValue()));
            return car;
        }
    }
}
