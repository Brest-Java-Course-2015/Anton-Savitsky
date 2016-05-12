package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.annotation.Resource;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by antonsavitsky on 21.02.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-test-config.xml"})
@WebAppConfiguration
public class TestCarWebController {

    @Value("${restServicesPrefix}")
    private String restServicesPrefix;

    @Resource
    private CarWebController carWebController;

    @Autowired
    private RestTemplate restTemplate;

    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

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

        mockServer= MockRestServiceServer.createServer(restTemplate);
    }

    @After
    public void tearDown(){

    }

    @Test
    public void testGetCarDto() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix + "/car/dto"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                                "{\"cars\":null,\"total\":null}",
                                MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/admin/car")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/cars.jsp"));
    }

    @Test
    public void testDeleteCar() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix + "/car/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess(
                        "redirect:/admin/car",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/admin/car/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/car"));
    }


    @Test
    public void testUpdateCarForm() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix + "/car/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"carId\":null,\"carName\":null," +
                        "\"dateOfCreation\":null}", MediaType.APPLICATION_JSON));

        mockServer.expect(requestTo(restServicesPrefix + "/producer/dto"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"producers\":null,\"total\":null}",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/admin/car/update/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/carform.jsp"));
    }

    @Test
    public void testSaveUpdatedCar() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix + "/car"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess("redirect:/admin/car",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/admin/car/update/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/car"));
    }


    @Test
    public void testAddCar() throws Exception {
        String car = new ObjectMapper()
                .writeValueAsString(new Car());

        mockServer.expect(requestTo(restServicesPrefix + "/car"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("3",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/admin/car/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(car))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/car"));
    }

    @Test
    public void testGetCarsByDateDto() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix+
                "/car/dto/date?dateBefore=10/10/2015&dateAfter=15/10/2015"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        mockMvc.perform(
                get("/admin/car/carsbydate?dateBefore=10/10/2015&dateAfter=15/10/2015")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/cars.jsp"));
    }

}