package com.epam.brest.course2015.domain;

import com.epam.brest.course2015.test.Loggable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by antonsavitsky on 02.12.15.
 */
@Entity
@Table(name = "PRODUCER")
public class Producer {
    @Id
    @Column(name = "producerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer producerId;

    @Column(name = "producerName")
    private String producerName;

    @Column(name = "country")
    private String country;

    @Transient
    private Integer countOfCars;

    public Producer(Integer producerId, String producerName, String country){
        this.producerId=producerId;
        this.producerName=producerName;
        this.country=country;
    }

    public Producer(Integer producerId, String producerName, String country, Integer countOfCars){
        this.producerId=producerId;
        this.producerName=producerName;
        this.country=country;
        this.countOfCars=countOfCars;
    }

    public Producer(String producerName, String country){
        this.producerName=producerName;
        this.country=country;
    }

    public Producer(String producerName){
        this.producerName=producerName;
    }

    public Producer(){}

    public Producer(Integer producerId){
        this.producerId=producerId;
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
    @Loggable
    public Integer getCountOfCars(){ return countOfCars;}
    @Loggable
    public void setCountOfCars(Integer countOfCars){this.countOfCars=countOfCars;}

    @Override
    public String toString() {
        return String.format("Producer: {" +
                "producerId=" + producerId +
                ", producerName='" + producerName +
                ", country=" + country +
                ", countOfCars="+ countOfCars+
                '}');
    }

}
