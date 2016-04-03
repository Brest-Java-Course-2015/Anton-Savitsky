package com.epam.brest.course2015.soap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.test.server.MockWebServiceClient;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import java.io.IOException;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

/**
 * Created by antonsavitsky on 3/20/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:endpoint-mock-test.xml"})
@Transactional
public class TestGetCarByIdMarshallerEndpoint {

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void setUp(){
        Assert.assertNotNull(applicationContext);
        mockClient=MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void testGetCarById() throws IOException {
        Source requestPayload=new StreamSource(new ClassPathResource("soap-messages/GetCarByIdRequestTestMessage.xml").getFile());

        Source responsePayload=new StreamSource(new ClassPathResource("soap-messages/GetCarByIdResponseTestMessage.xml").getFile());

        mockClient.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }

    @Test
    public void testUpdateCarById() throws IOException {
        Source requestPayload=new StreamSource(new ClassPathResource("soap-messages/UpdateCarRequestTestMessage.xml").getFile());

        Source responsePayload=new StreamSource(new ClassPathResource("soap-messages/UpdateCarResponseTestMessage.xml").getFile());

        mockClient.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }

    @Test
    public void testGetCarDto() throws IOException {
        Source requestPayload=new StreamSource(new ClassPathResource("soap-messages/GetCarDtoRequestTestMessage.xml").getFile());

        Source responsePayload=new StreamSource(new ClassPathResource("soap-messages/GetCarDtoResponseTestMessage.xml").getFile());

        mockClient.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }

}
