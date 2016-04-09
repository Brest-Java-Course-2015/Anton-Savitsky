package com.epam.brest.course2015.provider;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.transactions.CarTransactions;
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
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class CarRestControllerMockTest {
    @Autowired
    private CarRestController carRestController;

    private MockMvc mockMvc;

    @Autowired
    private CarTransactions carTransactionsMock;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(carRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
        verify(carTransactionsMock);
        reset(carTransactionsMock);
    }


    @Test
    public void testAddCar() throws Exception {
        expect(carTransactionsMock.addCar(anyObject(Car.class))).andReturn(3);
        replay(carTransactionsMock);
        String car = new ObjectMapper().writeValueAsString(new Car("ert",convertToDate("15/09/2013"),new Producer(1)));
        System.out.println(car);
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
        carTransactionsMock.updateCar(anyObject(Car.class));
        expectLastCall().once();
        replay(carTransactionsMock);
        String car=new ObjectMapper().writeValueAsString(new Car(0,"ert",convertToDate("15/09/2013"), new Producer(1)));
        System.out.println(car);
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
        expect(carTransactionsMock.getCarById(1)).andReturn(new Car(1));
        replay(carTransactionsMock);
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
        carTransactionsMock.deleteCar(1);
        expectLastCall().once();
        replay(carTransactionsMock);
        mockMvc.perform(
                delete("/car/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllCars() throws Exception {
        expect(carTransactionsMock.getAllCars()).andReturn(Arrays.<Car>asList(new Car(0), new Car(1)));
        replay(carTransactionsMock);
        mockMvc.perform(
                get("/car")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCarsByDateDto() throws Exception {
        expect(carTransactionsMock.getCarsDtoByDate(anyObject(LocalDate.class), anyObject(LocalDate.class))).andReturn(new CarDto());
        replay(carTransactionsMock);
        mockMvc.perform(
                get("/car/dto/date?dateBefore=10/10/2015&dateAfter=15/10/2015")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"cars\":null,\"total\":null}"));
    }

    @Test
    public void getCarsDto() throws Exception {
        expect(carTransactionsMock.getCarsDto()).andReturn(new CarDto());
        replay(carTransactionsMock);
        mockMvc.perform(
                get("/car/dto")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCarsByDateOfCreation() throws Exception {
        expect(carTransactionsMock.getListOfCarsByDateOfCreation(anyObject(LocalDate.class),anyObject(LocalDate.class))).andReturn(Arrays.<Car>asList(new Car(0), new Car(1)));
        replay(carTransactionsMock);
        mockMvc.perform(
                get("/car/date?dateBefore=10/10/2015&dateAfter=15/10/2015")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public LocalDate convertToDate(String s){
        DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DATE_FORMAT.parseLocalDate(s);
    }
}
