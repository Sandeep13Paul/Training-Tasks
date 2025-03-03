package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Mongo-Student", url = "http://localhost:8086/api/student")
public interface StudentInterface {
    @GetMapping("/getStudent")
    ResponseEntity<ApiResponse<List<StudentDTO>>> getStudents();

    @PostMapping("/save")
    ResponseEntity<ApiResponse<StudentDTO>> saveStudent(StudentDTO studentDTO);

    @GetMapping("/getOne/{id}")
    ResponseEntity<ApiResponse<StudentDTO>> getOneStud(@RequestParam String id);

    @DeleteMapping("/deleteOneStud/{id}")
    ResponseEntity<ApiResponse<Boolean>> deleteOneStudent(@RequestParam String id);

    @PostMapping("/register")
    ResponseEntity<ApiResponse<StudentDTO>> registerToCourse(@RequestParam String studentId, @RequestParam String courseId);

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam String studentId, @RequestParam String courseId);

    @GetMapping("/getProg")
    public ResponseEntity<ApiResponse<CourseStatus>> findProgress(@RequestParam String studentId, @RequestParam String courseId);
}
