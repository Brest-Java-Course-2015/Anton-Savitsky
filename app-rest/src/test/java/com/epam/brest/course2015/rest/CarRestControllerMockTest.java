package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import java.util.Arrays;
import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
    @Autowired
    private CarRestController carRestController;

    private MockMvc mockMvc;

    @Autowired
    private CarService carServiceMock;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(carRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
        verify(carServiceMock);
        reset(carServiceMock);
    }

    @Test
    public void testAddCar() throws Exception {
        expect(carServiceMock.addCar(anyObject(Car.class))).andReturn(3);
        replay(carServiceMock);
        String car = new ObjectMapper().writeValueAsString(new Car("ert",2,DATE_FORMAT.parseLocalDate("15/09/2013")));
        mockMvc.perform(
                post("/car")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(car))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("3"));
    }

    @Test
    public void testUpdateCar() throws Exception {
        carServiceMock.updateCar(anyObject(Car.class));
        expectLastCall().once();
        replay(carServiceMock);
        String car=new ObjectMapper().writeValueAsString(new Car(0,"ert",2,DATE_FORMAT.parseLocalDate("15/09/2013")));
        mockMvc.perform(
                put("/car")
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(car))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCarById() throws Exception {
        expect(carServiceMock.getCarById(1)).andReturn(new Car(1));
        replay(carServiceMock);
        String car=new ObjectMapper().writeValueAsString(new Car(1));
        mockMvc.perform(
                get("/car/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(car));
    }

    @Test
    public void testDeleteCar() throws Exception {
        carServiceMock.deleteCar(1);
        expectLastCall().once();
        replay(carServiceMock);
        mockMvc.perform(
                delete("/car/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllCars() throws Exception {
        expect(carServiceMock.getAllCars()).andReturn(Arrays.<Car>asList(new Car(0), new Car(1)));
        replay(carServiceMock);
        String cars="["+new ObjectMapper().writeValueAsString(new Car(0))+","+new ObjectMapper().writeValueAsString(new Car(1))+"]";
        mockMvc.perform(
                get("/car")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(content().string(cars));
    }

    @Test
    public void testGetCarsByDateDto() throws Exception {
        expect(carServiceMock.getCarsByDateDto(anyObject(LocalDate.class), anyObject(LocalDate.class))).andReturn(new CarDto());
        replay(carServiceMock);
        mockMvc.perform(
                get("/car/dto/date?dateBefore=10/10/2015&dateAfter=15/10/2015")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"cars\":null,\"total\":null}"));
    }

    @Test
    public void getCarsDto() throws Exception {
        expect(carServiceMock.getCarsDto()).andReturn(new CarDto());
        replay(carServiceMock);
        mockMvc.perform(
                get("/car/dto")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"cars\":null,\"total\":null}"));
    }

    @Test
    public void getCarsByDateOfCreation() throws Exception {
        expect(carServiceMock.getListOfCarsByDateOfCreation(anyObject(LocalDate.class),anyObject(LocalDate.class))).andReturn(Arrays.<Car>asList(new Car(0), new Car(1)));
        replay(carServiceMock);
        String cars="["+new ObjectMapper().writeValueAsString(new Car(0))+","+new ObjectMapper().writeValueAsString(new Car(1))+"]";
        mockMvc.perform(
                get("/car/date?dateBefore=10/10/2015&dateAfter=15/10/2015")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(cars));
    }
}
