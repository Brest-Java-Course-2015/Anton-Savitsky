package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.dto.CarDto;
import com.epam.brest.course2015.service.CarService;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.easymock.EasyMock.*;
import org.springframework.test.web.*;


import javax.annotation.Resource;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
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
    private CarService carServiceMock;

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
        verify(carServiceMock);
        reset(carServiceMock);
    }

    @Test
    public void testGetCarDto() throws Exception {
        expect(carServiceMock.getCarsDto()).andReturn(new CarDto());
        replay(carServiceMock);
        mockMvc.perform(
                get("/car")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/cars.jsp"));
    }

    @Test
    public void testDeleteCar() throws Exception {
        carServiceMock.deleteCar(1);
        expectLastCall().once();
        replay(carServiceMock);
        mockMvc.perform(
                post("/car/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car"));
    }

    @Test
    public void testUpdateCarForm() throws Exception {
        expect(carServiceMock.getCarById(1)).andReturn(new Car(1));
        replay(carServiceMock);
        mockMvc.perform(
               get("/car/update/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/carform.jsp"));
    }

    @Test
    public void testSaveUpdatedCar() throws Exception {
        carServiceMock.updateCar(anyObject(Car.class));
        expectLastCall().once();
        replay(carServiceMock);
        mockMvc.perform(
                post("/car/update/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car"));
    }

    @Test
    public void testAddCar() throws Exception {
        expect(carServiceMock.addCar(anyObject(Car.class))).andReturn(1);
        replay(carServiceMock);
        mockMvc.perform(
                post("/car/add")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car"));
    }

    @Test
    public void testGetCarsByDateDto() throws Exception {
        expect(carServiceMock.getCarsByDateDto(anyObject(LocalDate.class), anyObject(LocalDate.class))).andReturn(new CarDto());
        replay(carServiceMock);
        mockMvc.perform(
                get("/car/carsbydate?dateBefore=10/10/2015&dateAfter=15/10/2015")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/carsbydate.jsp"));
    }
}