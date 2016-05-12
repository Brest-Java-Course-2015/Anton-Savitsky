package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Producer;
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
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by antonsavitsky on 24.02.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-test-config.xml"})
public class TestProducerWebController {
    @Value("${restServicesPrefix}")
    private String restServicesPrefix;

    @Resource
    private ProducerWebController producerWebController;

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

        mockMvc = standaloneSetup(producerWebController)
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter())
                .setViewResolvers(viewResolver).build();

        mockServer= MockRestServiceServer.createServer(restTemplate);
    }


    @After
    public void tearDown(){


    }


    @Test
    public void testGetProducerDto() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix+"/producer/dto"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "{\"producers\":null,\"total\":null}",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/producer")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/producers.jsp"));
    }


    @Test
    public void testDeleteProducer() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix+"/producer/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess(
                        "redirect:/admin/producer",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/admin/producer/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/producer"));
    }


    @Test
    public void testUpdateProducerForm() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix+"/producer/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"producerId\":null,\"producerName\":null," +
                        "\"country\":null}", MediaType.APPLICATION_JSON));

        mockServer.expect(requestTo(restServicesPrefix+"/producer/dto"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"producers\":null,\"total\":null}",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                get("/admin/producer/update/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/producerform.jsp"));
    }


    @Test
    public void testSaveUpdatedProducer() throws Exception {
        mockServer.expect(requestTo(restServicesPrefix+"/producer"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess("redirect:/admin/producer",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/admin/producer/update/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/producer"));
    }

    @Test
    public void testAddProducer() throws Exception {
        String producer = new ObjectMapper()
                .writeValueAsString(new Producer());

        mockServer.expect(requestTo(restServicesPrefix+"/producer"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("3",
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/admin/producer/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producer))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/producer"));
    }
}
