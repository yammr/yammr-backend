package com.gibgab.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibgab.service.account.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;

    private User user = new User();

    @Test
    public void testRegister() throws Exception {
        user.setEmail("x@y.edu");
        user.setPassword("1234");

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk());
    }
}