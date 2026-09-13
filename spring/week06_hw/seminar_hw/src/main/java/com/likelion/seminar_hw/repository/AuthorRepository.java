package com.likelion.seminar_hw.repository;

import com.likelion.seminar_hw.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}