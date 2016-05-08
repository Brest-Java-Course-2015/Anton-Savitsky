package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.provider.CarServiceConsumer;
import com.epam.brest.course2015.provider.ProducerServiceConsumer;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by antonsavitsky on 21.02.16.
 */
@Controller
@CrossOrigin
public class CarWebController {
    @Autowired
    private CarServiceConsumer carServiceConsumer;

    @Autowired
    private ProducerServiceConsumer producerServiceConsumer;

    @RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message", "This is welcome page! Car factory app.");
        model.setViewName("hello");
        return model;
    }

    @RequestMapping(value = "/admin/**", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.addObject("message", "This is admin page.");
        model.setViewName("admin");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out.");
        }
        model.setViewName("login");
        return model;
    }

    @Loggable
    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public ModelAndView getCarsDto1() {
        CarDto dto = carServiceConsumer.getCarsDto();
        return new ModelAndView("cars", "dto", dto);
    }


    @Loggable
    @RequestMapping(value = "/car/carsbydate", method = RequestMethod.GET)
    public ModelAndView getCarByDateDto1(@RequestParam(value = "dateBefore") String dateBefore,
                                         @RequestParam(value = "dateAfter") String dateAfter) {
        CarDto dto = carServiceConsumer.getCarsDtoByDate(dateBefore, dateAfter);
        return new ModelAndView("cars", "dto", dto);
    }

    @Loggable
    @RequestMapping(value = "/admin/car", method = RequestMethod.GET)
    public ModelAndView getCarDto() {
        CarDto dto = carServiceConsumer.getCarsDto();
        return new ModelAndView("cars", "dto", dto);
    }

    @Loggable
    @RequestMapping(value = "/admin/car/delete/{id}", method = RequestMethod.POST)
    public String deleteCar(@PathVariable(value ="id") Integer carId){
        carServiceConsumer.deleteCar(carId);
        return "redirect:/admin/car";
    }

    @Loggable
    @RequestMapping(value = "/admin/car/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateCarForm(@PathVariable(value = "id") Integer carId){
        ModelAndView modelAndView=new ModelAndView("carform", "car", carServiceConsumer.getCarById(carId));
        modelAndView.addObject("producersdto", producerServiceConsumer.getProducersDto());
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/admin/car/update/{id}", method = RequestMethod.POST)
    public String saveUpdatedCar(Car car) {
        carServiceConsumer.updateCar(car);
        return "redirect:/admin/car";
    }

    @Loggable
    @RequestMapping(value = "/admin/car/add", method = RequestMethod.GET)
    public ModelAndView carAddingForm(){
        ModelAndView modelAndView=new ModelAndView("carform", "car", new Car());
        modelAndView.addObject("producersdto", producerServiceConsumer.getProducersDto());
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/admin/car/add", method = RequestMethod.POST)
    public String addCar(Car car) {
        //adding as new car so setting id to null
        car.setCarId(null);
        carServiceConsumer.addCar(car);
        return "redirect:/admin/car";
    }

    @Loggable
    @RequestMapping(value = "/admin/car/carsbydate", method = RequestMethod.GET)
    public ModelAndView getCarByDateDto(@RequestParam(value="dateBefore") String dateBefore,
                                        @RequestParam(value="dateAfter") String dateAfter){
        CarDto dto = carServiceConsumer.getCarsDtoByDate(dateBefore, dateAfter);
        return new ModelAndView("cars", "dto", dto);
    }


}
