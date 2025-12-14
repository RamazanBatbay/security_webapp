package com.ramazanbatbay.security_webapp.controller;

import com.ramazanbatbay.security_webapp.model.dto.UserLoginDto;
import com.ramazanbatbay.security_webapp.model.dto.UserRegisterDto;
import com.ramazanbatbay.security_webapp.model.User;
import com.ramazanbatbay.security_webapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
                this.userService = userService;
        }

        @PostMapping("/register")
        public ResponseEntity<User> register(
                        @RequestHeader(value = "X-Source", required = false, defaultValue = "Web") String source,
                        @Valid @RequestBody UserRegisterDto request) {

                // Log source (demonstration of @RequestHeader)
                System.out.println("Registration request from source: " + source);

                User user = userService.createUser(
                                request.getUsername(),
                                request.getEmail(),
                                request.getPassword());
                return ResponseEntity.status(201).body(user);
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto request) {
                User user = userService.authenticate(
                                request.getEmail(),
                                request.getPassword());

                // Manually set authentication in SecurityContext
                org.springframework.security.core.userdetails.UserDetails userDetails = org.springframework.security.core.userdetails.User
                                .builder()
                                .username(user.getEmail())
                                .password(user.getPassword())
                                .roles(user.getRole())
                                .build();

                org.springframework.security.authentication.UsernamePasswordAuthenticationToken authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                org.springframework.security.core.context.SecurityContextHolder.getContext()
                                .setAuthentication(authentication);

                return ResponseEntity.ok(Map.of("message", "Login successful", "username", user.getUsername()));
        }
}