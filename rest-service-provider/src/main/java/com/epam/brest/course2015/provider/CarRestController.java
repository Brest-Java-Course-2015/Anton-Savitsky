package com.epam.brest.course2015.provider;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
import com.epam.brest.course2015.transactions.CarTransactions;
import com.epam.brest.course2015.test.Loggable;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/car")
public class CarRestController{

    @Autowired
    private CarTransactions carTransactions;

    @SubscribeForUpdates
    @RequestMapping(method = RequestMethod.PUT)
    //@Loggable
    public void updateCar(@RequestBody Car car){
        carTransactions.updateCar(car);
        //simpMessagingTemplate.convertAndSend("/topic/update", carTransactions.getCarsDto());
    }


    @RequestMapping(method = RequestMethod.GET)
    @Loggable
    public  @ResponseBody List<Car> getAllCars() {
        return carTransactions.getAllCars();
    }


    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody Car getCarById(@PathVariable("id") Integer id){
        return carTransactions.getCarById(id);
    }


    @SubscribeForUpdates
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    //@Loggable
    public Integer addCar(@RequestBody Car car) {
        int carId=carTransactions.addCar(car);
        //simpMessagingTemplate.convertAndSend("/topic/update", carTransactions.getCarsDto());
        return carId;
    }


    @RequestMapping(value="/date", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody List<Car> getListOfCarsByDateOfCreation(@RequestParam(value = "dateBefore")
                                                                 String dateBefore,
                                                                 @RequestParam(value = "dateAfter")
                                                                 String dateAfter) {
        return carTransactions.getListOfCarsByDateOfCreation(convertToDate(dateBefore),
                convertToDate(dateAfter));
    }


    @SubscribeForUpdates
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    //@Loggable
    public void deleteCar(@PathVariable("id") Integer id) {
        carTransactions.deleteCar(id);
        //simpMessagingTemplate.convertAndSend("/topic/update", carTransactions.getCarsDto());
    }


    @RequestMapping(value="/dto", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody CarDto getCarsDto(){
        return carTransactions.getCarsDto();
    }


    @RequestMapping(value="/dto/date", method = RequestMethod.GET)
    @Loggable
    public @ResponseBody
    CarDto getCarsDtoByDate(@RequestParam(value = "dateBefore") String dateBefore,
                            @RequestParam(value = "dateAfter") String dateAfter){
        return carTransactions.getCarsDtoByDate(convertToDate(dateBefore), convertToDate(dateAfter));
    }


    @RequestMapping(value="/initpaging", method = RequestMethod.GET)
    @Loggable
    public CarPagingDto getInitPaging(@RequestParam(value="from") Integer from,
                                        @RequestParam(value="to") Integer to)
    {
        return  carTransactions.getCarPagingDto(from, to);
    }

    @RequestMapping(value = "/nextpage", method = RequestMethod.GET)
    @Loggable
    public CarPagingDto getNextPage(@RequestParam(value="from") Integer from,
                                 @RequestParam(value="to") Integer to)
    {
        return carTransactions.getCarsByPage(from, to);
    }


    public LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}