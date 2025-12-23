package com.ramazanbatbay.security_webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
<<<<<<< HEAD
=======
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> origin/master
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
<<<<<<< HEAD
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
=======
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
>>>>>>> origin/master
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
<<<<<<< HEAD
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for testing
                .authorizeHttpRequests(auth -> auth
                        // Allow access to static resources and auth pages
                        .requestMatchers("/", "/hello/**", "/login", "/register", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .loginProcessingUrl("/login") // URL to submit the login form to
                        .usernameParameter("email") // Check for "email" parameter instead of "username"
                        .defaultSuccessUrl("/", true) // Redirect to index after successful login
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

=======
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for testing via Postman/Curl easily
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**", "/error", "/", "/hello", "/user").permitAll() // Allow access
                                                                                                        // to our
                                                                                                        // endpoints
                        .anyRequest().authenticated());
>>>>>>> origin/master
        return http.build();
    }
}
