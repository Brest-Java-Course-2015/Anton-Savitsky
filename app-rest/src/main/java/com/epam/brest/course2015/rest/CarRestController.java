package com.epam.brest.course2015.rest;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.service.CarService;
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
@CrossOrigin
@RestController
public class CarRestController {
    // При использовании методов put, post указывать request headers:
    //Content-Type: application/json
    //Accept-Language: application/json
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private CarService carService;

    @RequestMapping(value="/car", method = RequestMethod.PUT)
    public void updateCar(@RequestBody Car car){
        LOGGER.debug("Updating car with id={}", car.getCarId());
        carService.updateCar(car);
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public @ResponseBody List<Car> getAllCars() {
        LOGGER.debug("getAllCars()");
        return carService.getAllCars();
    }

    @RequestMapping(value="/car", method = RequestMethod.GET)
    public @ResponseBody Car getCarById(@RequestParam(value="id") Integer id){
        LOGGER.debug("Getting car by its id={}", id);
        return carService.getCarById(id);
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Integer addCar(@RequestBody Car car) throws ParseException {
        LOGGER.debug("Adding new car with id={}", car.getCarId());
        return carService.addCar(car);
    }

    @RequestMapping(value="/car/date", method = RequestMethod.GET)
    public @ResponseBody List<Car> getListOfCarsByDateOfCreation(@RequestParam(value = "dateBefore")
                                                                 String dateBefore,
                                                                 @RequestParam(value = "dateAfter")
                                                                 String dateAfter) throws ParseException {

        LOGGER.debug("getting list of cars by date interval (from,to)=({},{})",dateBefore,dateAfter);
        return carService.getListOfCarsByDateOfCreation(DATE_FORMAT.parse(dateBefore), DATE_FORMAT.parse(dateAfter));
    }

    @RequestMapping(value = "/car",
            method = RequestMethod.DELETE)
    public void deleteCar(@RequestParam(value = "id")
                          Integer id) {
        LOGGER.debug("Deleting a car {}", id);
        carService.deleteCar(id);
    }

    @RequestMapping(value="/carsdto",
            method = RequestMethod.GET)
    public @ResponseBody CarDto getCarsDto(){
        LOGGER.debug("Getting cars dto");
        return carService.getCarsDto();
    }

    @RequestMapping(value="/carsdtobydate",
            method = RequestMethod.GET)
    public @ResponseBody CarDto getCarsDtoByDate(@RequestParam(value = "dateBefore")
                                                 String dateBefore,
                                                 @RequestParam(value = "dateAfter")
                                                 String dateAfter) throws ParseException {
        LOGGER.debug("Getting cars dto by date");
        return carService.getCarsByDateDto(DATE_FORMAT.parse(dateBefore), DATE_FORMAT.parse(dateAfter));
    }
}