package com.ramazanbatbay.security_webapp.service;

import com.ramazanbatbay.security_webapp.model.User;
import com.ramazanbatbay.security_webapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "test@example.com", "encodedPassword", "USER");
    }

    @Test
    void createUser_Success() {
        // Arrange
        String rawPassword = "password123";
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User createdUser = userService.createUser("testuser", "test@example.com", rawPassword);

        // Assert
        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_EmailTaken() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser("newuser", "test@example.com", "password123");
        });

        assertEquals("Email already taken", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void authenticate_Success() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // Act
        User authenticatedUser = userService.authenticate("test@example.com", "password123");

        // Assert
        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmail());
    }

    @Test
    void authenticate_Failure_UserNotFound() {
        // Arrange
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.authenticate("unknown@example.com", "password123");
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void authenticate_Failure_WrongPassword() {
        // Arrange
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.authenticate("test@example.com", "wrongpassword");
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }
}
