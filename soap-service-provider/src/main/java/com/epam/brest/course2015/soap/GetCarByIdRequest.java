package com.epam.brest.course2015.soap;

import com.epam.brest.course2015.domain.Car;

/**
 * Created by antonsavitsky on 3/20/16.
 */
public class GetCarByIdRequest {
    private Integer carId;

    public GetCarByIdRequest(){
        carId=null;
    }

    public GetCarByIdRequest(Integer carId){
        this.carId=carId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
