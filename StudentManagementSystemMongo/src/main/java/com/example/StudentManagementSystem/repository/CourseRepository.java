package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String>, CourseRepositoryCustom {
//    @Query("DELETE FROM course c WHERE c.id = :id AND c.organization.id = :orgId")
//    public void deleteCourse(@Param("id") Long id, @Param("orgId") Long orgId);
}
