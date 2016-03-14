package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.test.Loggable;

import java.util.List;

/**
 * Created by antonsavitsky on 30.11.15.
 */
public class CarDto {
    private List<Car> cars;
    private Integer total;
    @Loggable
    public Integer getTotal() {
        return total;
    }
    @Loggable
    public void setTotal(Integer total) {
        this.total = total;
    }
    @Loggable
    public List<Car> getCars() {
        return cars;
    }
    @Loggable
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
