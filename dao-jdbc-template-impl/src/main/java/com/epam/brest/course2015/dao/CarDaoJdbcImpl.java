package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.test.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public class CarDaoJdbcImpl implements CarDao {

    @Value("${car.updateCar}")
    private String updateCar;
    @Value("${car.selectCarById}")
    private String selectCarById;
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
    @Value("${car.pagingDto}")
    private String pagingDto;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Loggable
    public List<Car> getPagingList(Integer min, Integer max){
        MapSqlParameterSource parameterSource=new MapSqlParameterSource("min", min);
        parameterSource.addValue("max", max);
        List<Car> carList=namedParameterJdbcTemplate.query(pagingDto, parameterSource, new CarMapper());
        return carList;
    }

    @Override
    @Loggable
    public Integer getTotalCount(){
        Integer totalCountOfCars=namedParameterJdbcTemplate.queryForObject(countAllCars,
                new MapSqlParameterSource(), Integer.class);
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
        return namedParameterJdbcTemplate.queryForObject(selectCarById,
                new MapSqlParameterSource("carId", carId), new CarMapper());
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
        namedParameterJdbcTemplate.update(insertCar, getParametersMap(car), keyHolder);
        return keyHolder.getKey().intValue();
    }


    private MapSqlParameterSource getParametersMap(Car car) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("carId", car.getCarId());
        parameterSource.addValue("carName", car.getCarName());
        parameterSource.addValue("producerId", car.getProducer().getProducerId());
        parameterSource.addValue("dateOfCreation",
                car.getDateOfCreation().toDateTimeAtStartOfDay().toDate());
        return parameterSource;
    }

    @Override
    @Loggable
    public void updateCar(Car car) {
        namedParameterJdbcTemplate.update(updateCar, getParametersMap(car));
    }


    @Override
    @Loggable
    public void deleteCar(Integer carId) {
        namedParameterJdbcTemplate.update(deleteCar,
                new MapSqlParameterSource("carId",carId));
    }

    private class CarMapper implements RowMapper<Car> {
        @Override
        public Car mapRow(ResultSet resultSet, int i) throws SQLException {
            Date date=resultSet.getTimestamp("dateOfCreation");
            LocalDate lDate=new LocalDate(date);
            Car car=new Car(resultSet.getInt("carId"),
                    resultSet.getString("carName"),
                    lDate,
                    new Producer(resultSet.getInt("producerId"), resultSet.getString("producerName")));
            return car;
        }
    }

}
