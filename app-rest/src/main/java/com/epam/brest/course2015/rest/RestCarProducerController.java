package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.service.CarProducerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
/**
 * Created by juga on 16.10.15.
 */
@RestController
public class RestCarProducerController {
// При использовании методов put, post указывать request headers:
    //Content-Type: application/json
    //Accept-Language: application/json
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CarProducerService carProducerService;

    @RequestMapping(value="/car", method = RequestMethod.PUT)
    public void updateCar(@RequestBody Car car){
        LOGGER.debug("Updating car with id={}", car.getCarId());
        carProducerService.updateCar(car);
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public @ResponseBody List<Car> getAllCars() {
        LOGGER.debug("getAllCars()");
        return carProducerService.getAllCars();
    }

    @RequestMapping(value="/car", method = RequestMethod.GET)
    public @ResponseBody Car getCarById(@RequestParam(value="carId") Integer id){
        LOGGER.debug("Getting car by its id={}", id);
        return carProducerService.getCarById(id);
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Integer addCar(@RequestBody Car car) throws ParseException {
        LOGGER.debug("Adding new car with id={}", car.getCarId());
        return carProducerService.addCar(car);
    }

    @RequestMapping(value="/car/date", method = RequestMethod.GET)
    public @ResponseBody List<Car> getListOfCarsByDateOfCreation(@RequestParam(value = "dateBefore")
                                                       String dateBefore,
                                                   @RequestParam(value = "dateAfter")
                                                   String dateAfter) throws ParseException {

      LOGGER.debug("getting list of cars by date interval (from,to)=({},{})",dateBefore,dateAfter);
      return carProducerService.getListOfCarsByDateOfCreation(DATE_FORMAT.parse(dateBefore), DATE_FORMAT.parse(dateAfter));
    }

    @RequestMapping(value = "/car",
            method = RequestMethod.DELETE)
    public void deleteCar(@RequestParam(value = "id")
                           Integer id) {
        LOGGER.debug("Deleting a car {}", id);
        carProducerService.deleteCar(id);
    }

    @RequestMapping(value="/carsdto",
        method = RequestMethod.GET)
    public @ResponseBody CarDto getCarsDto(){
        LOGGER.debug("Getting cars dto");
        return carProducerService.getCarsDto();
    }

    @RequestMapping(value="/carsdtobydate",
    method = RequestMethod.GET)
    public @ResponseBody CarDto getCarsDtoByDate(@RequestParam(value = "dateBefore")
                                                     String dateBefore,
                                                 @RequestParam(value = "dateAfter")
                                                     String dateAfter) throws ParseException {
        LOGGER.debug("Getting cars dto by date");
        return carProducerService.getCarsByDateDto(DATE_FORMAT.parse(dateBefore), DATE_FORMAT.parse(dateAfter));
    }

    @RequestMapping(value="/producers",
    method = RequestMethod.GET)
    public @ResponseBody List<Producer> getAllProducers(){
        LOGGER.debug("Getting all producers'");
        return carProducerService.getAllProducers();
    }

    @RequestMapping(value="/producer",
    method = RequestMethod.GET)
    public @ResponseBody Producer getProducerById(@RequestParam(value="id") Integer id){
        LOGGER.debug("Getting producer by id={}",id);
        return carProducerService.getProducerById(id);
    }

    @RequestMapping(value = "/producer",
            method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Integer addProducer(@RequestBody Producer producer) throws ParseException {
        LOGGER.debug("Adding new producer with id={}", producer.getProducerId());
        return carProducerService.addProducer(producer);
    }

    @RequestMapping(value="/producer", method = RequestMethod.PUT)
    public void updateProducer(@RequestBody Producer producer){
        LOGGER.debug("Updating producer with id={}", producer.getProducerId());
        carProducerService.updateProducer(producer);
    }

    @RequestMapping(value = "/producer",
            method = RequestMethod.DELETE)
    public void deleteProducer(@RequestParam(value = "id")
                          Integer id) {
        LOGGER.debug("Deleting a producer {}", id);
        carProducerService.deleteProducer(id);
    }

    @RequestMapping(value="/producersdto",
            method = RequestMethod.GET)
    public @ResponseBody ProducerDto getProducersDto(){
        LOGGER.debug("Getting producers dto");
        return carProducerService.getProducersDto();
    }
/*
    @RequestMapping(value="/cars",
            method = RequestMethod.GET)
    public @ResponseBody Integer getCountOfCarsByProducerId(@RequestParam(value="pid") Integer pid){
        LOGGER.debug("Get count Of cars by producerid");
        return carProducerService.getCountOfCarsByProducerId(pid);

    }
    */
}
