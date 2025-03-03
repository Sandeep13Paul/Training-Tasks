package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.services.CourseService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<CourseDTO>> saveCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO courseDTO1 = courseService.insertOrUpdate(courseDTO, courseDTO.getInstructorId() ,courseDTO.getOrganizationId());
        return ResponseEntity.ok(new ApiResponse<>("success", "Course saved Successfully", courseDTO));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAll() {
        List<CourseDTO> courseDTOS = courseService.getAllCourses();
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched All Courses", courseDTOS));
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> get(@RequestParam Long id) {
        CourseDTO courseDTO = courseService.getOne(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course is Present", courseDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteCourse(@RequestParam Long id) {
        Boolean flag = courseService.deleteCourse(id);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Course Successfully Deleted", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Course Not Deleted", "error"));
    }

    @DeleteMapping("/deleteByOrg")
    public ResponseEntity<ApiResponse<Boolean>> deleteUsingOrgId(@RequestParam Long orgId, @RequestParam Long courseId) {
        Boolean flag = courseService.deleteByOrg(orgId, courseId);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Course Successfully Deleted", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Course Not Deleted", "error"));
    }
}
