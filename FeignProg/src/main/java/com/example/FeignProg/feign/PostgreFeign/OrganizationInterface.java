package com.example.FeignProg.feign.PostgreFeign;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.OrganizationDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Postgre-Organization", url = "http://localhost:8085/api/organization")
public interface OrganizationInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<OrganizationDTO>> saveOrganization(@RequestBody OrganizationDTO organizationDTO);

    @GetMapping("/getOrg")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrgById(@RequestParam Long id);

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteOrg(@RequestParam Long id);

    @GetMapping("/getStudentCount")
    public ResponseEntity<ApiResponse<Long>> getStudCount(@RequestParam Long id);

    @GetMapping("/getStudentEnrolledCount")
    public ResponseEntity<ApiResponse<Long>> getStudEnrolledCount(@RequestParam Long courseId);

    @GetMapping("/getInstructor")
    public ResponseEntity<ApiResponse<List<Object[]>>> getInstructorFromOrg(@RequestParam Long orgId);

    @GetMapping("/getCountOfInstructors")
    public ResponseEntity<ApiResponse<Long>> getCountOfInstructors(@RequestParam Long orgId);

    @GetMapping("/getAllDetails")
    public ResponseEntity<ApiResponse<List<Object[]>>> getAll(@RequestParam Long courseId);

    @GetMapping("/getStudentDetails")
    public ResponseEntity<ApiResponse<List<Object[]>>> getStudentByCourseStatus(@RequestParam CourseStatus courseStatus);
}
