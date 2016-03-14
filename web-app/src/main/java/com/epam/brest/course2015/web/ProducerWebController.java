package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.dto.ProducerDto;
import com.epam.brest.course2015.test.Loggable;
import com.epam.brest.course2015.transactions.ProducerTransactions;
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
@RequestMapping(value = "/producer")
public class ProducerWebController {

    @Autowired
    private ProducerTransactions producerTransactions;

    @Loggable
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getProducerDto(){
        ProducerDto producerDto= producerTransactions.getProducersDto();
        return new ModelAndView("producers","dto",producerDto);
    }

    @Loggable
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteProducer(@PathVariable(value = "id") Integer producerId){
        producerTransactions.deleteProducer(producerId);
        return "redirect:/producer";
    }

    @Loggable
    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateProducerForm(@PathVariable(value = "id") Integer producerId){
        ModelAndView modelAndView=new ModelAndView("producerform", "producer", producerTransactions.getProducerById(producerId));
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/update/{id}", method=RequestMethod.POST)
    public String saveUpdatedProducer(Producer producer) {
        producerTransactions.updateProducer(producer);
        return "redirect:/producer";
    }

    @Loggable
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView producerAddingForm(){
        ModelAndView modelAndView=new ModelAndView("producerform", "producer", new Producer());
        modelAndView.addObject("producersdto", producerTransactions.getProducersDto());
        return modelAndView;
    }

    @Loggable
    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String addProducer(Producer producer) {
        //adding as new producer so setting id to null
        producer.setProducerId(null);
        producerTransactions.addProducer(producer);
        return "redirect:/producer";
    }

}
