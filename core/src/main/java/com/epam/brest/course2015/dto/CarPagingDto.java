package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.Car;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by antonsavitsky on 05.02.16.
 */
public class CarPagingDto {
    private int total;
    private List<Car> pageList;

    public CarPagingDto(List<Car> pageList, Integer total){
        this.pageList=pageList;
        this.total=total;
    }

    public CarPagingDto(){}

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Car> getPageList() {
        return pageList;
    }

    public void setPageList(List<Car> pageList) {
        this.pageList = pageList;
    }

    @Override
    public String toString() {
        return "CarPagingDto{" +
                "total=" + total +
                ", pageList=" + pageList +
                '}';
    }
}
