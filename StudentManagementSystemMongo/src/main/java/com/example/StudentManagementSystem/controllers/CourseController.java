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

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<CourseDTO>> saveCourse(@RequestBody CourseDTO courseDTO) {
        courseDTO = courseService.save(courseDTO, courseDTO.getInstructorId());
        return ResponseEntity.ok(new ApiResponse<>("success", "Course saved Successfully", courseDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourse(@RequestBody CourseDTO courseDTO) {
        courseDTO = courseService.update(courseDTO, courseDTO.getInstructorId());
        return ResponseEntity.ok(new ApiResponse<>("success", "Course saved Successfully", courseDTO));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAll() {
        List<CourseDTO> courseDTOS = courseService.getAllCourses();
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched All Courses", courseDTOS));
    }

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> get(@RequestParam String id) {
        CourseDTO courseDTO = courseService.getOne(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Course is Present", courseDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteCourse(@RequestParam String id) {
        Boolean flag = courseService.deleteCourse(id);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Course Successfully Deleted", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Course Not Deleted", "error"));
    }
}
