package com.epam.brest.course2015.domain;

import com.epam.brest.course2015.test.Loggable;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by antonsavitsky on 02.12.15.
 */
@Entity
@Table(name = "PRODUCER")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "producerId")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class Producer {
    @Id
    @Column(name = "producerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer producerId;

    @Column(name = "producerName")
    private String producerName;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "producer", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    //@JsonBackReference
    @Transient
    @JsonIgnore
    private List<Car> cars;

    @Transient
    private int countOfCars;

    public Producer(Integer producerId){
        this.producerId=producerId;
    }

    public Producer(Integer producerId, String producerName){
        this.producerId=producerId;
        this.producerName=producerName;
    }

    public Producer(Integer producerId, String producerName, String country){
        this.producerId=producerId;
        this.producerName=producerName;
        this.country=country;
    }

    public Producer(Integer producerId, String producerName, String country, List<Car> cars){
        this.producerId=producerId;
        this.producerName=producerName;
        this.country=country;
        this.cars=cars;
    }

    public Producer(String producerName, String country){
        this.producerName=producerName;
        this.country=country;
    }

    public Producer(String producerName){
        this.producerName=producerName;
    }

    public Producer(){}

    @Loggable
    public int getCountOfCars() {
        return countOfCars;
    }

    @Loggable
    public void setCountOfCars(int countOfCars) {
        this.countOfCars = countOfCars;
    }

    @Loggable
    public List<Car> getCars() {
        return cars;
    }

    @Loggable
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Loggable
    public Integer getProducerId() {
        return producerId;
    }
    @Loggable
    public void setProducerId(Integer  producerId) {
        this.producerId = producerId;
    }
    @Loggable
    public String getProducerName() {
        return producerName;
    }
    @Loggable
    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }
    @Loggable
    public String getCountry() {
        return country;
    }
    @Loggable
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("Producer: {" +
                "producerId=" + producerId +
                ", producerName='" + producerName +
                ", country=" + country +
                ", countOfCars="+ (cars==null ? 0: cars.size())+
                "\nCars: "+ (cars==null ? "null" : cars.toString())+
                '}');
    }
}
