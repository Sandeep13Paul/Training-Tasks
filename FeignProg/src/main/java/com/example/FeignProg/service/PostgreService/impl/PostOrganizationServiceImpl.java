package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.OrganizationDTO;
import com.example.FeignProg.feign.PostgreFeign.OrganizationInterface;
import com.example.FeignProg.service.PostgreService.OrganizationService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PostOrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationInterface organizationInterface;

    @Override
    public ResponseEntity<ApiResponse<OrganizationDTO>> savePostOrganization(OrganizationDTO organizationDTO) {
        return organizationInterface.saveOrganization(organizationDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<OrganizationDTO>> getPostOrganization(Long orgId) {
        return organizationInterface.getOrgById(orgId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deletePostOrganization(Long orgId) {
        return organizationInterface.deleteOrg(orgId);
    }

    @Override
    public ResponseEntity<ApiResponse<Long>> getPostOrganizationStud(Long orgId) {
        return organizationInterface.getStudCount(orgId);
    }

    @Override
    public ResponseEntity<ApiResponse<Long>> getPostOrganizationEnrolledStud(Long courseId) {
        return organizationInterface.getStudEnrolledCount(courseId);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Object[]>>> getPostOrganizationInstructor(Long orgId) {
        return organizationInterface.getInstructorFromOrg(orgId);
    }

    @Override
    public ResponseEntity<ApiResponse<Long>> getPostOrganizationInstructorCount(Long orgId) {
        return organizationInterface.getCountOfInstructors(orgId);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Object[]>>> getPostOrganizationAll(Long courseId) {
        return organizationInterface.getAll(courseId);
    }

    @Override
    public ResponseEntity<ApiResponse<List<Object[]>>> getPostOrganizationStudentCourseStatus(CourseStatus courseStatus) {
        return organizationInterface.getStudentByCourseStatus(courseStatus);
    }
}
