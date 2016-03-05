package com.epam.brest.course2015.domain;

import com.epam.brest.course2015.test.Loggable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created by antonsavitsky on 09.11.15.
 */
@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @Column(name="carId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(name = "producerId")
    private Integer producerId;

    @Transient
    private String producerName;


    @Column(name = "carName")
    private String carName;

    @JsonDeserialize(using=com.epam.brest.course2015.test.JsonDateDeserializer.class)
    @JsonSerialize(using=com.epam.brest.course2015.test.JsonDateSerializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name="dateOfCreation")
    private LocalDate dateOfCreation;


    public Car(Integer carId, String carName, Integer producerId, LocalDate dateOfCreation, String producerName){
        this.carId=carId;
        this.producerId=producerId;
        this.carName=carName;
        this.dateOfCreation=dateOfCreation;
        this.producerName=producerName;
    }

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


    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    @Override
    public String toString() {
        return "Car: {" +
                "carId=" + carId +
                ", carName='" + carName +
                ", producerId=" + producerId +
                ", dateOfCreation=" + dateOfCreation +
                ", producerName=" + producerName +
                '}';
    }
/*
    //!!!NEW
    @Id
    @Column(name="carId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @ManyToOne
    @JoinColumn(name = "producerId")
    private Producer producer;

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Column(name = "carName")
    private String carName;

    @JsonDeserialize(using=com.epam.brest.course2015.test.JsonDateDeserializer.class)
    @JsonSerialize(using=com.epam.brest.course2015.test.JsonDateSerializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name="dateOfCreation")
    private LocalDate dateOfCreation;


    public Car(Integer carId, String carName, LocalDate dateOfCreation, Producer producer){
        this.carId=carId;
        this.producer=producer;
        this.carName=carName;
        this.dateOfCreation=dateOfCreation;
    }

    public Car(){}

    public Car(Integer carId){
        this.carId=carId;
    }

    public Car(String carName, LocalDate dateOfCreation, Producer producer){
        this.carName=carName;
        this.producer=producer;
        this.dateOfCreation=dateOfCreation;
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
        return "Car: {" +
                "carId=" + carId +
                ", carName='" + carName +
                ", dateOfCreation=" + dateOfCreation +
                ", producerId=" + producer.getProducerId() +
                ", producerName=" + producer.getProducerName() +
                ", country="+producer.getCountry()+
                '}';
    }
    */
}
