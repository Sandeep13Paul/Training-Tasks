package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.StudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    public ResponseEntity<ApiResponse<StudentDTO>> savePostStudent(StudentDTO studentDTO);

    public ResponseEntity<ApiResponse<StudentDTO>> findPostOne(Long studentId);

    public ResponseEntity<ApiResponse<Boolean>> deletePostStudent(Long studentId);

    public ResponseEntity<ApiResponse<Boolean>> registerPostStud(Long studentId, Long courseId);

    public ResponseEntity<ApiResponse<Boolean>> withdrawPostStudent(Long studentId, Long courseId);

    public ResponseEntity<ApiResponse<CourseStatus>> findPostProgress(Long studentId, Long courseId);
}
