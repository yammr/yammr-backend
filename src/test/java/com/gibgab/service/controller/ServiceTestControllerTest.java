package com.gibgab.service.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers=ServiceTestController.class, secure=false)
public class ServiceTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void callTest() throws Exception {
        mockMvc.perform(get("/test")).andDo(print()).andExpect(status().isOk()).
                andExpect(content().string(containsString("I'm alive")));
    }
}