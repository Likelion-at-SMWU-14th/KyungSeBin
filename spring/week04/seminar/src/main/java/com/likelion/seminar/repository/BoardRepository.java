package com.likelion.seminar.repository;

import com.likelion.seminar.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BoardRepository extends JpaRepository<Board, Long>{
}
