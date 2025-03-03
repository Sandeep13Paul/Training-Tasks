package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;

import java.util.List;

public interface StudentService {
    StudentDTO getOne(Long id);
    List<StudentDTO> getAllStudents();
    StudentDTO insertOrUpdate(StudentDTO student, List<Long> courseIds, Long organizationId);
    boolean deleteStudent(Long id);
    boolean deleteAllStudent();
    boolean enrollToCourse(Long studentId, Long courseId);
    boolean withdrawFromCourse(Long studentId, Long courseId);
    CourseStatus getProgress(Long studentId, Long courseId);
}
