package com.congthanh.authservice.service;

import com.congthanh.authservice.model.entity.RefreshToken;
import com.congthanh.authservice.model.entity.TokenBlacklist;
import com.congthanh.authservice.repository.userRole.UserRoleRepository;
import com.congthanh.authservice.model.request.TokenPayload;
import com.congthanh.authservice.repository.refreshToken.RefreshTokenRepository;
import com.congthanh.authservice.repository.tokenBlacklist.TokenBlacklistRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.security.jwt.token.secretKey}")
    private String secretKey;

    @Value("${spring.security.jwt.token.expiration}")
    private long expiration;

    @Value("${spring.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    private final TokenBlacklistRepository tokenBlacklistRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRoleRepository userRoleRepository;

    private SecretKey getSigningKey() {
        byte[] secretBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (secretBytes.length < 32) {
            throw new IllegalArgumentException("Secret key must be at least 32 bytes long");
        }
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String generateAccessToken(TokenPayload payload) {
        return Jwts.builder()
                .setSubject(payload.accountId())
                .claim("role", payload.role())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(String accountId) {
        return Jwts.builder()
                .setSubject(accountId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token: " + e.getMessage());
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getAccountIdFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    public void blacklistToken(String token) {
        Claims claims = validateToken(token);
        Date expiryDate = claims.getExpiration();
        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryDate(expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        tokenBlacklistRepository.save(blacklistedToken);
    }

    public void cleanExpiredTokens() {
        tokenBlacklistRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }

    public String refreshAccessToken(String refreshToken) {
//        RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new RuntimeException("Not found refresh token: " + refreshToken));
//        if(token.getExpiryDate().isBefore(Instant.now())) {
//            throw new RuntimeException("Refresh token is expired");
//        }

        Claims claims = validateToken(refreshToken);
        String accountId = claims.getSubject();

        List<String> roles = userRoleRepository.getRoleByAccountId(accountId);

        return generateAccessToken(new TokenPayload(accountId, roles));
    }
}
