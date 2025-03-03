package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Student;

import java.util.List;

public interface CourseService {
    CourseDTO getOne(String id);
    List<CourseDTO> getAllCourses();
    CourseDTO save(CourseDTO courseDTO, String instructorId);
    CourseDTO update(CourseDTO courseDTO, String instructorId);
    boolean deleteCourse(String id);
//    boolean deleteByOrg(String orgId, String courseId);
}
