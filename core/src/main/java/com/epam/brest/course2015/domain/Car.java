package com.epam.brest.course2015.domain;

import com.epam.brest.course2015.test.Loggable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created by antonsavitsky on 09.11.15.
 */
@Entity
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
@Table(name = "CAR")
public class Car {
    //!!!NEW
    @Id
    @Column(name="carId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(name = "carName")
    private String carName;

    @JsonDeserialize(using=com.epam.brest.course2015.test.JsonDateDeserializer.class)
    @JsonSerialize(using=com.epam.brest.course2015.test.JsonDateSerializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name="dateOfCreation")
    private LocalDate dateOfCreation;


    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name="producerId")
    //@JsonManagedReference
    private Producer producer;

    public Car(String carName, LocalDate dateOfCreation){
        this.carName=carName;
        this.dateOfCreation=dateOfCreation;
    }

    //main constructor
    public Car(Integer carId, String carName, LocalDate dateOfCreation, Producer producer){
        this.carId=carId;
        this.producer=producer;
        this.carName=carName;
        this.dateOfCreation=dateOfCreation;
    }

    public Car(Integer carId, String carName, LocalDate dateOfCreation){
        this.carId=carId;
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
    public Producer getProducer() {
        return producer;
    }

    @Loggable
    public void setProducer(Producer producer) {
        this.producer = producer;
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
                ", producerId="+ (producer==null ? "no": producer.getProducerId())+
                ", producerName=" +(producer==null ? "no": producer.getProducerName())+
                '}';
    }
}
