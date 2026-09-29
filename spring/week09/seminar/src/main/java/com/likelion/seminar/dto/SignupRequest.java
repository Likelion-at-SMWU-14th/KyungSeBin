package com.likelion.seminar.dto;

public record SignupRequest(
        String email,
        String password,
        String name
) {
}