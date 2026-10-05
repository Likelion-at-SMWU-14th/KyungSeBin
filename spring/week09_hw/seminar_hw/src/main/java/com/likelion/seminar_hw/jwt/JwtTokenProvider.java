package com.likelion.seminar_hw.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration}") long accessExpiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String createAccessToken(String email) {
        return createToken(email, "ACCESS", accessExpiration);
    }

    public String createRefreshToken(String email) {
        return createToken(email, "REFRESH", refreshExpiration);
    }

    private String createToken(
            String email,
            String tokenType,
            long expiration
    ) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(email)
                .claim("token_type", tokenType)
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expiration)))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String getAccessEmail(String token) {
        return parseClaims(token, "ACCESS").getSubject();
    }

    public Claims parseRefreshToken(String token) {
        return parseClaims(token, "REFRESH");
    }

    private Claims parseClaims(String token, String expectedType) {
        if (token == null || token.isBlank()) {
            throw new JwtException("토큰이 없습니다.");
        }

        // 서명과 만료시간 검증
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Access / Refresh 용도 검증
        if (!expectedType.equals(
                claims.get("token_type", String.class)
        )) {
            throw new JwtException("토큰 종류가 올바르지 않습니다.");
        }

        if (claims.getSubject() == null
                || claims.getSubject().isBlank()
                || claims.getExpiration() == null) {
            throw new JwtException("필수 토큰 정보가 없습니다.");
        }

        return claims;
    }
}