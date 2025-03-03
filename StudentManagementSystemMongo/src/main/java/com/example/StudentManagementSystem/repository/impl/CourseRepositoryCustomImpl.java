package com.example.StudentManagementSystem.repository.impl;

import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.repository.CourseRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void deleteCourse(Long id, Long orgId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("organization.id").is(orgId));

        mongoTemplate.remove(query, Course.class);

    }
}
