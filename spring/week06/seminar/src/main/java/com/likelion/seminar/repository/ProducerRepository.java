package com.likelion.seminar.repository;

import com.likelion.seminar.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
