package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.service.CarService;
import com.epam.brest.course2015.service.ProducerService;
import com.epam.brest.course2015.test.Loggable;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by antonsavitsky on 21.02.16.
 */
@Controller
@RequestMapping("/car")
@CrossOrigin
public class CarWebController {

    @Autowired
    private CarService carService;

    @Autowired
    private ProducerService producerService;

    @Loggable
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCarDto() {
        CarDto dto = carService.getCarsDto();
        return new ModelAndView("cars", "dto", dto);
    }

    @Loggable
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteCar(@PathVariable(value ="id") Integer carId){
        carService.deleteCar(carId);
        return "redirect:/car";
    }

    @Loggable
    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateCarForm(@PathVariable(value = "id") Integer carId){
        ModelAndView modelAndView=new ModelAndView("carform", "car", carService.getCarById(carId));
        modelAndView.addObject("producersdto", producerService.getProducersDto());
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/update/{id}", method=RequestMethod.POST)
    public String saveUpdatedCar(Car car) {
        carService.updateCar(car);
        return "redirect:/car";
    }

    @Loggable
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView carAddingForm(){
        ModelAndView modelAndView=new ModelAndView("carform", "car", new Car());
        modelAndView.addObject("producersdto", producerService.getProducersDto());
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String addCar(Car car) {
        //adding as new car so setting id to null
        car.setCarId(null);
        carService.addCar(car);
        return "redirect:/car";
    }

    @Loggable
    @RequestMapping(value = "/carsbydate", method = RequestMethod.GET)
    public ModelAndView getCarByDateDto(@RequestParam(value="dateBefore") String dateBefore,
                                        @RequestParam(value="dateAfter") String dateAfter){
        CarDto dto = carService.getCarsByDateDto(convertToDate(dateBefore), convertToDate(dateAfter));
        return new ModelAndView("carsbydate", "dto", dto);
    }

    public LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
