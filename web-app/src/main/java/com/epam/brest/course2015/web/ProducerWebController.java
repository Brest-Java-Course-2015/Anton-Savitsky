package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.provider.ProducerServiceConsumer;
import com.epam.brest.course2015.test.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by antonsavitsky on 23.02.16.
 */
@Controller
@CrossOrigin
public class ProducerWebController {
    @Autowired
    private ProducerServiceConsumer producerServiceConsumer;

    @Loggable
    @RequestMapping(value = "/producer", method = RequestMethod.GET)
    public ModelAndView getProducerDto1() {
        ProducerDto producerDto = producerServiceConsumer.getProducersDto();
        return new ModelAndView("producers", "dto", producerDto);
    }

    @Loggable
    @RequestMapping(value = "/admin/producer", method = RequestMethod.GET)
    public ModelAndView getProducerDto(){
        ProducerDto producerDto= producerServiceConsumer.getProducersDto();
        return new ModelAndView("producers","dto",producerDto);
    }

    @Loggable
    @RequestMapping(value = "/admin/producer/delete/{id}", method = RequestMethod.POST)
    public String deleteProducer(@PathVariable(value = "id") Integer producerId){
        producerServiceConsumer.deleteProducer(producerId);
        return "redirect:/admin/producer";
    }

    @Loggable
    @RequestMapping(value = "/admin/producer/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateProducerForm(@PathVariable(value = "id") Integer producerId){
        ModelAndView modelAndView=new ModelAndView("producerform", "producer",
                producerServiceConsumer.getProducerById(producerId));
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/admin/producer/update/{id}", method = RequestMethod.POST)
    public String saveUpdatedProducer(Producer producer) {
        producerServiceConsumer.updateProducer(producer);
        return "redirect:/admin/producer";
    }

    @Loggable
    @RequestMapping(value = "/admin/producer/add", method = RequestMethod.GET)
    public ModelAndView producerAddingForm(){
        ModelAndView modelAndView=new ModelAndView("producerform",
                "producer", new Producer());
        modelAndView.addObject("producersdto");
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/admin/producer/add", method = RequestMethod.POST)
    public String addProducer(Producer producer) {
        //adding as new producer so setting id to null
        producer.setProducerId(null);
        producerServiceConsumer.addProducer(producer);
        return "redirect:/admin/producer";
    }
}
