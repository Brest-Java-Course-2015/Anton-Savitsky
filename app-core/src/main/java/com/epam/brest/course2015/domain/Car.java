package com.epam.brest.course2015.domain;

import com.epam.brest.course2015.test.Loggable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDate;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public class    Car {
    private Integer carId;
    private Integer producerId;
    private String carName;
    @JsonDeserialize(using=com.epam.brest.course2015.test.JsonDateDeserializer.class)
    @JsonSerialize(using=com.epam.brest.course2015.test.JsonDateSerializer.class)
    private LocalDate dateOfCreation;

    public Car(Integer carId, String carName, Integer producerId, LocalDate dateOfCreation){
        this.carId=carId;
        this.producerId=producerId;
        this.carName=carName;
        this.dateOfCreation=dateOfCreation;
    }

    public Car(){}

    public Car(Integer carId){
        this.carId=carId;
    }

    public Car(String carName, int producerId, LocalDate dateOfCreation){
        this.carName=carName;
        this.producerId=producerId;
        this.dateOfCreation=dateOfCreation;
    }
    @Loggable
    public Integer getProducerId() {
        return producerId;
    }
    @Loggable
    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }
    @Loggable
    public Integer getCarId() {
        return carId;
    }
    @Loggable
    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    @Loggable
    public String getCarName() {
        return carName;
    }
    @Loggable
    public void setCarName(String carName) {
        this.carName = carName;
    }
    @Loggable
    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }
    @Loggable
    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return String.format("Car: {" +
                "carId=" + carId +
                ", carName='" + carName +
                ", producerId=" + producerId +
                ", dateOfCreation=" + dateOfCreation +
                '}');
    }
}
