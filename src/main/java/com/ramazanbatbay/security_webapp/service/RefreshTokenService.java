package com.ramazanbatbay.security_webapp.service;

import com.ramazanbatbay.security_webapp.model.RefreshToken;
import com.ramazanbatbay.security_webapp.repository.RefreshTokenRepository;
import com.ramazanbatbay.security_webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByEmail(username).get());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(60 * 60 * 24 * 7)); // 7 days
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(
                    token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    // For Rotation: Delete old token
    public void deleteByToken(String token) {
        Optional<RefreshToken> rToken = refreshTokenRepository.findByToken(token);
        rToken.ifPresent(refreshTokenRepository::delete);
    }
}
