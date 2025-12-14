package com.ramazanbatbay.security_webapp.controller;

import com.ramazanbatbay.security_webapp.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, user!";
    }

    @GetMapping("/user")
    public User getUser() {
        // Return a dummy user to demonstrate JSON serialization
        User user = new User();
        user.setId(1);
        user.setUsername("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("hidden");
        return user;
    }
}
