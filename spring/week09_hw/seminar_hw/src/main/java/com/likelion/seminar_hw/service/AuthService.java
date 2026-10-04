package com.likelion.seminar_hw.service;

import com.likelion.seminar_hw.dto.LoginRequest;
import com.likelion.seminar_hw.dto.RefreshRequest;
import com.likelion.seminar_hw.dto.SignupRequest;
import com.likelion.seminar_hw.dto.TokenResponse;
import com.likelion.seminar_hw.entity.Member;
import com.likelion.seminar_hw.entity.RefreshToken;
import com.likelion.seminar_hw.jwt.JwtTokenProvider;
import com.likelion.seminar_hw.repository.MemberRepository;
import com.likelion.seminar_hw.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "이미 존재하는 이메일입니다."
            );
        }

        Member member = new Member(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name()
        );

        memberRepository.save(member);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        // 1. 이메일과 비밀번호 인증
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        // 2. 같은 회원의 로그인/재발급을 순서대로 처리
        Member member = memberRepository
                .findByEmailForUpdate(authentication.getName())
                .orElseThrow(() ->
                        new BadCredentialsException("인증 실패")
                );

        // 3. 토큰 두 개 발급
        TokenResponse tokens = issueTokens(member.getEmail());

        Instant expiresAt = jwtTokenProvider
                .parseRefreshToken(tokens.refreshToken())
                .getExpiration()
                .toInstant();

        // 4. 기존 Refresh가 있으면 교체, 없으면 새로 저장
        RefreshToken stored = refreshTokenRepository
                .findById(member.getId())
                .orElse(null);

        if (stored == null) {
            refreshTokenRepository.save(
                    new RefreshToken(
                            member.getId(),
                            hash(tokens.refreshToken()),
                            expiresAt
                    )
            );
        } else {
            stored.rotate(hash(tokens.refreshToken()), expiresAt);
        }

        return tokens;
    }

    @Transactional
    public TokenResponse refresh(RefreshRequest request) {
        String oldRefreshToken = request.refreshToken();

        // 1. 서명, 만료시간, REFRESH 타입 검증하기
        Claims claims =
                jwtTokenProvider.parseRefreshToken(oldRefreshToken);

        // 2. 검증된 토큰의 이메일로 회원 조회하기
        Member member = memberRepository
                .findByEmailForUpdate(claims.getSubject())
                .orElseThrow(() ->
                        new BadCredentialsException("인증 실패")
                );

        // 3. 서버에 저장된 현재 Refresh 정보 조회하기
        RefreshToken stored = refreshTokenRepository
                .findById(member.getId())
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "저장된 Refresh 토큰이 없습니다."
                        )
                );

        // 4. DB 만료시간과 현재 토큰 일치 여부 확인하기
        if (!stored.getExpiresAt().isAfter(Instant.now())
                || !stored.getTokenHash().equals(
                hash(oldRefreshToken)
        )) {
            throw new BadCredentialsException(
                    "만료되었거나 사용할 수 없는 Refresh 토큰입니다."
            );
        }

        // 5. 새 Access / Refresh 발급하기
        TokenResponse tokens = issueTokens(member.getEmail());

        Instant newExpiresAt = jwtTokenProvider
                .parseRefreshToken(tokens.refreshToken())
                .getExpiration()
                .toInstant();

        // 6. Rotation: 기존 Refresh 정보를 새 값으로 교체하기
        stored.rotate(
                hash(tokens.refreshToken()),
                newExpiresAt
        );

        return tokens;
    }

    private TokenResponse issueTokens(String email) {
        return new TokenResponse(
                jwtTokenProvider.createAccessToken(email),
                jwtTokenProvider.createRefreshToken(email)
        );
    }

    private String hash(String token) {
        try {
            byte[] digest = MessageDigest
                    .getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256을 사용할 수 없습니다.",
                    e
            );
        }
    }
}