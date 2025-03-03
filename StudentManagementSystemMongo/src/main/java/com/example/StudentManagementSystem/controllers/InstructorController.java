package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.services.InstructorService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveInstructor(@RequestBody InstructorDTO instructorDTO) {
        instructorDTO = instructorService.save(instructorDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Saved Successfully", instructorDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<InstructorDTO>> updateInstructor(@RequestBody InstructorDTO instructorDTO) {
        instructorDTO = instructorService.update(instructorDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Saved Successfully", instructorDTO));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<InstructorDTO>> getById(@RequestParam String id) {
        InstructorDTO instructorDTO = instructorService.getOne(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Found", instructorDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteInst(@RequestParam String id) {
        Boolean flag = instructorService.deleteInstructor(id);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Deleted", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Instructor Not Deleted", "error"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam String instructorId, @RequestParam String courseId) {
        boolean flag = instructorService.enrollToCourse(instructorId, courseId);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Enrolled to Course", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Instructor Not enrolled", "error"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam String instructorId, @RequestParam String courseId) {
        boolean flag = instructorService.withdrawFromCourse(instructorId, courseId);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Withdrawn from Course", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Instructor Not withdrawn", "error"));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<ApiResponse<Boolean>> updateCStatus(@RequestParam String instructorId, @RequestParam String courseId, @RequestParam String studentId, @RequestParam CourseStatus courseStatus) {
        boolean flag = instructorService.updateCourseStatus(instructorId, courseId, studentId, courseStatus);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Updated Course Status", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Instructor was unable to Update Course Status", "error"));
    }
}
