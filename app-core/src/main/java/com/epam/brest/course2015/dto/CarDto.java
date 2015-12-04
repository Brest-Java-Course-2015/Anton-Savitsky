package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.Car;

import java.util.List;

/**
 * Created by antonsavitsky on 30.11.15.
 */
public class CarDto {
    private List<Car> cars;
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
