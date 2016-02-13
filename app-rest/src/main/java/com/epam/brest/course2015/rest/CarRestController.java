package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
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

@CrossOrigin
@RestController
@RequestMapping(value = "/car")
public class CarRestController {

    @Autowired
    private CarService carService;


    @RequestMapping(method = RequestMethod.PUT)
    @Loggable
    public void updateCar(@RequestBody Car car){
        carService.updateCar(car);
    }


    @RequestMapping(method = RequestMethod.GET)
    @Loggable
    public  @ResponseBody List<Car> getAllCars() {
        return carService.getAllCars();
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody Car getCarById(@PathVariable("id") Integer id){
        return carService.getCarById(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @Loggable
    public Integer addCar(@RequestBody Car car) throws ParseException {
        return carService.addCar(car);
    }


    @RequestMapping(value="/date", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody List<Car> getListOfCarsByDateOfCreation(@RequestParam(value = "dateBefore")
                                                                 String dateBefore,
                                                                 @RequestParam(value = "dateAfter")
                                                                 String dateAfter) throws ParseException {

        return carService.getListOfCarsByDateOfCreation(convertToDate(dateBefore),
                convertToDate(dateAfter));
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @Loggable
    public void deleteCar(@PathVariable("id") Integer id) {
        carService.deleteCar(id);
    }


    @RequestMapping(value="/dto", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody CarDto getCarsDto(){
        return carService.getCarsDto();
    }


    @RequestMapping(value="/dto/date", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody
    CarDto getCarsDtoByDate(@RequestParam(value = "dateBefore") String dateBefore,
                            @RequestParam(value = "dateAfter") String dateAfter) throws ParseException {
        return carService.getCarsByDateDto(convertToDate(dateBefore), convertToDate(dateAfter));
    }


    @RequestMapping(value="/initpaging", method = RequestMethod.GET)
    @Loggable
    public CarPagingDto getInitPaging(@RequestParam(value="from") Integer from,
                                        @RequestParam(value="to") Integer to)
    {
        return  carService.getCarPagingDto(from, to);
    }

    @RequestMapping(value = "/nextpage", method = RequestMethod.GET)
    @Loggable
    public CarPagingDto getNextPage(@RequestParam(value="from") Integer from,
                                 @RequestParam(value="to") Integer to)
    {
        return carService.getCarsByPage(from, to);
    }


    public LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }

}