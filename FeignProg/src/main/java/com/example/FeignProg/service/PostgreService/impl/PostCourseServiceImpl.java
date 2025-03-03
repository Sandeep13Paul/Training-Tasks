package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.PostgreDTO.CourseDTO;
import com.example.FeignProg.feign.PostgreFeign.CourseInterface;
import com.example.FeignProg.service.PostgreService.CourseService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PostCourseServiceImpl implements CourseService {

    @Autowired
    private CourseInterface courseInterface;

    @Override
    public ResponseEntity<ApiResponse<CourseDTO>> savePostCourse(CourseDTO courseDTO) {
        return courseInterface.saveCourse(courseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<List<CourseDTO>>> getAllPostCourse() {
        return courseInterface.getAll();
    }

    @Override
    public ResponseEntity<ApiResponse<CourseDTO>> getPostCourse(Long courseId) {
        return courseInterface.get(courseId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deletePostCourse(Long courseId) {
        return courseInterface.deleteCourse(courseId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deletePostCourseByOrg(Long organizationId, Long courseId) {
        return courseInterface.deleteUsingOrgId(organizationId, courseId);
    }
}
