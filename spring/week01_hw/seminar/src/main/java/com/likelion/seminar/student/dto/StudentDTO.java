package com.likelion.seminar.student.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentDTO {

    private String studentId; // string 타입, 학번

    private String name; //string 타입, 이름

    private LocalDate dateOfBirth; //LocalDate 타입, 생일
}
