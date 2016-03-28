package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
import com.epam.brest.course2015.provider.CarServiceConsumer;
import com.epam.brest.course2015.soap.GetCarByIdRequest;
import com.epam.brest.course2015.soap.GetCarByIdResponse;
import com.epam.brest.course2015.test.Loggable;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import com.epam.brest.course2015.soap.Helper;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * Created by antonsavitsky on 3/25/16.
 */
public class CarSoapConsumer implements CarServiceConsumer {
    @Autowired
    private org.springframework.oxm.Marshaller marshaller;

    @Autowired
    private org.springframework.oxm.Unmarshaller unmarshaller;

    private WebServiceTemplate webServiceTemplate;
    @Autowired
    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate=webServiceTemplate;
    }

    @Override
    public void updateCar(Car car) {
        final StreamSource sourceToSend=new StreamSource(new StringReader(
                "<soap:UpdateCarRequest xmlns:soap=\"http://epam.com/brest/course2015/soap\" " +
                        "xmlns:core=\"http://epam.com/brest/course2015/core\">"+
                    "<core:car>"+
                        "<core:carId>"+car.getCarId()+"</core:carId>"+
                        "<core:carName>"+car.getCarName()+"</core:carName>"+
                        "<core:dateOfCreation>"+car.getDateOfCreation().toString("yyyy-MM-dd")+"</core:dateOfCreation>"+
                        "<core:producer>"+
                            "<core:producerId>"+car.getProducer().getProducerId()+"</core:producerId>"+
                            "<core:producerName>"+car.getProducer().getProducerName()+"</core:producerName>"+
                        "</core:producer>"+
                    "</core:car>"+
                "</soap:UpdateCarRequest>"
        ));

        StreamResult result = new StreamResult(System.out);
        webServiceTemplate.sendSourceAndReceiveToResult(sourceToSend, result);
    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }

    @Override
    @Loggable
    public Car getCarById(Integer id){
        final StreamSource sourceToSend=new StreamSource(new StringReader(
                "<soap:GetCarByIdRequest xmlns:soap=\"http://epam.com/brest/course2015/soap\" " +
                        "xmlns:core=\"http://epam.com/brest/course2015/core\">"+
                "<core:carId>"+id+"</core:carId>"+
                "</soap:GetCarByIdRequest>"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(byteArrayOutputStream);
        webServiceTemplate.sendSourceAndReceiveToResult(sourceToSend, result);
        final String reply = new String(byteArrayOutputStream.toByteArray());

        StreamSource sourceToReceive=new StreamSource(new StringReader(reply));
        GetCarByIdResponse getCarByIdResponse=new GetCarByIdResponse();

        try {
            getCarByIdResponse=(GetCarByIdResponse)this.unmarshaller.unmarshal(sourceToReceive);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Helper.convertCarXmlTypeToCar(getCarByIdResponse.getCar());
    }

    @Override
    public Integer addCar(Car car) {
        return null;
    }

    @Override
    public List<Car> getListOfCarsByDateOfCreation(String dateBefore, String dateAfter) {
        return null;
    }

    @Override
    public void deleteCar(Integer id) {

    }

    @Override
    public CarDto getCarsDto() {
        return null;
    }

    @Override
    public CarDto getCarsDtoByDate(String dateBefore, String dateAfter) {
        return null;
    }

    @Override
    public CarPagingDto getInitPaging(Integer from, Integer to) {
        return null;
    }

    @Override
    public CarPagingDto getNextPage(Integer from, Integer to) {
        return null;
    }

}
