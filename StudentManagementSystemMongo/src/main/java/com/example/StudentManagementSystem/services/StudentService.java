package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Student;

import java.util.List;

public interface StudentService {
    StudentDTO getOne(String id);
    List<StudentDTO> getAllStudents();
    StudentDTO insert(StudentDTO studentDTO);
    StudentDTO update(String studentId, StudentDTO studentDTO);
    boolean deleteStudent(String id);
    boolean deleteAllStudent();
    boolean enrollToCourse(String studentId, String courseId);
    boolean withdrawFromCourse(String studentId, String courseId);
    CourseStatus getProgress(String studentId, String courseId);
}
