package com.gibgab.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.BanEvent;
import com.gibgab.service.database.repository.BanEventRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BanUserController.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BanUserControllerTest {


    @Autowired
    private MockMvc mockMvc;
    private String url = "/moderator/ban_user";

    @MockBean
    private BanEventRepository banEventRepository;

    @MockBean
    private UserRepository userRepository;
    private String bannedEmail = "bad@email.edu";
    private final String bannerEmail = "mod@email.edu";

    private ApplicationUser bannedUser = mock(ApplicationUser.class);

    private ApplicationUser banner = ApplicationUser.builder()
            .id(1)
            .email(bannerEmail)
            .user_status((byte)15).build();

    @Before
    public void setUp(){
        when(userRepository.findByEmail(bannedEmail)).thenReturn(bannedUser);
        when(bannedUser.getId()).thenReturn(0);

        when(userRepository.findByEmail(bannerEmail)).thenReturn(banner);
    }

    @Test
    @WithMockUser(username = bannerEmail, roles = {"MODERATOR", "USER"})
    public void BanUserCreatesBanEvent() throws Exception {
        Timestamp banStart = new Timestamp((new Date()).getTime());
        Timestamp banEnd = new Timestamp((new Date()).getTime());
        BanInfo banInfo = new BanInfo(bannedEmail, banStart.toString(), banEnd.toString());

        mockMvc.perform(post(url)
                    .with(csrf())
                    .content(asJsonString(banInfo))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<BanEvent> banEventMatcher = ArgumentCaptor.forClass(BanEvent.class);
        verify(banEventRepository).save(banEventMatcher.capture());
        verify(bannedUser).setActive(false);

        assertEquals(bannedUser.getId(), banEventMatcher.getValue().getBannedId());
        assertEquals(banner.getId(), banEventMatcher.getValue().getBannerId());
        //assertEquals(banStart, banEventMatcher.getValue().getStartTime());
        //assertEquals(banEnd, banEventMatcher.getValue().getEndTime());
    }

    @Test
    @WithMockUser(username = bannerEmail, roles = {"MODERATOR", "USER"})
    public void banUserSetsUserToBanned() throws Exception {
        Timestamp banStart = new Timestamp((new Date()).getTime());
        Timestamp banEnd = new Timestamp((new Date()).getTime());
        BanInfo banInfo = new BanInfo(bannedEmail, banStart.toString(), banEnd.toString());

        mockMvc.perform(post(url)
                    .with(csrf())
                    .content(asJsonString(banInfo))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userRepository).save(bannedUser);
        verify(bannedUser).setActive(false);
    }

    private String asJsonString(final Object object){
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @AllArgsConstructor
    private class BanInfo {
        String emailToBan;
        String banStart;
        String bannedUntil;
    }
}