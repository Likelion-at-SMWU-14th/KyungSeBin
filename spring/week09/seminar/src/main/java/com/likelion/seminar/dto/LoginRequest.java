package com.likelion.seminar.dto;

public record LoginRequest(
        String email,
        String password
) {
}