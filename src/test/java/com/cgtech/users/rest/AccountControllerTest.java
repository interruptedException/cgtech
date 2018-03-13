/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgtech.users.rest;

import com.cgtech.users.repositories.entities.User;
import com.cgtech.users.services.UserService;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
/**
 *
 * @author Victor
 */

public class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;
    
    @Mock
    private UserService userService;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }
    
    @Test
    public void createUserNonExistingEmail() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@test.com");
        
        Mockito.when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);
        
        mockMvc.perform(post("/accounts").content("{\"name\":\"Test\", \"email\":\"test@test.com\"}").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name",is(user.getName())))
               .andExpect(jsonPath("$.userId", is(user.getId().intValue())))
               .andExpect(jsonPath("$.email",is(user.getEmail())));                
    }

    
    @Test
    public void createUserExistingEmail() throws Exception{
        User oldUser = new User();
        oldUser.setId(1L);
        oldUser.setName("Test");
        oldUser.setEmail("test@test.com");
        
        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("newName");
        newUser.setEmail("test@test.com");
        
        Mockito.when(userService.findUserByEmail(oldUser.getEmail())).thenReturn(Optional.of(oldUser));
        Mockito.when(userService.updateUser(any(User.class))).thenReturn(newUser);
        mockMvc.perform(post("/accounts").content("{\"name\":\"newName\",\"email\":\"test@test.com\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(newUser.getName())))
                .andExpect(jsonPath("$.email",is(newUser.getEmail())))
                .andExpect(jsonPath("$.userId", is(oldUser.getId().intValue())));
    }
    
    @Test
    public void createUserBadEmailFormat() throws Exception{
        mockMvc.perform(post("/accounts").content("{\"name\":\"newName\",\"email\":\"test\"}").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
}
