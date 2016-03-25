package com.epam.brest.course2015.soap;

import com.epam.brest.course2015.core.Car;
import com.epam.brest.course2015.core.Producer;
import com.epam.brest.course2015.test.Loggable;
import com.epam.brest.course2015.transactions.CarTransactions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

/**
 * Created by antonsavitsky on 3/22/16.
 */
@Endpoint
public class CarEndpoint {

    private static final String TARGET_NAMESPACE =
            "http://epam.com/brest/course2015/soap";

    @Autowired
    private CarTransactions carTransactions;

    private final Logger LOGGER = LogManager.getLogger();


    /*
        process service requests with the XML root element
        matching that defined by the localPart attribute
    */
    @PayloadRoot(localPart = "GetCarByIdRequest", namespace = TARGET_NAMESPACE)
    @Loggable
    public @ResponsePayload GetCarByIdResponse getCarById(@RequestPayload GetCarByIdRequest getCarByIdRequest){
        com.epam.brest.course2015.domain.Car car=
                carTransactions.getCarById(getCarByIdRequest.getCarId());

        Car carXmlType=convertCarToCarXmlType(car);

        GetCarByIdResponse getCarByIdResponse=new GetCarByIdResponse();
        getCarByIdResponse.setCar(carXmlType);

        return getCarByIdResponse;
    }


    public static Car convertCarToCarXmlType(com.epam.brest.course2015.domain.Car car){
        Car carXmlType=new Car();

        carXmlType.setCarId(car.getCarId());
        carXmlType.setCarName(car.getCarName());
        carXmlType.setDateOfCreation(asXMLGregorianCalendar(car.getDateOfCreation()));

        carXmlType.setProducer(convertProducerToProducerXmlType(car.getProducer()));

        return carXmlType;
    }


    public static Producer convertProducerToProducerXmlType(com.epam.brest.course2015.domain.Producer producer){
        Producer producerXmlType=new Producer();

        producerXmlType.setProducerId(producer.getProducerId());
        producerXmlType.setProducerName(producer.getProducerName());
        producerXmlType.setCountry(producer.getCountry());

        return producerXmlType;
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
}
