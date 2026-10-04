package com.likelion.seminar_hw.controller;

import com.likelion.seminar_hw.dto.LoginRequest;
import com.likelion.seminar_hw.dto.RefreshRequest;
import com.likelion.seminar_hw.dto.SignupRequest;
import com.likelion.seminar_hw.dto.TokenResponse;
import com.likelion.seminar_hw.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestBody SignupRequest request
    ) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @Valid @RequestBody RefreshRequest request
    ) {
        return ResponseEntity.ok(
                authService.refresh(request)
        );
    }
}