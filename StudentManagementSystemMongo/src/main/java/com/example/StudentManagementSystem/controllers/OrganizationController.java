package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.services.OrganizationService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationDTO>>> getAllOrganizations() {
        List<OrganizationDTO> dtos = organizationService.getAllOrganizations();
        ApiResponse<List<OrganizationDTO>> response = new ApiResponse("success", "Organizations fetched successfully", dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationById(@RequestParam String id) {
        OrganizationDTO dto = organizationService.getOrganizationById(id);
        ApiResponse<OrganizationDTO> response = new ApiResponse( "success", "Organization fetched successfully", dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationDTO>> addOrganization(@RequestBody OrganizationDTO organizationDto) {
        OrganizationDTO createdOrg = organizationService.addOrganization(organizationDto);
        ApiResponse<OrganizationDTO> response = new ApiResponse("succees", "Organization added successfully", createdOrg);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<OrganizationDTO>> updateOrganization(@RequestParam String id,
                                                                              @RequestBody OrganizationDTO
                                                                                      organizationDto) {
        OrganizationDTO updatedOrg = organizationService.updateOrganization(id, organizationDto);
        ApiResponse<OrganizationDTO> response = new ApiResponse("success", "Organization updated successfully", updatedOrg);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteOrganization(@RequestParam String id) {
        organizationService.deleteOrganization(id);
        ApiResponse<Void> response = new ApiResponse("success", "Organization deleted successfully", null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student-count")
    public ResponseEntity<ApiResponse<Long>> getStudentCount(@RequestParam String orgId) {
        Long count = organizationService.getStudentCount(orgId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Student count fetched successfully", count));
    }

//    @GetMapping("/course-enrollment")
//    public ResponseEntity<ApiResponse<List<Long>>> getStudentCountByCourse(
//            @RequestParam String orgId) {
//        List<Long> counts = organizationService.getStudentCountByCourse(orgId);
//        return ResponseEntity.ok(new ApiResponse<>("success", "Course enrollment counts fetched successfully", counts));
//    }

    @GetMapping("/courses/instructors")
    public ResponseEntity<ApiResponse<List<InstructorDTO>>> getCourseInstructors(
            @RequestParam String courseId) {
        List<InstructorDTO> dtos = organizationService.getInstructorsByCourse(courseId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course instructors fetched successfully", dtos));
    }

    @GetMapping("/instructor-count")
    public ResponseEntity<ApiResponse<Long>> getInstructorCount(@RequestParam String orgId) {
        long count = organizationService.getInstructorCount(orgId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor count fetched successfully", count));
    }

//    @GetMapping("/courses/{courseId}/details")
//    public ResponseEntity<ApiResponse<CourseDTO>> getCourseDetails(
//            @PathVariable String courseId) {
//        CourseDTO dto = organizationService.getCourseDetailsById(courseId);
//        return ResponseEntity.ok(new ApiResponse<>("success", "Course details fetched successfully", dto));
//    }

    @GetMapping("/students/by-status")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudentsByCourseStatus(
            @RequestParam CourseStatus status) {
        List<StudentDTO> dtos = organizationService.getStudentsByCourseStatus(status);
        return ResponseEntity.ok(new ApiResponse<>("success", "Students fetched successfully", dtos));
    }
}
