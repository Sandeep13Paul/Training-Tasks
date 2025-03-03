package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.MongoDTO.CourseDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Mongo-Course", url = "http://localhost:8086/api/course")
public interface CourseInterface {
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<CourseDTO>> saveCourse(@RequestBody CourseDTO courseDTO);

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<CourseDTO>> updateCourse(@RequestBody CourseDTO courseDTO);

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAll();

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> get(@RequestParam String id);

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteCourse(@RequestParam String id);
}
