package com.devsxplore.thesis.supervisors.adapter.in.web;

import com.devsxplore.thesis.accounts.adapter.in.security.OAuthService;
import com.devsxplore.thesis.accounts.adapter.in.security.SecurityConfig;
import com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorRequestMapper;
import com.devsxplore.thesis.supervisors.adapter.in.web.mapper.SupervisorResponseMapper;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.supervisor.*;
import com.devsxplore.thesis.supervisors.application.port.in.usecase.topic.TopicShowListUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SupervisorController.class)
@Import({SecurityConfig.class, SupervisorRequestMapper.class, SupervisorResponseMapper.class})
class SupervisorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    OAuthService oAuthService;
    @MockitoBean
    SupervisorCreateUseCase supervisorCreateUseCase;
    @MockitoBean
    SupervisorLoadUseCase supervisorLoadUseCase;
    @MockitoBean
    SupervisorUpdateUseCase supervisorUpdateUseCase;
    @MockitoBean
    SupervisorDeleteUseCase supervisorDeleteUseCase;
    @MockitoBean
    FieldAddUseCase fieldAddUseCase;
    @MockitoBean
    LoadByUserIdUseCase loadByUserIdUseCase;
    @MockitoBean
    SupervisorShowAllUseCase supervisorShowAllUseCase;
    @MockitoBean
    TopicShowListUseCase topicShowListUseCase;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Das Registrierungsformular ist für User erreichbar")
    void should_display_register_form() throws Exception {
        mvc.perform(get("/supervisor/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/supervisor-register"));
    }

    @Test
    @WithMockUser(roles = "SUPERVISOR")
    @DisplayName("Profilseite ist für Supervisor erreichbar")
    void should_display_profile_for_supervisor() throws Exception {
        mvc.perform(get("/supervisor/profile"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Registrierung scheitert bei ungültigen Eingaben")
    void should_fail_registration_when_validation_fails() throws Exception {
        mvc.perform(post("/supervisor/register")
                        .with(csrf())
                        .param("academicTitle", "")
                        .param("firstName", "")
                        .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("supervisor/supervisor-register"));
    }
}