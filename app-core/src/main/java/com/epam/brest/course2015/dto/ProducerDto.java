package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.Car;
import com.epam.brest.course2015.domain.Producer;

import java.util.List;

/**
 * Created by antonsavitsky on 02.12.15.
 */
public class ProducerDto {
    private List<Producer> producers;
    private Integer total;
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<Producer> getProducers() {
        return producers;
    }
    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }
}
