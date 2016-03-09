package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.service.ProducerService;
import com.epam.brest.course2015.test.Loggable;
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
@RequestMapping(value="/producer")
public class ProducerRestController {
    @Autowired
    private ProducerService producerService;

    @RequestMapping(method = RequestMethod.GET)
    @Loggable
    public @ResponseBody
    List<Producer> getAllProducers(){
        return producerService.getAllProducers();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody Producer getProducerById(@PathVariable("id") Integer id){
        return producerService.getProducerById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @Loggable
    public Integer addProducer(@RequestBody Producer producer) throws ParseException {
        return producerService.addProducer(producer);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Loggable
    public void updateProducer(@RequestBody Producer producer){
        producerService.updateProducer(producer);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @Loggable
    public void deleteProducer(@PathVariable("id") Integer id) {
        producerService.deleteProducer(id);
    }

    @RequestMapping(value="/dto",
            method = RequestMethod.GET)
    @Loggable
    public @ResponseBody
    ProducerDto getProducersDto(){
        return producerService.getProducersDto();
    }
}

