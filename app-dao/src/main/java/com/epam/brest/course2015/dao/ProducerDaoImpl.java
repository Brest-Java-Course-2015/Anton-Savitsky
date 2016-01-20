package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.util.List;

/**
 * Created by antonsavitsky on 02.12.15.
 */
public class ProducerDaoImpl implements ProducerDao {
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
    @Loggable
    public Producer getProducerById(Integer producerId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        return namedParameterJdbcTemplate.queryForObject(selectProducerById, parameterSource, producerMapper);
    }

    @Override
    @Loggable
    public Integer addProducer(Producer producer) {
        KeyHolder keyHolder= new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource=new BeanPropertySqlParameterSource(producer);
        namedParameterJdbcTemplate.update(insertProducer, parameterSource,keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    @Loggable
    public void updateProducer(Producer producer) {
        BeanPropertySqlParameterSource parameterSource =
                new BeanPropertySqlParameterSource(producer);
        namedParameterJdbcTemplate.update(updateProducer, parameterSource);
    }

    @Override
    @Loggable
    public Integer getTotalCount(){
        SqlParameterSource parameterSource=new MapSqlParameterSource();
        return namedParameterJdbcTemplate.queryForObject(totalCount, parameterSource, Integer.class);
    }

    @Override
    @Loggable
    public void deleteProducer(Integer producerId) {
        deleteAllCarsWithProducerId(producerId);
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        namedParameterJdbcTemplate.update(deleteProducer, parameterSource);
    }

    @Override
    @Loggable
    public void deleteAllCarsWithProducerId(Integer producerId) {
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        namedParameterJdbcTemplate.update(deleteAllCarsWithProducerId, parameterSource);
    }

    @Override
    @Loggable
    public Integer countCarsByProducerId(Integer producerId){
        SqlParameterSource parameterSource=new MapSqlParameterSource("producerId",producerId);
        return namedParameterJdbcTemplate.queryForObject(countOfCarsByProducerId, parameterSource, Integer.class);
    }

    @Override
    @Loggable
    public List<Producer> getAllProducers() {
        return namedParameterJdbcTemplate.query(selectAll,producerMapper);
    }
}
