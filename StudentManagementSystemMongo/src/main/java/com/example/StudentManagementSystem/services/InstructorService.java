package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Instructor;

import java.util.List;

public interface InstructorService {
    InstructorDTO getOne(String id);
    List<InstructorDTO> getAllInstructors();
    InstructorDTO save(InstructorDTO instructorDTO);
    InstructorDTO update(InstructorDTO instructorDTO);
    boolean deleteInstructor(String id);
    boolean deleteAllInstructors();
    boolean enrollToCourse(String instructorId, String courseId);
    boolean withdrawFromCourse(String instructorId, String courseId);
    boolean updateCourseStatus(String instructorId, String courseId, String studentId, CourseStatus courseStatus);
}
