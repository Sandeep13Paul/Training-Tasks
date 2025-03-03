package com.example.StudentManagementSystem.repository;


import com.example.StudentManagementSystem.entity.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String>, OrganizationRepositoryCustom {
}
