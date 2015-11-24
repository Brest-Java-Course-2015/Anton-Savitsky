package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.service.CarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by juga on 16.10.15.
 */
@RestController
public class CarRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CarService carService;

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public @ResponseBody List<Car> getAllCars() {
        LOGGER.debug("getUsers()");
        return carService.getAllCars();
    }
}
