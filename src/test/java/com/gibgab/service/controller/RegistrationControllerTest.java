package com.gibgab.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.repository.UserRepository;
import com.gibgab.service.database.repository.VerificationTokenRepository;
import com.gibgab.service.security.verification.VerificationEmailSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = RegistrationController.class, secure = false)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;

    @MockBean
    private VerificationEmailSender verificationEmailSender;

    private String url = "/register";

    @AllArgsConstructor
    @Data
    private static class UserInfo {
        String email;
        String password;
    }

    @Test
    public void registrationWithValidEmailCreatesNewUser() throws Exception {
        String validEmail = "a@b.edu";
        String password = "a";
        UserInfo user = new UserInfo(validEmail, password);

        when(userRepository.findByEmail(validEmail)).thenReturn(null);
        when(userRepository.save(any())).then(returnsFirstArg());
        when(verificationTokenRepository.save(any())).then(returnsFirstArg());

        doRequest(user, status().isOk(), content().string(containsString("Created")));
    }

    @Test
    public void registrationWithInvalidEmailDoesNotCreateNewUser() throws Exception {
        String invalidEmail = "@.edu";
        String password = "a";
        UserInfo user = new UserInfo(invalidEmail, password);

        when(userRepository.findByEmail(invalidEmail)).thenReturn(null);
        when(userRepository.save(any())).then(returnsFirstArg());
        when(verificationTokenRepository.save(any())).then(returnsFirstArg());

        doRequest(user, status().isOk(), content().string(containsString("Bad")));
    }

    @Test
    public void registrationWithExistingEmailDoesNotCreateNewUser() throws Exception {
        String validEmail = "a@b.edu";
        String password = "a";
        UserInfo user = new UserInfo(validEmail, password);
        ApplicationUser existingUser = mock(ApplicationUser.class);

        when(userRepository.findByEmail(validEmail)).thenReturn(existingUser);
        when(userRepository.save(any())).then(returnsFirstArg());
        when(verificationTokenRepository.save(any())).then(returnsFirstArg());

        doRequest(user, status().isOk(), content().string(containsString("Bad")));
    }

    private void doRequest(UserInfo user, ResultMatcher status, ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(getJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status)
                .andExpect(resultMatcher);
    }

    private String getJsonString(final Object object){
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}