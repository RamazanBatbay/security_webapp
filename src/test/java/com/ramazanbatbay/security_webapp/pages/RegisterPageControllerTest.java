package com.ramazanbatbay.security_webapp.pages;

import com.ramazanbatbay.security_webapp.model.dto.UserRegisterDto;
import com.ramazanbatbay.security_webapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterPageControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private RegisterPageController registerPageController;

    @Test
    void registerUser_WhenEmailAlreadyTaken_ShouldReturnRegisterViewWithErrors() {
        // Arrange
        UserRegisterDto request = new UserRegisterDto();
        request.setUsername("testuser");
        request.setEmail("existing@example.com");
        request.setPassword("password");

        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new RuntimeException("Email already taken"))
                .when(userService).createUser(any(), any(), any());

        // Act
        String viewName = registerPageController.registerUser(request, bindingResult);

        // Assert
        assertEquals("register", viewName);
        verify(userService).createUser(request.getUsername(), request.getEmail(), request.getPassword());
        verify(bindingResult).rejectValue(eq("email"), eq("error.userRegisterDto"), eq("Email already taken"));
    }

    @Test
    void registerUser_WhenSuccessful_ShouldRedirectToLogin() {
        // Arrange
        UserRegisterDto request = new UserRegisterDto();
        request.setUsername("newuser");
        request.setEmail("new@example.com");
        request.setPassword("password");

        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        String viewName = registerPageController.registerUser(request, bindingResult);

        // Assert
        assertEquals("redirect:/login", viewName);
        verify(userService).createUser(request.getUsername(), request.getEmail(), request.getPassword());
    }
}
