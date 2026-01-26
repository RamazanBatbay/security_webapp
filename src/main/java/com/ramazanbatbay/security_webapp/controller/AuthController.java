package com.ramazanbatbay.security_webapp.controller;

import com.ramazanbatbay.security_webapp.model.AuthRequest;
import com.ramazanbatbay.security_webapp.model.AuthResponse;
import com.ramazanbatbay.security_webapp.model.RefreshToken;
import com.ramazanbatbay.security_webapp.model.TokenRefreshRequest;
import com.ramazanbatbay.security_webapp.service.JwtService;
import com.ramazanbatbay.security_webapp.service.RefreshTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@lombok.extern.slf4j.Slf4j
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                log.info("User logged in successfully: {}", authRequest.getUsername());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
                return new AuthResponse(jwtService.generateToken(authRequest.getUsername()), refreshToken.getToken());
            } else {
                log.warn("Failed login attempt for user: {}", authRequest.getUsername());
                throw new UsernameNotFoundException("Invalid user request !");
            }
        } catch (Exception e) {
            log.warn("Failed login attempt for user: {} Error: {}", authRequest.getUsername(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    // ROTATION LOGIC:
                    // 1. Get the user from the old token
                    String username = refreshToken.getUser().getEmail(); // Assuming email is username

                    // 2. Delete the old refresh token (Invalidate it)
                    refreshTokenService.deleteByToken(requestRefreshToken);

                    // 3. Create a NEW refresh token (Rotate)
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(username);

                    // 4. Generate a NEW access token
                    String accessToken = jwtService.generateToken(username);

                    return new AuthResponse(accessToken, newRefreshToken.getToken());
                })
                .orElseThrow(() -> {
                    log.warn("Failed refresh attempt: Token not found or expired");
                    return new RuntimeException("Refresh token is not in database!");
                });
    }
}
