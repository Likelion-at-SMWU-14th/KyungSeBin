package com.likelion.seminar_hw.member.repository;

import com.likelion.seminar_hw.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository
        extends JpaRepository<Member, Long> {

    List<Member>
    findByUsernameContainingIgnoreCaseAndAgeGreaterThanEqual(
            String keyword,
            int minAge
    );

    Page<Member> findByActiveTrue(Pageable pageable);

    @Query("""
            select m
            from Member m
            where m.username like concat('%', :keyword, '%')
              and m.age >= :minAge
              and m.active = true
            order by m.age desc
            """)
    List<Member> searchByJpql(
            @Param("keyword") String keyword,
            @Param("minAge") int minAge
    );
}