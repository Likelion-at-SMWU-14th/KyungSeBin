package com.likelion.seminar_hw.repository;

import com.likelion.seminar_hw.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {
}