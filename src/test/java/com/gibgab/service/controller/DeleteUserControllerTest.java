package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeleteUserController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private final String username = "user@univ.edu";
    private String url = "/user/delete";

    @Test
    @WithMockUser(username = username)
    public void deleteUserDeletesUser() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser();

        when(userRepository.findByEmail(username)).thenReturn(applicationUser);

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        verify(userRepository).delete(applicationUser);
    }

    @Test
    @WithMockUser(username = username)
    public void alreadyDeletedUserReturns400Exception() throws Exception {
        when(userRepository.findByEmail(username)).thenReturn(null);

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(userRepository, times(0)).delete(any());
    }
}