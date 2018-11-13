package com.gibgab.service.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AuthenticationTestController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthenticationTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String username = "username";

    @Test
    @WithMockUser(username=username)
    public void getUsernameReturnsUsername() throws Exception {
        // When I call the api /secret/getmyusername
        // then I receive my username
        mockMvc.perform(get("/secret/getmyusername"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(username));
    }
}