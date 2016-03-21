package com.epam.brest.course2015.soap;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.transactions.CarTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.AbstractMarshallingPayloadEndpoint;
/**
 * Created by antonsavitsky on 3/20/16.
 */
public class GetCarByIdEndpoint
        extends AbstractMarshallingPayloadEndpoint {


    private CarTransactions carTransactions;
    @Autowired
    public void setCarTransactions(CarTransactions carTransactions){
        this.carTransactions=carTransactions;
    }

    @Override
    protected Object invokeInternal(Object o) throws Exception {
        GetCarByIdRequest getCarByIdRequest=(GetCarByIdRequest) o;

        int carId=getCarByIdRequest.getCarId();
        Car car=carTransactions.getCarById(carId);
        return new GetCarByIdResponse(car);
    }
}
