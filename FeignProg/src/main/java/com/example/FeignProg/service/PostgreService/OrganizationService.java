package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.OrganizationDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrganizationService {
    public ResponseEntity<ApiResponse<OrganizationDTO>> savePostOrganization(OrganizationDTO organizationDTO);
    public ResponseEntity<ApiResponse<OrganizationDTO>> getPostOrganization(Long orgId);
    public ResponseEntity<ApiResponse<Boolean>> deletePostOrganization(Long orgId);
    public ResponseEntity<ApiResponse<Long>> getPostOrganizationStud(Long orgId);
    public ResponseEntity<ApiResponse<Long>> getPostOrganizationEnrolledStud(Long courseId);
    public ResponseEntity<ApiResponse<List<Object[]>>> getPostOrganizationInstructor(Long orgId);
    public ResponseEntity<ApiResponse<Long>> getPostOrganizationInstructorCount(Long orgId);
    public ResponseEntity<ApiResponse<List<Object[]>>> getPostOrganizationAll(Long courseId);
    public ResponseEntity<ApiResponse<List<Object[]>>> getPostOrganizationStudentCourseStatus(CourseStatus courseStatus);

}
