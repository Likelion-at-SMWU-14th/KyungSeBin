package com.likelion.seminar_hw.member.service;

import com.likelion.seminar_hw.member.domain.Member;
import com.likelion.seminar_hw.member.dto.MemberCreateRequest;
import com.likelion.seminar_hw.member.dto.MemberPageResponse;
import com.likelion.seminar_hw.member.dto.MemberResponse;
import com.likelion.seminar_hw.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        Member member = Member.create(
                request.username(),
                request.email(),
                request.age()
        );

        // TODO: 회원을 저장하는 코드를 한 줄 추가하세요.
        Member savedMember=memberRepository.save(member);

        return MemberResponse.from(savedMember);
    }

    public List<MemberResponse> findAll() {
        // TODO: 모든 회원을 조회하는 코드를 한 줄 추가하세요.
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse findById(Long memberId) {
        Member member = getMember(memberId);

        return MemberResponse.from(member);
    }

    public List<MemberResponse> search(
            String keyword,
            int minAge
    ) {
        return memberRepository
                .findByUsernameContainingIgnoreCaseAndAgeGreaterThanEqual(
                        keyword,
                        minAge)
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberPageResponse findPage(
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Member> memberPage=
                memberRepository.findByActiveTrue(pageable);

        return MemberPageResponse.from(memberPage);
    }

    public List<MemberResponse> searchByJpql(
            String keyword,
            int minAge
    ) {
        return memberRepository.searchByJpql(keyword, minAge)
                .stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional
    public void changeAge(
            Long memberId,
            int age
    ) {
        Member member = getMember(memberId);

        // TODO: 회원의 나이를 변경하는 코드를 한 줄 추가하세요.
        member.changeAge(age);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "회원을 찾을 수 없습니다. id=" + memberId
                        )
                );
    }

    private List<MemberResponse> toResponses(
            List<Member> members
    ) {
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }
}