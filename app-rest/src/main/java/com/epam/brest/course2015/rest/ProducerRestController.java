package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.service.ProducerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by antonsavitsky on 04.01.16.
 */
@CrossOrigin
@RestController
public class ProducerRestController {
    // При использовании методов put, post указывать request headers:
    //Content-Type: application/json
    //Accept-Language: application/json
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private ProducerService producerService;

    @RequestMapping(value="/producers",
            method = RequestMethod.GET)
    public @ResponseBody
    List<Producer> getAllProducers(){
        LOGGER.debug("Getting all producers'");
        return producerService.getAllProducers();
    }

    @RequestMapping(value="/producer",
            method = RequestMethod.GET)
    public @ResponseBody Producer getProducerById(@RequestParam(value="id") Integer id){
        LOGGER.debug("Getting producer by id={}",id);
        return producerService.getProducerById(id);
    }

    @RequestMapping(value = "/producer",
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Integer addProducer(@RequestBody Producer producer) throws ParseException {
        LOGGER.debug("Adding new producer '{}' '{}'", producer.getProducerName(),producer.getCountry());
        return producerService.addProducer(producer);
    }

    @RequestMapping(value="/producer", method = RequestMethod.PUT)
    public void updateProducer(@RequestBody Producer producer){
        LOGGER.debug("Updating producer with id={}", producer.getProducerId());
        producerService.updateProducer(producer);
    }

    @RequestMapping(value = "/producer",
            method = RequestMethod.DELETE)
    public void deleteProducer(@RequestParam(value = "id")
                               Integer id) {
        LOGGER.debug("Deleting a producer {}", id);
        producerService.deleteProducer(id);
    }

    @RequestMapping(value="/producersdto",
            method = RequestMethod.GET)
    public @ResponseBody
    ProducerDto getProducersDto(){
        LOGGER.debug("Getting producers dto");
        return producerService.getProducersDto();
    }
}

