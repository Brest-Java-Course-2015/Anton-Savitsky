package com.epam.brest.course2015.soap;

import com.epam.brest.course2015.transactions.CarTransactions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

/**
 * Created by antonsavitsky on 3/20/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:endpoint-mock-test.xml"})
public class TestGetCarByIdMarshallerEndpoint {
/*
    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Autowired
    private CarTransactions carTransactionsMock;

    @Before
    public void setUp(){
        mockClient=MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void testGetCarById(){
        Source requestPayload=new StringSource(
                "<GetCarByIdRequest>"+
                        "<carId>1</carId>"+
                "</GetCarByIdRequest>");
        Source responsePayload=new StringSource(
                "<GetCarByIdResponse>"+
                    "<car>"+
                        "<carId>1</carId>"+
                        "<carName>car</carName>"+
                        "<dateOfCreation>10/10/2015</dateOfCreation>"+
                        "<producer>"+
                            "<producerId>1</producerId>"+
                            "<producerName>producer</producerName>"+
                            "<country>country</country>"+
                        "</producer>"+
                    "</car>"+
                "</GetCarByIdResponse>");

        mockClient.sendRequest(withPayload(requestPayload)).andExpect(payload(responsePayload));
    }
*/
}
