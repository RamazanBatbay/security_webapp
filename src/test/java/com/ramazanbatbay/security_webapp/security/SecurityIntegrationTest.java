package com.ramazanbatbay.security_webapp.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SecurityIntegrationTest {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders
                                .webAppContextSetup(context)
                                .apply(springSecurity())
                                .build();
        }

        @Test
        void publicEndpoints_Reachability() throws Exception {
                mockMvc.perform(get("/"))
                                .andExpect(result -> assertTrue(
                                                result.getResponse().getStatus() == 200
                                                                || result.getResponse().getStatus() == 302));

                mockMvc.perform(get("/login"))
                                .andExpect(result -> assertTrue(
                                                result.getResponse().getStatus() == 200
                                                                || result.getResponse().getStatus() == 302));

                mockMvc.perform(get("/register"))
                                .andExpect(result -> assertTrue(
                                                result.getResponse().getStatus() == 200
                                                                || result.getResponse().getStatus() == 302));
        }

        @Test
        void securedEndpoints_Unauthenticated_RedirectsToLogin() throws Exception {
                mockMvc.perform(get("/notes"))
                                .andExpect(status().is3xxRedirection());
        }

        @Test
        void adminEndpoints_UserRole_AccessDenied() throws Exception {
                // CustomAccessDeniedHandler redirects to /error?code=403
                mockMvc.perform(get("/admin/dashboard")
                                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                                                .user("user").roles("USER")))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/error?code=403"));
        }

        @Test
        void adminEndpoints_AdminRole_AccessAllowed() throws Exception {
                // AdminController exists, so should be 200 OK
                mockMvc.perform(get("/admin/dashboard")
                                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
                                                .user("admin").roles("ADMIN")))
                                .andExpect(status().isOk());
        }

        @Test
        void login_Success() throws Exception {
                mockMvc.perform(post("/login")
                                .param("email", "test@example.com")
                                .param("password", "password")
                                .with(csrf()))
                                .andExpect(status().is3xxRedirection());
        }

        @Test
        void csrf_Protection_MissingToken_Forbidden() throws Exception {
                // CustomAccessDeniedHandler redirects to /error?code=403
                mockMvc.perform(post("/login")
                                .param("email", "user")
                                .param("password", "pass"))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/error?code=403"));
        }
}
