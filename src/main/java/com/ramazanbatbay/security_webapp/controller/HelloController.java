package com.ramazanbatbay.security_webapp.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class HelloController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
=======
import com.ramazanbatbay.security_webapp.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
>>>>>>> origin/master

    @GetMapping("/hello")
    public String hello() {
        return "Hello, user!";
    }
<<<<<<< HEAD
}
=======

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
>>>>>>> origin/master
