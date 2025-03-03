package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Organization;

import java.util.List;

public interface OrganizationService {
    OrganizationDTO getOne(Long id);
    List<OrganizationDTO> getAllOrganizations();
    OrganizationDTO insertOrUpdate(OrganizationDTO organizationDTO);
    boolean deleteOrganization(Long id);
    boolean deleteAllOrganizations();
    Long getStudentCountInOrganization(Long orgId);
    Long getStudentCountInCourse(Long orgId);
    List<Object[]> getInstructorDetails(Long orgId);
    Long getInstructorCount(Long orgId);

    List<Object[]> getAllDetailsByCourseId(Long courseId);
    List<Object[]> getStudentDetailsByStatus(CourseStatus courseStatus);
}
