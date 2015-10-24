package com.epam.brest.course2015.dto;

import com.epam.brest.course2015.domain.User;

import java.util.List;

/**
 * Created by antonsavitsky on 23.10.15.
 */
public class UserDto {

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private List<User> users;

    public int getTotal() {
        return total;
    }

    public List<User> getUsers() {
        return users;
    }


    private int total;
}
