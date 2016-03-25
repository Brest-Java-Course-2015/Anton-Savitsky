package com.epam.brest.course2015.consumer;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.dto.CarPagingDto;
import com.epam.brest.course2015.provider.CarServiceConsumer;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.List;

/**
 * Created by antonsavitsky on 3/25/16.
 */
public class CarSoapConsumer implements CarServiceConsumer {

    private static final String SERVICE_PREFIX="http://localhost:8080/";

    private WebServiceTemplate webServiceTemplate;
    @Autowired
    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate=webServiceTemplate;
    }

    @Override
    public void updateCar(Car car) {

    }

    @Override
    public List<Car> getAllCars() {
        return null;
    }

    @Override
    @Loggable
    public Car getCarById(Integer id) {
        StreamSource source=new StreamSource(new StringReader(
                "<soap:GetCarByIdRequest " +
                "xmlns:soap=\"http://epam.com/brest/course2015/soap\">"+
                "<soap:carId>"+id+"</soap:carId>"+
                "</soap:GetCarByIdRequest>"));

        String resultStr=new String();
        StreamResult result=new StreamResult(System.out);
        webServiceTemplate.sendSourceAndReceiveToResult(source, result);
        //System.out.println("Returned: "+resultStr);
        return new Car();
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
