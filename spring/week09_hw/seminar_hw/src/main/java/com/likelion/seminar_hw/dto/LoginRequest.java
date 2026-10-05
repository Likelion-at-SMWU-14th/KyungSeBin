package com.likelion.seminar_hw.dto;

public record LoginRequest(
        String email,
        String password
) {
}