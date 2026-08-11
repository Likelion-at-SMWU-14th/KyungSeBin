package com.likelion.seminar.repository;

import com.likelion.seminar.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post,Long>{
}
