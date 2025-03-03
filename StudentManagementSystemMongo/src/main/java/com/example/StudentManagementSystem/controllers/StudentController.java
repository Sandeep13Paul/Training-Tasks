package com.example.StudentManagementSystem.controllers;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.services.StudentService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(@RequestBody StudentDTO studentDTO) {
        studentDTO = studentService.insert(studentDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Student Saved Successfully", studentDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(@RequestParam String studentId, @RequestBody StudentDTO studentDTO) {
        studentDTO = studentService.update(studentId, studentDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "Student Saved Successfully", studentDTO));
    }

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStud() {
        List<StudentDTO> studentDTOS = studentService.getAllStudents();
        return ResponseEntity.ok(new ApiResponse<>("success", "fetched all students", studentDTOS));
    }

    @GetMapping("/getOne")
    public ResponseEntity<ApiResponse<StudentDTO>> getOneStud(@RequestParam String id) {
        StudentDTO studentDTO = studentService.getOne(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched the given student with provided Id", studentDTO));
    }

    @DeleteMapping("/deleteOneStud")
    public ResponseEntity<ApiResponse<Boolean>> deleteOne(@RequestParam String id) {
        boolean flag = studentService.deleteStudent(id);
        if (!flag) return ResponseEntity.badRequest().body(new ApiResponse<>("Data Not Found", "error"));
        return ResponseEntity.ok(new ApiResponse<>("success", "Student successfully deleted", flag));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam String studentId, @RequestParam String courseId) {
        boolean flag = studentService.enrollToCourse(studentId, courseId);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Enrolled to Course", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Student Not enrolled", "error"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam String studentId, @RequestParam String courseId) {
        boolean flag = studentService.withdrawFromCourse(studentId, courseId);
        if (flag) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Student Successfully Withdrawn from Course", flag));
        }
        return ResponseEntity.ok(new ApiResponse<>("Student Not withdrawn", "error"));
    }

    @GetMapping("/getProg")
    public ResponseEntity<ApiResponse<CourseStatus>> findProgress(@RequestParam String studentId, @RequestParam String courseId) {
        CourseStatus courseStatus = studentService.getProgress(studentId, courseId);
        return ResponseEntity.ok(new ApiResponse<>("success", "Fetched status Successfully", courseStatus));
    }
}
