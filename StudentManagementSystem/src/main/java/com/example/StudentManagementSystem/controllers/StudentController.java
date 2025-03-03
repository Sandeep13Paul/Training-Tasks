package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.services.StudentService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(@RequestBody StudentDTO studentDTO) {
        studentDTO = studentService.insertOrUpdate(studentDTO, studentDTO.getCourseIds(), studentDTO.getOrganizationId());
        return ResponseEntity.ok(new ApiResponse<>("success", "Student Saved Successfully", studentDTO));
    }

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<StudentDTO>> getStud(@RequestParam Long id) {
        StudentDTO studentDTO = studentService.getOne(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Student is Present", studentDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteStud(@RequestParam Long id) {
        boolean flag = studentService.deleteStudent(id);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Deleted", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Student Not Deleted", "error"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        boolean flag = studentService.enrollToCourse(studentId, courseId);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Enrolled to Course", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Student Not enrolled", "error"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        boolean flag = studentService.withdrawFromCourse(studentId, courseId);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Withdrawn from Course", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Student Not withdrawn", "error"));
    }

    @GetMapping("/getProg")
    public ResponseEntity<ApiResponse<CourseStatus>> findProgress(@RequestParam Long studentId, @RequestParam Long courseId) {
        CourseStatus courseStatus = studentService.getProgress(studentId, courseId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched status Successfully", courseStatus));
    }
}
