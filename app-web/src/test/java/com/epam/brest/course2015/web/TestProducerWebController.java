package com.epam.brest.course2015.web;
import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.service.ProducerService;
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
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by antonsavitsky on 24.02.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-config.xml"})
public class TestProducerWebController {
    @Autowired
    private ProducerWebController producerWebController;

    @Autowired
    private ProducerService producerServiceMock;

    private MockMvc mockMvc;

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
    }

    @After
    public void tearDown(){
        verify(producerServiceMock);
        reset(producerServiceMock);
    }

    @Test
    public void testGetProducerDto() throws Exception {
        expect(producerServiceMock.getProducersDto()).andReturn(new ProducerDto());
        replay(producerServiceMock);
        mockMvc.perform(
                get("/producer")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/producers.jsp"));
    }

    @Test
    public void testDeleteProducer() throws Exception {
        producerServiceMock.deleteProducer(1);
        expectLastCall().once();
        replay(producerServiceMock);
        mockMvc.perform(
                post("/producer/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/producer"));
    }

    @Test
    public void testUpdateProducerForm() throws Exception {
        expect(producerServiceMock.getProducerById(1)).andReturn(new Producer(1));
        replay(producerServiceMock);
        mockMvc.perform(
                get("/producer/update/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/view/producerform.jsp"));
    }

    @Test
    public void testSaveUpdatedProducer() throws Exception {
        producerServiceMock.updateProducer(anyObject(Producer.class));
        expectLastCall().once();
        replay(producerServiceMock);
        mockMvc.perform(
                post("/producer/update/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/producer"));
    }

    @Test
    public void testAddProducer() throws Exception {
        expect(producerServiceMock.addProducer(anyObject(Producer.class))).andReturn(1);
        replay(producerServiceMock);
        mockMvc.perform(
                post("/producer/add")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/producer"));
    }
}