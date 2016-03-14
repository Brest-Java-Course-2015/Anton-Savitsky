package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.transactions.CarTransactions;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.easymock.EasyMock.*;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by antonsavitsky on 21.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-config.xml"})
@WebAppConfiguration
public class TestCarWebController {

    @Autowired
    private CarWebController carWebController;

    @Autowired
    private CarTransactions carTransactionsMock;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        InternalResourceViewResolver viewResolver =
                new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = standaloneSetup(carWebController)
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter())
                .setViewResolvers(viewResolver)
                .build();
    }

    @After
    public void tearDown(){
        verify(carTransactionsMock);
        reset(carTransactionsMock);
    }

    @Test
    public void testGetCarDto() throws Exception {
        expect(carTransactionsMock.getCarsDto()).andReturn(new CarDto());
        replay(carTransactionsMock);
        mockMvc.perform(
                get("/car")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/cars.jsp"));
    }

    @Test
    public void testDeleteCar() throws Exception {
        carTransactionsMock.deleteCar(1);
        expectLastCall().once();
        replay(carTransactionsMock);
        mockMvc.perform(
                post("/car/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car"));
    }

    @Test
    public void testUpdateCarForm() throws Exception {
        expect(carTransactionsMock.getCarById(1)).andReturn(new Car(1));
        replay(carTransactionsMock);
        mockMvc.perform(
               get("/car/update/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/carform.jsp"));
    }

    @Test
    public void testSaveUpdatedCar() throws Exception {
        carTransactionsMock.updateCar(anyObject(Car.class));
        expectLastCall().once();
        replay(carTransactionsMock);
        mockMvc.perform(
                post("/car/update/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car"));
    }

    @Test
    public void testAddCar() throws Exception {
        expect(carTransactionsMock.addCar(anyObject(Car.class))).andReturn(1);
        replay(carTransactionsMock);
        mockMvc.perform(
                post("/car/add")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car"));
    }

    @Test
    public void testGetCarsByDateDto() throws Exception {
        expect(carTransactionsMock
                .getCarsDtoByDate(anyObject(LocalDate.class), anyObject(LocalDate.class)))
                .andReturn(new CarDto());
        replay(carTransactionsMock);
        mockMvc.perform(
                get("/car/carsbydate?dateBefore=10/10/2015&dateAfter=15/10/2015")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/carsbydate.jsp"));
    }
}