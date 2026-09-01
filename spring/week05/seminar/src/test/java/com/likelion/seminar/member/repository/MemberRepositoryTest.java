package com.likelion.seminar.member.repository;

import com.likelion.seminar.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.saveAll(List.of(
                Member.create(
                        "민서",
                        "minseo@example.com",
                        24
                ),
                Member.create(
                        "민지",
                        "minji@example.com",
                        29
                ),
                Member.create(
                        "철수",
                        "chulsoo@example.com",
                        19
                ),
                Member.create(
                        "영희",
                        "younghee@example.com",
                        31
                )
        ));
    }

    @Test
    void 이름과_최소_나이로_회원을_조회한다() {
        // when
        List<Member> result =
                memberRepository
                        .findByUsernameContainingIgnoreCaseAndAgeGreaterThanEqual(
                                "민",
                                20
                        );

        // then
        assertThat(result)
                .extracting(Member::getUsername)
                .containsExactlyInAnyOrder("민서", "민지");
    }

    @Test
    void 활성_회원을_페이징하여_조회한다() {
        // given
        Pageable pageable = PageRequest.of(
                0,
                2,
                Sort.by("createdAt").descending()
        );

        // when
        Page<Member> result =
                memberRepository.findByActiveTrue(pageable);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getNumber()).isZero();
        assertThat(result.getSize()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.hasNext()).isTrue();
    }

    @Test
    void JPQL로_활성_회원을_검색한다() {
        // when
        List<Member> result =
                memberRepository.searchByJpql(
                        "민",
                        20
                );

        // then
        assertThat(result)
                .extracting(Member::getUsername)
                .containsExactly("민지", "민서");

        assertThat(result)
                .extracting(Member::getAge)
                .containsExactly(29, 24);
    }
}