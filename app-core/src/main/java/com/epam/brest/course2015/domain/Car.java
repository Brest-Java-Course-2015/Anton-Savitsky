package com.epam.brest.course2015.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by antonsavitsky on 09.11.15.
 */
public class Car {
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private Integer carId;
    private Integer producerId;
    private String carName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateOfCreation;

    public Car(Integer carId, String carName, Integer producerId, Date dateOfCreation){
        this.carId=carId;
        this.producerId=producerId;
        this.carName=carName;
        this.dateOfCreation=dateOfCreation;
    }

    public Car(){}

    public Car(Integer carId){
        this.carId=carId;
    }

    public Car(String carName, int producerId, Date dateOfCreation){
        this.carName=carName;
        this.producerId=producerId;
        this.dateOfCreation=dateOfCreation;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public enum CarFields {
        CAR_ID("carId"),
        CAR_NAME("carName"),
        PRODUCER_ID("producerId"),
        DATE_OF_CREATION("dateOfCreation");

        CarFields(String value) {
            this.value = value;
        }
        private final String value;
        public String getValue() { return value; }
    }

    @Override
    public String toString() {
        return String.format("Car: {" +
                "carId=" + carId +
                ", carName='" + carName +
                ", producerId=" + producerId +
                ", dateOfCreation=" + DATE_FORMAT.format(dateOfCreation) +
                '}');
    }
}
