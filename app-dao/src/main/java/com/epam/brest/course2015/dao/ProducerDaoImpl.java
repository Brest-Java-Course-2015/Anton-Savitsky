package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static com.epam.brest.course2015.domain.Producer.ProducerFields.*;

/**
 * Created by antonsavitsky on 02.12.15.
 */
public class ProducerDaoImpl implements ProducerDao {
    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${producer.selectProducerById}")
    private String selectProducerById;
    @Value("${producer.insertProducer}")
    private String insertProducer;
    @Value("${producer.updateProducer}")
    private String updateProducer;
    @Value("${producer.deleteProducer}")
    private String deleteProducer;
    @Value("${producer.totalCount}")
    private String totalCount;
    @Value("${car.countOfCarsByProducerId}")
    private String countOfCarsByProducerId;
    @Value("${producer.deleteAllCarsWithProducerId}")
    private String deleteAllCarsWithProducerId;
    @Value("${producer.selectAll}")
    private String selectAll;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<Producer> producerMapper = new BeanPropertyRowMapper<>(Producer.class);

    @Override
    public Producer getProducerById(Integer producerId) {
        LOGGER.debug("Getting producer by Id={}",producerId);
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        return namedParameterJdbcTemplate.queryForObject(selectProducerById, parameterSource, producerMapper);
    }

    @Override
    public Integer addProducer(Producer producer) {
        LOGGER.debug("addProducer(): producerName={}", producer.getProducerName());
        KeyHolder keyHolder= new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource=new BeanPropertySqlParameterSource(producer);
        namedParameterJdbcTemplate.update(insertProducer, parameterSource,keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProducer(Producer producer) {
        LOGGER.debug("UpdateProducer(): producerId={}", producer.getProducerId());
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(producer);
        namedParameterJdbcTemplate.update(updateProducer, parameterSource);
    }

    @Override
    public Integer getTotalCount(){
        LOGGER.debug("getTotalCount()");
        SqlParameterSource parameterSource=new MapSqlParameterSource();
        return namedParameterJdbcTemplate.queryForObject(totalCount, parameterSource, Integer.class);
    }

    @Override
    public void deleteProducer(Integer producerId) {
        LOGGER.debug("DeleteProducer(): producerId={}",producerId);
        deleteAllCarsWithProducerId(producerId);
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        namedParameterJdbcTemplate.update(deleteProducer, parameterSource);
    }

    @Override
    public void deleteAllCarsWithProducerId(Integer producerId) {
        LOGGER.debug("deleteAllCarsWithProducerId(): producerId={}",producerId);
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        namedParameterJdbcTemplate.update(deleteAllCarsWithProducerId, parameterSource);
    }

    @Override
    public Integer countCarsByProducerId(Integer producerId){
        LOGGER.debug("countCarsByProducerId(): producerId={}",producerId);
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        return namedParameterJdbcTemplate.queryForObject(countOfCarsByProducerId, parameterSource, Integer.class);
    }

    @Override
    public List<Producer> getAllProducers() {
        LOGGER.debug("getAllProducers()");
        return namedParameterJdbcTemplate.query(selectAll,producerMapper);
    }
}
