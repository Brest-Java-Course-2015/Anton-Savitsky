package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.test.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public class CarDaoImpl implements CarDao {
    DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
    @Value("${car.updateCar}")
    private String updateCar;
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
    @Value("${car.deleteCar}")
    private String deleteCar;
    @Value("${car.countAllCars}")
    private String countAllCars;
    @Value("${car.selectAll}")
    private String selectAll;
    @Value("${car.countCarsById}")
    private String countCarsById;
    @Value("${car.dateOfCreationById}")
    private String dateOfCreationById;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger();

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
        List<Car> listOfCars=namedParameterJdbcTemplate.query(selectAll, new CarMapper());
        return listOfCars;
    }

    @Override
    @Loggable
    public Car getCarById(Integer carId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        return namedParameterJdbcTemplate.queryForObject(selectCarById, parameterSource, new CarMapper());
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
    public List<Car> getListOfCarsByDateOfCreation(LocalDate dateBefore, LocalDate dateAfter) {
        MapSqlParameterSource parameterSource =
                new MapSqlParameterSource("dateBefore", dateBefore.toDateTimeAtStartOfDay().toDate());
        parameterSource.addValue("dateAfter", dateAfter.toDateTimeAtStartOfDay().toDate());
        return namedParameterJdbcTemplate.query(selectCarsByDateOfCreation, parameterSource, new CarMapper());
    }

    @Override
    @Loggable
    public Integer addCar(Car car) throws DataAccessException {
        KeyHolder keyHolder= new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(car);
        namedParameterJdbcTemplate.update(insertCar, getParametersMap(car), keyHolder);
        return keyHolder.getKey().intValue();
    }

    private MapSqlParameterSource getParametersMap(Car car) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("carId", car.getCarId());
        parameterSource.addValue("carName", car.getCarName());
        parameterSource.addValue("producerId", car.getProducerId());
        Date date=car.getDateOfCreation().toDateTimeAtStartOfDay().toDate();
        LOGGER.debug(date.toString());
        parameterSource.addValue("dateOfCreation", date);
        return parameterSource;
    }

    @Override
    @Loggable
    public void updateCar(Car car){
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(car);
        namedParameterJdbcTemplate.update(updateCar, getParametersMap(car));
    }

    @Override
    @Loggable
    public void deleteCar(Integer carId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("carId",carId);
        namedParameterJdbcTemplate.update(deleteCar, parameterSource);
    }

    private class CarMapper implements RowMapper<Car> {
        @Override
        public Car mapRow(ResultSet resultSet, int i) throws SQLException {
            Date date=resultSet.getTimestamp("dateOfCreation");
            LocalDate lDate=new LocalDate(date);
             LOGGER.debug(lDate.toString());
            Car car = new Car(resultSet.getInt("carId"),
                    resultSet.getString("carName"),
                    resultSet.getInt("producerId"),
                    lDate);
            return car;
        }
    }
}
