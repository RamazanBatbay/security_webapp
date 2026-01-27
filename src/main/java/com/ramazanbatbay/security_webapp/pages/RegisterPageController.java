package com.ramazanbatbay.security_webapp.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterPageController {

    private final com.ramazanbatbay.security_webapp.service.UserService userService;

    public RegisterPageController(com.ramazanbatbay.security_webapp.service.UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(org.springframework.ui.Model model) {
        model.addAttribute("userRegisterDto", new com.ramazanbatbay.security_webapp.model.dto.UserRegisterDto());
        return "register";
    }

    @org.springframework.web.bind.annotation.PostMapping("/register")
    public String registerUser(
            @jakarta.validation.Valid @org.springframework.web.bind.annotation.ModelAttribute("userRegisterDto") com.ramazanbatbay.security_webapp.model.dto.UserRegisterDto request,
            org.springframework.validation.BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.createUser(request.getUsername(), request.getEmail(), request.getPassword());
        } catch (RuntimeException e) {
            bindingResult.rejectValue("email", "error.userRegisterDto", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }
}