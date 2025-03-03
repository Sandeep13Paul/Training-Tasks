package com.example.StudentManagementSystem.repository.impl;

import com.example.StudentManagementSystem.entity.*;
import com.example.StudentManagementSystem.repository.OrganizationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class OrganizationRepositoryCustomImpl implements OrganizationRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Long getStudentCount(String orgId) {
        return mongoTemplate.count(
                Query.query(Criteria.where("organizationId").is(orgId)),
                Student.class
        );
    }

    @Override
    public List<Instructor> getInstructorsByCourse(String courseId) {
        return mongoTemplate.find(
                Query.query(Criteria.where("courseId").is(courseId)),
                Instructor.class
        );
    }

    @Override
    public long getInstructorCount(String orgId) {
        return mongoTemplate.count(
                Query.query(Criteria.where("organizationId").is(orgId)),
                Instructor.class
        );
    }

    @Override
    public Course getCourseDetailsById(String courseId) {
        Course course = mongoTemplate.findById(courseId, Course.class);
        if (course == null) {
            return null;
        }

        Instructor instructor = null;
        if (course.getInstructorId() != null) {
            instructor = mongoTemplate.findById(
                    course.getInstructorId(),
                    Instructor.class);
        }

        List<Student> students = mongoTemplate.find(
                Query.query(Criteria.where("enrolledCourses.courseId").is(courseId)),
                Student.class
        );


        return course;
    }

    @Override
    public List<Student> getStudentsByCourseStatus(CourseStatus status) {


        return mongoTemplate.find(
                Query.query(Criteria.where("enrolledCourses.status").is(status)),
                Student.class
        );

    }
}
