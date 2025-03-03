package com.example.FeignProg.feign.MongoFeign;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.MongoDTO.InstructorDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Mongo-Instructor", url = "http://localhost:8086/api/instructor")
public interface InstructorInterface {
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveInstructor(@RequestBody InstructorDTO instructorDTO);

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<InstructorDTO>> updateInstructor(@RequestBody InstructorDTO instructorDTO);

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<InstructorDTO>> getById(@RequestParam String id);

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteInst(@RequestParam String id);

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam String instructorId, @RequestParam String courseId);

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam String instructorId, @RequestParam String courseId);

    @PutMapping("/updateStatus")
    public ResponseEntity<ApiResponse<Boolean>> updateCStatus(@RequestParam String instructorId, @RequestParam String courseId, @RequestParam String studentId, @RequestParam CourseStatus courseStatus);
}
