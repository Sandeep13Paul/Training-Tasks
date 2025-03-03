package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Instructor;

import java.util.List;

public interface InstructorService {
    InstructorDTO getOne(Long id);
    List<InstructorDTO> getAllInstructors();
    InstructorDTO insertOrUpdate(InstructorDTO instructorDTO, Long organizationId);
    boolean deleteInstructor(Long id);
    boolean deleteAllInstructors();
    boolean updateCourseStatus(Long instructorId, Long courseId, Long studentId, CourseStatus courseStatus);
}
