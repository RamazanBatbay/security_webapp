package com.ramazanbatbay.security_webapp.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RateLimitIntegrationTest {

    @Autowired
    private org.springframework.web.context.WebApplicationContext context;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders
                .webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Autowired
    private com.ramazanbatbay.security_webapp.service.RateLimiterService rateLimiterService;

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        rateLimiterService.cleanup();
    }

    @Test
    void testRateLimit_Login_Exceeded() throws Exception {
        // Limit is 5 requests per minute.
        // Send 5 allowed requests
        for (int i = 0; i < 100; i++) {
            mockMvc.perform(post("/login")
                    .param("email", "test@example.com")
                    .param("password", "password")
                    .with(csrf()))
                    .andExpect(status().isFound());
        }

        // Send 6th request - should be blocked
        mockMvc.perform(post("/login")
                .param("email", "test@example.com")
                .param("password", "password")
                .with(csrf()))
                .andExpect(status().isTooManyRequests());
    }
}
