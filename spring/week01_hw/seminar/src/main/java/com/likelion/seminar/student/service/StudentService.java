package com.likelion.seminar.student.service;

import com.likelion.seminar.student.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<StudentDTO> studentDTOList=new ArrayList<>();

    // 학생 등록하기
    public void createStudent(StudentDTO studentDTO) {
        studentDTOList.add(studentDTO);
    }

    // 전체 학생 목록 조회
    public List<StudentDTO> getStudents(){
        return studentDTOList;
    }

    //studentId를 이용 -> 특정 학생 조회
    public StudentDTO getStudentById(String studentId){
        for (StudentDTO student:studentDTOList){ // 비교
            if (student.getStudentId().equals(studentId)){
                return student;
            }
        }
        return null;
    }

    //특정 학생 정보 수정
    public void updateStudent(String studentId, StudentDTO studentDTO) {
        StudentDTO targetStudent = getStudentById(studentId); //getStudentById -> 특정학생 찾기
        if (targetStudent != null) {
            if (studentDTO.getName() != null) { //이름이 null 이 아닐때, 부분 수정 허용
                targetStudent.setName(studentDTO.getName());
            }
            if (studentDTO.getDateOfBirth() != null) { // 생일이 null 이 아닐때, 부분 수정 허용
                targetStudent.setDateOfBirth(studentDTO.getDateOfBirth());
            }
        }
    }

    //특정 학생 삭제하기
    public void deleteStudent(String studentId){
        studentDTOList.removeIf(student->student.getStudentId().equals(studentId));
    }
}