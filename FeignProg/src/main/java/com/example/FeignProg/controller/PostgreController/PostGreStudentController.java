package com.example.FeignProg.controller.PostgreController;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.StudentDTO;
import com.example.FeignProg.service.PostgreService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/postFeignStudent")
public class PostGreStudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.savePostStudent(studentDTO);
    }

    @GetMapping("/getStudent")
    public ResponseEntity<ApiResponse<StudentDTO>> getStud(@RequestParam Long id) {
        return studentService.findPostOne(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Boolean>> deleteStud(@RequestParam Long id) {
        return studentService.deletePostStudent(id);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Boolean>> registerToCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        return studentService.registerPostStud(studentId, courseId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> withdrawFromCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        return studentService.withdrawPostStudent(studentId, courseId);
    }

    @GetMapping("/getProg")
    public ResponseEntity<ApiResponse<CourseStatus>> findProgress(@RequestParam Long studentId, @RequestParam Long courseId) {
        return studentService.findPostProgress(studentId, courseId);
    }

}
