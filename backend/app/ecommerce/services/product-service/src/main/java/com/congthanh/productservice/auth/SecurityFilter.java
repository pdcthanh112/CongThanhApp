package com.congthanh.productservice.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Value("${spring.security.jwt.token.secretKey}")
    private String JWT_SECRET = "secretKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.extractToken(request);
        if (token != null) {
            try {
                Claims claims = this.validateToken(token);
                List<SimpleGrantedAuthority> authorities = this.extractAuthorities(claims);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    private Claims validateToken(String token) {
        try {
            byte[] secretBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
            if (secretBytes.length < 32) {
                throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes long for HS256");
            }

            SecretKey signingKey = Keys.hmacShaKeyFor(secretBytes);

            return Jwts.parser()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired", e); // Token đã hết hạn
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid token signature", e);  // Chữ ký không hợp lệ
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid token format", e);  // Token không đúng định dạng
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported token", e);  // Token không được hỗ trợ
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid token or secret key", e);   // Token hoặc khóa không hợp lệ
        }
    }

    private List<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        List<String> roles = claims.get("role", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
