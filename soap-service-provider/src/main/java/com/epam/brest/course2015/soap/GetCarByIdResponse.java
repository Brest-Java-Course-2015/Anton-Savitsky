package com.epam.brest.course2015.soap;

import com.epam.brest.course2015.domain.Car;

/**
 * Created by antonsavitsky on 3/20/16.
 */
public class GetCarByIdResponse {
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public GetCarByIdResponse(Car car) {
        this.car = car;
    }

    public GetCarByIdResponse(){
        car=null;
    }
}
