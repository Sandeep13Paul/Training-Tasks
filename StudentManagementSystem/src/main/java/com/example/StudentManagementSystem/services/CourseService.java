package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Student;

import java.util.List;

public interface CourseService {
    CourseDTO getOne(Long id);
    List<CourseDTO> getAllCourses();
    CourseDTO insertOrUpdate(CourseDTO courseDTO, Long instructorId, Long organizationId);
    boolean deleteCourse(Long id);
    boolean deleteAllCourses();
    boolean deleteByOrg(Long orgId, Long courseId);
}
