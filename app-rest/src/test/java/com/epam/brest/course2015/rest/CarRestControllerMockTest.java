package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by antonsavitsky on 26.11.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-spring-mock-test.xml"})
public class CarRestControllerMockTest {
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static final Logger LOGGER = LogManager.getLogger();
    @Resource
    private CarRestController carRestController;
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(carRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
        verify(carService);
        reset(carService);
    }

    @Test
    public void testAddCar() throws Exception {
        expect(carService.addCar(anyObject(Car.class))).andReturn(4);
        replay(carService);
        String car = new ObjectMapper().writeValueAsString(new Car("ert",2,DATE_FORMAT.parse("15/09/2013")));
        mockMvc.perform(
                post("/car")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(car))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("4"));
    }

    @Test
    public void testGetCarById() throws Exception {
        expect(carService.getCarById(1)).andReturn(new Car(1));
        replay(carService);
        mockMvc.perform(
                get("/car?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
