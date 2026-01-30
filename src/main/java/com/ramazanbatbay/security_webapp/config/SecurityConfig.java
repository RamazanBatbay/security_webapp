package com.ramazanbatbay.security_webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public org.springframework.security.authentication.AuthenticationManager authenticationManager(
                        org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config) {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        com.ramazanbatbay.security_webapp.security.CustomAuthenticationFailureHandler failureHandler,
                        com.ramazanbatbay.security_webapp.security.CustomAuthenticationEntryPoint entryPoint,
                        com.ramazanbatbay.security_webapp.security.CustomAccessDeniedHandler accessDeniedHandler,
                        com.ramazanbatbay.security_webapp.security.LoginRateLimitFilter loginRateLimitFilter) {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/hello/**", "/login", "/register", "/css/**",
                                                                "/js/**", "/images/**",
                                                                "/favicon.ico", "/error")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login")
                                                .usernameParameter("email")
                                                .defaultSuccessUrl("/notes", true)
                                                .failureHandler(failureHandler)
                                                .permitAll())
                                .addFilterBefore(loginRateLimitFilter,
                                                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(entryPoint)
                                                .accessDeniedHandler(accessDeniedHandler))
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout")
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .headers(headers -> headers
                                                .frameOptions(frame -> frame.deny())
                                                .contentSecurityPolicy(csp -> csp.policyDirectives(
                                                                "default-src 'self'; script-src 'self' 'unsafe-inline' https://cdn.tailwindcss.com; style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; font-src https://fonts.gstatic.com"))
                                                .referrerPolicy(referrer -> referrer.policy(
                                                                org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)));

                return http.build();
        }
}
