package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.services.OrganizationService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<OrganizationDTO>> saveOrganization(@RequestBody OrganizationDTO organizationDTO) {
        organizationDTO = organizationService.insertOrUpdate(organizationDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Organization Created Successfully", organizationDTO));
    }

    @GetMapping("/getOrg")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrgById(@RequestParam Long id) {
        OrganizationDTO organizationDTO = organizationService.getOne(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Organization Found", organizationDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteOrg(@RequestParam Long id) {
        Boolean flag = organizationService.deleteOrganization(id);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Organization Successfully Deleted", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Organization Not Deleted", "error"));
    }

    @GetMapping("/getStudentCount")
    public ResponseEntity<ApiResponse<Long>> getStudCount(@RequestParam Long orgId) {
        Long count = organizationService.getStudentCountInOrganization(orgId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Count Of Students Fetched", count));
    }

    @GetMapping("/getStudentEnrolledCount")
    public ResponseEntity<ApiResponse<Long>> getStudEnrolledCount(@RequestParam Long courseId) {
        Long count = organizationService.getStudentCountInCourse(courseId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Count Of Students Fetched", count));
    }

    @GetMapping("/getInstructor")
    public ResponseEntity<ApiResponse<List<Object[]>>> getInstructorFromOrg(@RequestParam Long orgId) {
        List<Object[]> instructors = organizationService.getInstructorDetails(orgId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructors Fetched", instructors));
    }

    @GetMapping("/getCountOfInstructors")
    public ResponseEntity<ApiResponse<Long>> getCountOfInstructors(@RequestParam Long orgId) {
        Long count = organizationService.getInstructorCount(orgId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Count Of Students Fetched", count));
    }

    @GetMapping("/getAllDetails")
    public ResponseEntity<ApiResponse<List<Object[]>>> getAll(@RequestParam Long courseId) {
        List<Object[]> allDetails = organizationService.getAllDetailsByCourseId(courseId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched All Details By Course Id", allDetails));
    }

    @GetMapping("/getStudentDetails")
    public ResponseEntity<ApiResponse<List<Object[]>>> getStudentByCourseStatus(@RequestParam CourseStatus courseStatus) {
        List<Object[]> studentDetails = organizationService.getStudentDetailsByStatus(courseStatus);
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched Student By Course Status", studentDetails));
    }
}
