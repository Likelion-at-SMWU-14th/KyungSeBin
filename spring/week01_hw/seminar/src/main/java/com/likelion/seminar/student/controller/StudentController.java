package com.likelion.seminar.student.controller;

import com.likelion.seminar.student.dto.StudentDTO;
import com.likelion.seminar.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/students") // students/{studentId} 자동으로
public class StudentController {

    private final StudentService studentService;

    //학생 등록 - POST /students
    @PostMapping
    public void createStudent(@RequestBody StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
    }

    //전체 학생 목록 조회 - GET /students
    @GetMapping
    public List<StudentDTO> getStudents(){
        return studentService.getStudents();
    }

    //특정 학생 조회 - GET /students/{studentId}
    @GetMapping("/{studentId}")
    public StudentDTO getStudentById(@PathVariable String studentId){
        return studentService.getStudentById(studentId);
    }

    //특정 학생 정보 수정 - PUT /students/{studentId}
    @PutMapping("/{studentId}")
    public void updateStudent(@PathVariable String studentId, @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(studentId, studentDTO);
    }

    //특정 학생 삭제 -DELETE /students/{studentId}
    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable String studentId){
        studentService.deleteStudent(studentId);
    }
}
