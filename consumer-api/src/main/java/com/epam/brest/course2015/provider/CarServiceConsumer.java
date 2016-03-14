package com.epam.brest.course2015.provider;
import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
import java.util.List;

/**
 * Created by antonsavitsky on 3/12/16.
 */
public interface CarServiceConsumer {
    void updateCar(Car car);
    List<Car> getAllCars();
    Car getCarById(Integer id);
    Integer addCar(Car car);
    List<Car> getListOfCarsByDateOfCreation(String dateBefore,String dateAfter);
    void deleteCar(Integer id);
    CarDto getCarsDto();
    CarDto getCarsDtoByDate(String dateBefore, String dateAfter);
    CarPagingDto getInitPaging(Integer from, Integer to);
    CarPagingDto getNextPage(Integer from, Integer to);
}
