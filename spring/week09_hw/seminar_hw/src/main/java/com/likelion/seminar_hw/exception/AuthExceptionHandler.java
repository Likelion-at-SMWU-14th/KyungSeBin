package com.likelion.seminar_hw.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({
            JwtException.class,
            AuthenticationException.class
    })
    public ResponseEntity<Map<String, String>> handleUnauthorized(
            Exception e
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "message",
                        "인증 정보가 유효하지 않습니다. 다시 로그인해 주세요."
                ));
    }
}