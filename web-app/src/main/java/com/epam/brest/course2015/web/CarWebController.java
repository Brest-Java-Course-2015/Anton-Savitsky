package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.provider.CarServiceConsumer;
import com.epam.brest.course2015.provider.ProducerServiceConsumer;
import com.epam.brest.course2015.test.Loggable;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private CarServiceConsumer carServiceConsumer;

    @Autowired
    private ProducerServiceConsumer producerServiceConsumer;


    @Loggable
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCarDto() {
        CarDto dto = carServiceConsumer.getCarsDto();
        return new ModelAndView("cars", "dto", dto);
    }

    @Loggable
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteCar(@PathVariable(value ="id") Integer carId){
        carServiceConsumer.deleteCar(carId);
        return "redirect:/car";
    }

    @Loggable
    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateCarForm(@PathVariable(value = "id") Integer carId){
        ModelAndView modelAndView=new ModelAndView("carform", "car", carServiceConsumer.getCarById(carId));
        modelAndView.addObject("producersdto", producerServiceConsumer.getProducersDto());
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/update/{id}", method=RequestMethod.POST)
    public String saveUpdatedCar(Car car) {
        carServiceConsumer.updateCar(car);
        return "redirect:/car";
    }

    @Loggable
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView carAddingForm(){
        ModelAndView modelAndView=new ModelAndView("carform", "car", new Car());
        modelAndView.addObject("producersdto", producerServiceConsumer.getProducersDto());
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String addCar(Car car) {
        //adding as new car so setting id to null
        car.setCarId(null);
        carServiceConsumer.addCar(car);
        return "redirect:/car";
    }

    @Loggable
    @RequestMapping(value = "/carsbydate", method = RequestMethod.GET)
    public ModelAndView getCarByDateDto(@RequestParam(value="dateBefore") String dateBefore,
                                        @RequestParam(value="dateAfter") String dateAfter){
        CarDto dto = carServiceConsumer.getCarsDtoByDate(dateBefore, dateAfter);
        return new ModelAndView("carsbydate", "dto", dto);
    }
}
