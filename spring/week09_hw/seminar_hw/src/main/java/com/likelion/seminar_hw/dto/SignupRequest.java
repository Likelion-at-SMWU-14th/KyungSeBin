package com.likelion.seminar_hw.dto;

public record SignupRequest(
        String email,
        String password,
        String name
) {
}