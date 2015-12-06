package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

    public ProducerDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Producer getProducerById(Integer producerId) {
        LOGGER.debug("Getting producer by Id={}",producerId);
        return jdbcTemplate.queryForObject(selectProducerById, new Integer[]{producerId}, new ProducerRowMapper());
    }

    @Override
    public Integer addProducer(Producer producer) {
        LOGGER.debug("addProducer(): producerName={}", producer.getProducerName());
        KeyHolder keyHolder= new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertProducer, getParametersMap(producer),keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProducer(Producer producer) {
        LOGGER.debug("UpdateProducer(): producerId={}", producer.getProducerId());
        jdbcTemplate.update(updateProducer, new Object[]{producer.getProducerName(), producer.getCountry(), producer.getProducerId()});
    }

    @Override
    public Integer getTotalCount(){
        LOGGER.debug("getTotalCount()");
        return jdbcTemplate.queryForObject(totalCount, Integer.class);
    }

    @Override
    public void deleteProducer(Integer producerId) {
        LOGGER.debug("DeleteProducer(): producerId={}",producerId);
        deleteAllCarsWithProducerId(producerId);
        jdbcTemplate.update(deleteProducer, producerId);
    }

    @Override
    public void deleteAllCarsWithProducerId(Integer producerId) {
        LOGGER.debug("deleteAllCarsWithProducerId(): producerId={}",producerId);
        jdbcTemplate.update(deleteAllCarsWithProducerId, producerId);
    }

    @Override
    public Integer countCarsByProducerId(Integer producerId){
        LOGGER.debug("countCarsByProducerId(): producerId={}",producerId);
        return jdbcTemplate.queryForObject(countOfCarsByProducerId, new Integer[]{producerId}, Integer.class);
    }

    @Override
    public List<Producer> getAllProducers() {
        LOGGER.debug("getAllProducers()");
        return jdbcTemplate.query(selectAll,new ProducerRowMapper());
    }

    private MapSqlParameterSource getParametersMap(Producer producer) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(PRODUCER_ID.getValue(), producer.getProducerId());
        parameterSource.addValue(PRODUCER_NAME.getValue(), producer.getProducerName());
        parameterSource.addValue(COUNTRY.getValue(), producer.getCountry());
        return parameterSource;
    }

    private class ProducerRowMapper implements RowMapper<Producer> {
        @Override
        public Producer mapRow(ResultSet resultSet, int i) throws SQLException {
            Producer producer = new Producer(resultSet.getInt(PRODUCER_ID.getValue()),
                    resultSet.getString(PRODUCER_NAME.getValue()),
                    resultSet.getString(COUNTRY.getValue()));
            return producer;
        }
    }
}
