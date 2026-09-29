package com.likelion.seminar.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/mypage")
    public Map<String,String> mypage(
            Authentication authentication) {
        return Map.of(
                "message", "인증 성공!",
                "user", authentication.getName()
        );
    }
}
