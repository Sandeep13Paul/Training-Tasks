package com.example.StudentManagementSystem.repository;

import org.springframework.data.repository.query.Param;

public interface CourseRepositoryCustom {
    public void deleteCourse(Long id, Long orgId);
}
