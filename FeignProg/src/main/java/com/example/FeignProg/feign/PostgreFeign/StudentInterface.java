package com.example.FeignProg.feign.PostgreFeign;


import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.StudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Postgre-Student", url = "http://localhost:8085/api/student")
public interface StudentInterface {
    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(@RequestBody StudentDTO studentDTO);

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<StudentDTO>> getStud(@RequestParam Long id);

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteStud(@RequestParam Long id);

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam Long studentId, @RequestParam Long courseId);

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam Long studentId, @RequestParam Long courseId);

    @GetMapping("/getProg")
    public ResponseEntity<ApiResponse<CourseStatus>> findProgress(@RequestParam Long studentId, @RequestParam Long courseId);
}
