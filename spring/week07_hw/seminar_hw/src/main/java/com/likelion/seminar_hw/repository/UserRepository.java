package com.likelion.seminar_hw.repository;

import com.likelion.seminar_hw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
}
