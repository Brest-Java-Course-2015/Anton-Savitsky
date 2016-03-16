package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.Producer;
import com.epam.brest.course2015.test.Loggable;

import java.util.List;

/**
 * Created by antonsavitsky on 02.12.15.
 */
public class ProducerDto {
    private List<Producer> producers;
    private Integer total;

    @Loggable
    public Integer getTotal() {
        return total;
    }
    @Loggable
    public void setTotal(Integer total) {
        this.total = total;
    }
    @Loggable
    public List<Producer> getProducers() {
        return producers;
    }
    @Loggable
    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    @Override
    public String toString() {
        return "ProducerDto{" +
                "producers=" + producers +
                ", total=" + total +
                '}';
    }
}
