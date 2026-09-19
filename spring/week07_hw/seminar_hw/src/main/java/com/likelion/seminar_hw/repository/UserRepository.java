package com.likelion.seminar_hw.repository;

import com.likelion.seminar_hw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    //사용자 생성 시 사용함
    boolean existsByEmail(String email);

    //사용자 수정 시 사용함 : 본인을 제외하고 검사하도록 함
    boolean existsByEmailAndIdNot(String email, Long id);
}
