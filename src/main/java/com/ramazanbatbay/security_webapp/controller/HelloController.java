package com.ramazanbatbay.security_webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class HelloController {
    @GetMapping("/")
    public String home() {
        return "redirect:/notes";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, user!";
    }
}
