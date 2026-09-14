package com.likelion.seminar_hw.repository;

import com.likelion.seminar_hw.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}