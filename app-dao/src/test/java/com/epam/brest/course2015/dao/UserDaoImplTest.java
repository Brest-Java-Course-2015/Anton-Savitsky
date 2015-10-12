package com.epam.brest.course2015.dao;

import com.epam.brest.course2015.domain.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by antonsavitsky on 09.10.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @org.junit.Test
    public void testGetAllUsers() throws Exception {
        List<User> users = userDao.getAllUsers();
        assertTrue(users.size() == 2);
    }

    @org.junit.Test
    public void testGetUser() throws Exception {
        User user = userDao.getUserById(1);
        assertNotNull(user);
    }
}