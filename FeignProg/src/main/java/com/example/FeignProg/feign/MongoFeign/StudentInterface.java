package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.MongoDTO.MongoStudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Mongo-Student", url = "http://localhost:8086/api/student")
public interface StudentInterface {
    @GetMapping("/getStudent")
    ResponseEntity<ApiResponse<List<MongoStudentDTO>>> getStudents();

    @PostMapping("/save")
    ResponseEntity<ApiResponse<MongoStudentDTO>> saveStudent(MongoStudentDTO studentDTO);

    @GetMapping("/getOne/{id}")
    ResponseEntity<ApiResponse<MongoStudentDTO>> getOneStud(@RequestParam String id);

    @DeleteMapping("/deleteOneStud/{id}")
    ResponseEntity<ApiResponse<Boolean>> deleteOneStudent(@RequestParam String id);

    @PostMapping("/register")
    ResponseEntity<ApiResponse<MongoStudentDTO>> registerToCourse(@RequestParam String studentId, @RequestParam String courseId);

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam String studentId, @RequestParam String courseId);

    @GetMapping("/getProg")
    public ResponseEntity<ApiResponse<CourseStatus>> findProgress(@RequestParam String studentId, @RequestParam String courseId);
}
