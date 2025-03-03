package com.example.FeignProg.service.PostgreService;

import com.example.FeignProg.dto.PostgreDTO.CourseDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
    public ResponseEntity<ApiResponse<CourseDTO>> savePostCourse(CourseDTO courseDTO);
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAllPostCourse();
    public ResponseEntity<ApiResponse<CourseDTO>> getPostCourse(Long courseId);
    public ResponseEntity<ApiResponse<Boolean>> deletePostCourse(Long courseId);
    public ResponseEntity<ApiResponse<Boolean>> deletePostCourseByOrg(Long organizationId, Long courseId);
}
