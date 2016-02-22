package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.service.CarService;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Loggable
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getCarDto() {
        CarDto dto = carService.getCarsDto();
        return new ModelAndView("cars", "dto", dto);
    }

    @Loggable
    @RequestMapping(value = "/delete/{id}")
    public String deleteCar(@PathVariable(value = "id") Integer carId){
        carService.deleteCar(carId);
        return "forward:/car";
    }

    @Loggable
    @RequestMapping(value="/update/{id}")
    public ModelAndView updateCar(@PathVariable(value = "id") Integer carId){
        return new ModelAndView("car", "car", carService.getCarById(carId));
    }
}
