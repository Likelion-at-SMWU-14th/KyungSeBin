package com.likelion.seminar_hw.repository;

import com.likelion.seminar_hw.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByBoard_Id(Long boardId);
    //Board 삭제 전에 Board에 Post가 있는 지 검사하는 과정 수행
}
