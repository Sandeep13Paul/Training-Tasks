package com.example.StudentManagementSystem.services;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;

import java.util.List;

public interface OrganizationService {
    List<OrganizationDTO> getAllOrganizations();

    OrganizationDTO getOrganizationById(String organizationId);

    OrganizationDTO addOrganization(OrganizationDTO organizationDto);

    OrganizationDTO updateOrganization(String organizationId, OrganizationDTO updatedOrganizationDto);

    void deleteOrganization(String organizationId);

    long getStudentCount(String orgId);

//    List<Long> getStudentCountByCourse(String orgId);

    List<InstructorDTO> getInstructorsByCourse(String courseId);

    long getInstructorCount(String orgId);

//    CourseDTO getCourseDetailsById(String courseId);

//    List<StudentDTO> getStudentsByCourseStatus(CourseStatus status);

    public boolean deleteCourse(String id);

    List<StudentDTO> getStudentsByCourseStatus(CourseStatus status);
}
