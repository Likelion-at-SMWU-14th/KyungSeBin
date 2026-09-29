package com.likelion.seminar.service;


import com.likelion.seminar.dto.LoginRequest;
import com.likelion.seminar.dto.SignupRequest;
import com.likelion.seminar.dto.TokenResponse;
import com.likelion.seminar.entity.Member;
import com.likelion.seminar.jwt.JwtTokenProvider;
import com.likelion.seminar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public void signup(SignupRequest request) {

        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member member = new Member(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name()
        );

        memberRepository.save(member);
    }

    public TokenResponse login(LoginRequest request){

        Authentication authentication=
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()

                        )
                );
        String token=
                jwtTokenProvider.createToken(
                        authentication.getName()
                );
        return new TokenResponse(token);
    }
}