package com.gibgab.service.controller;

import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.VerificationToken;
import com.gibgab.service.database.repository.UserRepository;
import com.gibgab.service.database.repository.VerificationTokenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = VerificationController.class, secure = false)
public class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;

    @MockBean
    private UserRepository userRepository;

    private String url = "/verify";

    @Test
    public void validVerificationTokenVerifiesUser() throws Exception {
        String key = "Verification token";
        VerificationToken verificationToken = new VerificationToken();
        ApplicationUser applicationUser = new ApplicationUser();

        when(verificationTokenRepository.findByVerificationToken(key)).thenReturn(verificationToken);
        when(userRepository.findById(verificationToken.getId())).thenReturn(Optional.of(applicationUser));

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .param("key", key)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("email-verified"));

        verify(verificationTokenRepository).delete(verificationToken);
        verify(userRepository).save(applicationUser);
        assertTrue(applicationUser.isVerified());
    }
}