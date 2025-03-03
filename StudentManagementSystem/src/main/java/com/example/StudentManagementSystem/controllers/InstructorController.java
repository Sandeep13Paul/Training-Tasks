package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.services.InstructorService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveInstructor(@RequestBody InstructorDTO instructorDTO) {
        instructorDTO = instructorService.insertOrUpdate(instructorDTO, instructorDTO.getOrganizationId());
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Saved Successfully", instructorDTO));
    }

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<InstructorDTO>> getById(@RequestParam Long id) {
        InstructorDTO instructorDTO = instructorService.getOne(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Found", instructorDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteInst(@RequestParam Long id) {
        Boolean flag = instructorService.deleteInstructor(id);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Deleted", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Instructor Not Deleted", "error"));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<ApiResponse<Boolean>> updateCStatus(@RequestParam Long instructorId, @RequestParam Long courseId, @RequestParam Long studentId, @RequestParam CourseStatus courseStatus) {
        boolean flag = instructorService.updateCourseStatus(instructorId, courseId, studentId, courseStatus);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Instructor Successfully Updated Course Status", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Instructor was unable to Update Course Status", "error"));
    }
}
