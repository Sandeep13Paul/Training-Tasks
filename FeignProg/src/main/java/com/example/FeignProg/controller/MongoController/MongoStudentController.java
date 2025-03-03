package com.example.FeignProg.controller.MongoController;

import com.example.FeignProg.dto.MongoDTO.MongoStudentDTO;
import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.service.MongoService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class MongoStudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = "/student/getAll")
    public ResponseEntity<ApiResponse<List<MongoStudentDTO>>> getAllStuds() {
        return ResponseEntity.ok(Objects.requireNonNull(studentService.findAll().getBody()));
    }

    @PostMapping(value = "/student/saveStudent")
    public ResponseEntity<ApiResponse<MongoStudentDTO>> saveTheStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(Objects.requireNonNull(studentService.saveStudent(studentDTO).getBody()));
    }

    @GetMapping(value = "/student/getOne")
    public ResponseEntity<ApiResponse<MongoStudentDTO>> getOneStudent(@RequestParam boolean flag, @RequestParam String id) {
        return ResponseEntity.ok(Objects.requireNonNull(studentService.findOne(flag, id).getBody()));
    }

    @DeleteMapping(value = "/deleteOne/")
    public ResponseEntity<ApiResponse<Boolean>> deleteOneStudent(@RequestParam boolean flag, @RequestParam String id) {
        return ResponseEntity.ok(Objects.requireNonNull(studentService.deleteStudent(flag, id).getBody()));
    }
}