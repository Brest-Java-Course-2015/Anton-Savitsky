package com.epam.brest.course2015.soap;

import com.epam.brest.course2015.core.Car;
import com.epam.brest.course2015.core.Producer;
import com.epam.brest.course2015.dto.CarDto;
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

import static com.epam.brest.course2015.soap.Helper.convertCarToCarXmlType;
import static com.epam.brest.course2015.soap.Helper.convertCarXmlTypeToCar;
import static com.epam.brest.course2015.soap.Helper.convertCardtoToCardtoXml;

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

        System.out.println();
        com.epam.brest.course2015.domain.Car car=
                carTransactions.getCarById(getCarByIdRequest.getCarId());

        Car carXmlType=convertCarToCarXmlType(car);

        GetCarByIdResponse getCarByIdResponse=new GetCarByIdResponse();
        getCarByIdResponse.setCar(carXmlType);

        return getCarByIdResponse;
    }

    @PayloadRoot(localPart = "UpdateCarRequest", namespace = TARGET_NAMESPACE)
    public @ResponsePayload UpdateCarResponse updateCar(@RequestPayload UpdateCarRequest updateCarRequest){
        carTransactions.updateCar(convertCarXmlTypeToCar(updateCarRequest.getCar()));
        return new UpdateCarResponse();
    }

    @PayloadRoot(localPart = "GetCarDtoRequest", namespace = TARGET_NAMESPACE)
    public @ResponsePayload GetCarDtoResponse getCarDtoRequest(@RequestPayload GetCarDtoRequest getCarDtoRequest){
        CarDto carDto=carTransactions.getCarsDto();

        GetCarDtoResponse getCarDtoResponse=new GetCarDtoResponse();
        getCarDtoResponse.setCardto(convertCardtoToCardtoXml(carDto));

        return getCarDtoResponse;
    }
}
