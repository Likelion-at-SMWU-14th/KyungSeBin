package com.likelion.seminar.member.dto;

import com.likelion.seminar.member.domain.Member;
import org.springframework.data.domain.Page;

import java.util.List;

public record MemberPageResponse(
        List<MemberResponse> content,
        int currentPage,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean hasNext
) {

    public static MemberPageResponse from(Page<Member> page) {
        // TODO 1: Page에 들어 있는 Member를 MemberResponse로 변환하세요.
        // TODO 2: 페이지 정보를 MemberPageResponse에 담아 반환하세요.
        throw new UnsupportedOperationException("TODO");
    }
}