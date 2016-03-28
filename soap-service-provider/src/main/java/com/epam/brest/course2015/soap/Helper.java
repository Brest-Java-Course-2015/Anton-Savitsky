package com.epam.brest.course2015.soap;

import com.epam.brest.course2015.core.Car;
import com.epam.brest.course2015.core.Cardto;
import com.epam.brest.course2015.core.Cars;
import com.epam.brest.course2015.core.Producer;
import org.joda.time.LocalDate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by antonsavitsky on 3/26/16.
 */
public class Helper {

    public static Car convertCarToCarXmlType(com.epam.brest.course2015.domain.Car car){
        Car carXmlType=new Car();

        carXmlType.setCarId(car.getCarId());
        carXmlType.setCarName(car.getCarName());
        carXmlType.setDateOfCreation(car.getDateOfCreation());

        carXmlType.setProducer(convertProducerToProducerXmlType(car.getProducer()));

        return carXmlType;
    }

    public static com.epam.brest.course2015.domain.Car convertCarXmlTypeToCar(Car carXmlType){
        com.epam.brest.course2015.domain.Car car=new com.epam.brest.course2015.domain.Car();

        car.setCarId(carXmlType.getCarId());
        car.setCarName(carXmlType.getCarName());
        car.setDateOfCreation(carXmlType.getDateOfCreation());

        car.setProducer(convertProducerXmlTypeToProducer(carXmlType.getProducer()));

        return car;
    }

    public static Cardto convertCardtoToCardtoXml(com.epam.brest.course2015.dto.CarDto carDto){
        List<com.epam.brest.course2015.domain.Car> carList=carDto.getCars();
        List<Car> carXmlList=new ArrayList<>();

        Cars xmlCars=new Cars();
        for (com.epam.brest.course2015.domain.Car car: carList)
            xmlCars.getCar().add(convertCarToCarXmlType(car));

        Cardto xmlCardto=new Cardto();

        xmlCardto.setCars(xmlCars);
        xmlCardto.setTotal(xmlCars.getCar().size());

        return xmlCardto;
    }


    public static Producer convertProducerToProducerXmlType(com.epam.brest.course2015.domain.Producer producer){
        Producer producerXmlType=new Producer();

        producerXmlType.setProducerId(producer.getProducerId());
        producerXmlType.setProducerName(producer.getProducerName());
        producerXmlType.setCountry(producer.getCountry());

        return producerXmlType;
    }

    public static com.epam.brest.course2015.domain.Producer convertProducerXmlTypeToProducer(Producer producerXmlType){
        com.epam.brest.course2015.domain.Producer producer=new com.epam.brest.course2015.domain.Producer();

        producer.setProducerId(producerXmlType.getProducerId());
        producer.setProducerName(producerXmlType.getProducerName());
        producer.setCountry(producerXmlType.getCountry());

        return producer;
    }

    public static XMLGregorianCalendar asXMLGregorianCalendar(LocalDate date) {
        DatatypeFactory df= null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        if(date == null) {
            return null;
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(date.toDateTimeAtStartOfDay().toDate().getTime());
            return df.newXMLGregorianCalendar(gc);
        }
    }

    private static LocalDate asLocalDate(XMLGregorianCalendar xmlGregorianCalendar){
        if (xmlGregorianCalendar == null) {
            return null;
        } else {
            return new LocalDate(xmlGregorianCalendar.toGregorianCalendar().getTime());
        }
    }
}
