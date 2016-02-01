package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.service.CarService;
import com.epam.brest.course2015.test.Loggable;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;
/**
 * Created by juga on 16.10.15.
 */
@CrossOrigin
@RestController
public class CarRestController {
    @Autowired
    private CarService carService;

    @RequestMapping(value="/car", method = RequestMethod.PUT)
    @Loggable
    public void updateCar(@RequestBody Car car){
        carService.updateCar(car);
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    @Loggable
    public  @ResponseBody List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @RequestMapping(value="/car", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody Car getCarById(@RequestParam(value="id") Integer id){
        return carService.getCarById(id);
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @Loggable
    public Integer addCar(@RequestBody Car car) throws ParseException {
        return carService.addCar(car);
    }

    @RequestMapping(value="/car/date", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody List<Car> getListOfCarsByDateOfCreation(@RequestParam(value = "dateBefore")
                                                                 String dateBefore,
                                                                 @RequestParam(value = "dateAfter")
                                                                 String dateAfter) throws ParseException {

        return carService.getListOfCarsByDateOfCreation(convertToDate(dateBefore), convertToDate(dateAfter));
    }

    @RequestMapping(value = "/car",
            method = RequestMethod.DELETE)
    @Loggable
    public void deleteCar(@RequestParam(value = "id")
                          Integer id) {
        carService.deleteCar(id);
    }

    @RequestMapping(value="/carsdto",
            method = RequestMethod.GET)
    @Loggable
    public @ResponseBody CarDto getCarsDto(){
        return carService.getCarsDto();
    }

    @RequestMapping(value="/carsdtobydate",
            method = RequestMethod.GET)
    @Loggable
    public @ResponseBody CarDto getCarsDtoByDate(@RequestParam(value = "dateBefore")
                                                 String dateBefore,
                                                 @RequestParam(value = "dateAfter")
                                                 String dateAfter) throws ParseException {
        return carService.getCarsByDateDto(convertToDate(dateBefore), convertToDate(dateAfter));
    }

    public LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}