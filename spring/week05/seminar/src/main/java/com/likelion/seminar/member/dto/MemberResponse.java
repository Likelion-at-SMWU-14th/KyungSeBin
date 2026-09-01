package com.likelion.seminar.member.dto;


import com.likelion.seminar.member.domain.Member;

import java.time.LocalDateTime;

public record MemberResponse(
        Long id,
        String username,
        String email,
        int age,
        boolean active,
        LocalDateTime createdAt
) {

    public static MemberResponse from(Member member) {
        // TODO: Member 엔티티를 MemberResponse로 변환하세요.
        throw new UnsupportedOperationException("TODO");
    }
}