package com.devsxplore.thesis.integration;

import com.devsxplore.thesis.accounts.adapter.in.security.OAuthService;
import com.devsxplore.thesis.accounts.adapter.in.security.SecurityConfig;
import com.devsxplore.thesis.accounts.application.port.in.usecase.AssignUserRoleUseCase;
import com.devsxplore.thesis.accounts.application.port.in.usecase.ListAccountsUseCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
@Disabled
class SecurityIntegrationTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	OAuthService oAuthService;

	@MockitoBean
	ListAccountsUseCase listAccountsUseCase;

	@MockitoBean
	AssignUserRoleUseCase assignUserRoleUseCase;

	@Test
	@WithMockUser(roles = "STUDENT")
	@DisplayName("Studenten dürfen nicht auf den Admin-Bereich zugreifen")
	void student_cannot_access_admin_page() throws Exception {
		mvc.perform(get("/admin/accounts")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "SUPERVISOR")
	@DisplayName("Supervisors dürfen nicht auf den Admin-Bereich zugreifen")
	void supervisor_cannot_access_admin_page() throws Exception {
		mvc.perform(get("/admin/accounts")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Unauthentifizierte Nutzer werden zum Login weitergeleitet")
	void unauthenticated_user_is_redirected_to_login() throws Exception {
		mvc.perform(get("/supervisor/profile")).andExpect(status().is3xxRedirection());
	}

}