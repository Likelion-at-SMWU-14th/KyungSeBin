package com.likelion.seminar_hw.member.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// TODO 1: 엔티티 등록
@Entity
@Table(name="members")
// TODO 2: 테이블 이름 설정
public class Member {

    // TODO 3: 기본키 설정
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    // TODO 4: 기본키 자동 생성 설정
    private Long id;

    private String username;
    private String email;
    private int age;
    private boolean active;
    private LocalDateTime createdAt;

    protected Member() {
    }

    private Member(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public static Member create(
            String username,
            String email,
            int age
    ) {
        return new Member(username, email, age);
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}