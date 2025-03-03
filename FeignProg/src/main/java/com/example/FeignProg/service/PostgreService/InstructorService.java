package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.InstructorDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface InstructorService {
    public ResponseEntity<ApiResponse<InstructorDTO>> savePostInstructor(InstructorDTO instructorDTO);
    public ResponseEntity<ApiResponse<InstructorDTO>> getPostInstructor(Long instructorId);
    public ResponseEntity<ApiResponse<Boolean>> deletePostInstructor(Long instructorId);
    public ResponseEntity<ApiResponse<Boolean>> updatePostInstructor(Long instructorId, Long courseId, Long studentId, CourseStatus courseStatus);
}
