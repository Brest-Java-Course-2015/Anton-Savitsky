package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.User;
import com.epam.brest.course2015.dto.UserDto;
import com.epam.brest.course2015.rest.UserRestController;
        import com.epam.brest.course2015.rest.VersionController;
        import com.epam.brest.course2015.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.EasyMock;
        import org.easymock.internal.matchers.Null;
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

        import javax.annotation.Resource;

        import java.util.Arrays;

        import static org.easymock.EasyMock.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
        import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by kiryl on 23/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-rest-mock.xml"})
public class UserControllerMockTest {

    @Resource
    private UserRestController userRestController;

    private MockMvc mockMvc;

    @Autowired
    private UserService userService; //mock

    @Before
    public void setUp(){

        mockMvc= standaloneSetup(userRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void tearDown(){
        verify(userService);
        reset(userService);
    }

    @Test
    public void addUserTest() throws Exception{
        expect(userService.addUser(anyObject(User.class))).andReturn(3);
        replay(userService);

        String user= new ObjectMapper().writeValueAsString(new User("login2","password2"));
        mockMvc.perform(post("/user")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(user))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    public void getUsersTest() throws Exception {
        expect(userService.getAllUsers()).andReturn(Arrays.<User>asList(new User("log","p")));
        replay(userService);

        mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void updateUserTest() throws Exception{
        userService.updateUser(anyObject(User.class));
        expectLastCall();
        replay(userService);

        mockMvc.perform(put("/user/5/password5")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().string(""));
    }

    @Test
    public void getUsersDto() throws Exception {
        expect(userService.getUserDto()).andReturn(new UserDto());
        replay(userService);

        mockMvc.perform(get("/userdto")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }
}