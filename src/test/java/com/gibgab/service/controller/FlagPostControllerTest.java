package com.gibgab.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibgab.service.database.entity.ApplicationUser;
import com.gibgab.service.database.entity.PostFlag;
import com.gibgab.service.database.repository.PostFlagRepository;
import com.gibgab.service.database.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = FlagPostController.class)
public class FlagPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostFlagRepository postFlagRepository;
    @MockBean
    private UserRepository userRepository;

    private final int POST_ID = 5;
    private final String FLAGGING_EMAIL = "email@email.edu";
    private final ApplicationUser flaggingUser = ApplicationUser.builder().email(FLAGGING_EMAIL).build();
    private final String url = "/post/flag";

    @Data
    @EqualsAndHashCode
    private static class FlagPostParameters {
        Integer postId;
    }

    @Before
    public void setUp() throws Exception {
        when(userRepository.findByEmail(FLAGGING_EMAIL)).thenReturn(flaggingUser);
    }

    @Test
    @WithMockUser(username = FLAGGING_EMAIL)
    public void flagPostCreatesFlagEntry() throws Exception {
        FlagPostParameters flagPostParameters = new FlagPostParameters();
        flagPostParameters.setPostId(POST_ID);

        mockMvc.perform(post(url)
                    .with(csrf())
                    .content(asJsonString(flagPostParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<PostFlag> expectedPostFlag = ArgumentCaptor.forClass(PostFlag.class);
        verify(postFlagRepository).save(expectedPostFlag.capture());

        assertEquals(flaggingUser.getId(), expectedPostFlag.getValue().getFlag_author());
        assertEquals(Long.valueOf(POST_ID), Long.valueOf(expectedPostFlag.getValue().getPostId()));
        assertTrue(expectedPostFlag.getValue().getIsFlag());
    }

    private String asJsonString(final Object object){
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}