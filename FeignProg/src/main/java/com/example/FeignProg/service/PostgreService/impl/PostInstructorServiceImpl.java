package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.InstructorDTO;
import com.example.FeignProg.feign.PostgreFeign.InstructorInterface;
import com.example.FeignProg.service.PostgreService.InstructorService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class PostInstructorServiceImpl implements InstructorService {

    @Autowired
    public InstructorInterface instructorInterface;


    @Override
    public ResponseEntity<ApiResponse<InstructorDTO>> savePostInstructor(InstructorDTO instructorDTO) {
        return instructorInterface.saveInstructor(instructorDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<InstructorDTO>> getPostInstructor(Long instructorId) {
        return instructorInterface.getById(instructorId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deletePostInstructor(Long instructorId) {
        return instructorInterface.deleteInst(instructorId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> updatePostInstructor(Long instructorId, Long courseId, Long studentId, CourseStatus courseStatus) {
        return instructorInterface.updateCStatus(instructorId, courseId, studentId, courseStatus);
    }
}
