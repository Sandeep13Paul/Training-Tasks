package com.example.FeignProg.feign.PostgreFeign;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.InstructorDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Postgre-Instructor", url = "http://localhost:8085/api/instructor")
public interface InstructorInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<InstructorDTO>> saveInstructor(@RequestBody InstructorDTO instructorDTO);

    @GetMapping("/getDetails")
    public ResponseEntity<ApiResponse<InstructorDTO>> getById(@RequestParam Long id);

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteInst(@RequestParam Long id);

    @PutMapping("/updateStatus")
    public ResponseEntity<ApiResponse<Boolean>> updateCStatus(@RequestParam Long instructorId, @RequestParam Long courseId, @RequestParam Long studentId, @RequestParam CourseStatus courseStatus);
    }
