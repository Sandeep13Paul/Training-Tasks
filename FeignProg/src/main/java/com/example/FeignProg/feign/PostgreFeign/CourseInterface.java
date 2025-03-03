package com.example.FeignProg.feign.PostgreFeign;

import com.example.FeignProg.dto.PostgreDTO.CourseDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Postgre-Course", url = "http://localhost:8085/api/course")
public interface CourseInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<CourseDTO>> saveCourse(@RequestBody CourseDTO courseDTO);

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAll();

    @GetMapping("/getById")
    public ResponseEntity<ApiResponse<CourseDTO>> get(@RequestParam Long id);

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteCourse(@RequestParam Long id);

    @DeleteMapping("/deleteByOrg")
    public ResponseEntity<ApiResponse<Boolean>> deleteUsingOrgId(@RequestParam Long orgId, @RequestParam Long courseId);
}
