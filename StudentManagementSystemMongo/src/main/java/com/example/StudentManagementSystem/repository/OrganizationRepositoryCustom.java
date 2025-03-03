package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Student;

import java.util.List;
import java.util.Map;

public interface OrganizationRepositoryCustom {
    Long getStudentCount(String orgId);
//    List<Long> getStudentCountByCourse(String orgId);
    List<Instructor> getInstructorsByCourse(String courseId);
    long getInstructorCount(String orgId);
    Course getCourseDetailsById(String courseId);
    List<Student> getStudentsByCourseStatus(CourseStatus status);
}
