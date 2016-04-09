package com.epam.brest.course2015.provider;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.transactions.ProducerTransactions;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * Created by antonsavitsky on 04.01.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:rest-spring-mock-test.xml"})
public class ProducerRestControllerMockTest {
    @Autowired
    private ProducerRestController producerRestController;

    private MockMvc mockMvc;

    @Autowired
    private ProducerTransactions producerTransactionsMock;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(producerRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown() {
        verify(producerTransactionsMock);
        reset(producerTransactionsMock);
    }

    @Test
    public void testAddProducer() throws Exception {
        expect(producerTransactionsMock.addProducer(anyObject(Producer.class))).andReturn(1);
        replay(producerTransactionsMock);
        String producer = new ObjectMapper().writeValueAsString(new Producer("name1","country1"));
        mockMvc.perform(
                post("/producer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(producer))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    public void testUpdateProducer() throws Exception {
        producerTransactionsMock.updateProducer(anyObject(Producer.class));
        expectLastCall().once();
        replay(producerTransactionsMock);
        String producer=new ObjectMapper().writeValueAsString(new Producer(0, "name1", "country1"));
        mockMvc.perform(
                put("/producer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(producer))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProducerById() throws Exception {
        expect(producerTransactionsMock.getProducerById(0)).andReturn(new Producer(0));
        replay(producerTransactionsMock);
        String producer=new ObjectMapper().writeValueAsString(new Producer(0));
        mockMvc.perform(
                get("/producer/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(producer));
    }

    @Test
    public void testDeleteProducer() throws Exception {
        producerTransactionsMock.deleteProducer(0);
        expectLastCall().once();
        replay(producerTransactionsMock);
        mockMvc.perform(
                delete("/producer/0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProducersDto() throws Exception {
        expect(producerTransactionsMock.getProducersDto()).andReturn(new ProducerDto());
        replay(producerTransactionsMock);
        mockMvc.perform(
                get("/producer/dto")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllProducers() throws Exception {
        expect(producerTransactionsMock.getAllProducers()).andReturn(Arrays.<Producer>asList(new Producer(0)));
        replay(producerTransactionsMock);
        String producers="["+new ObjectMapper().writeValueAsString(new Producer(0))+"]";
        mockMvc.perform(
                get("/producer")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(producers));
    }
}
