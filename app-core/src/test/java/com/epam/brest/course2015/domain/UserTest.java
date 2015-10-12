package com.epam.brest.course2015.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
/**
 * Created by antonsavitsky on 09.10.15.
 */
public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void testGetLogin() throws Exception {
        user.setLogin("LOGIN");
        assertEquals("LOGIN", user.getLogin());
    }

    @Test
    public void testGetUserId() throws Exception {
        user.setUserId(11);
        assertEquals((Integer)11, user.getUserId());
    }

    @Test
    public void testGetPassword() throws Exception {
        user.setPassword("pas");
        assertEquals("pas",user.getPassword());
    }

    @Test
    public void testGetCreatedDate() throws Exception {
        user.setCreatedDate(new Date(2012,05,06));
        assertEquals(new Date(2012,05,06),user.getCreatedDate());
    }
}