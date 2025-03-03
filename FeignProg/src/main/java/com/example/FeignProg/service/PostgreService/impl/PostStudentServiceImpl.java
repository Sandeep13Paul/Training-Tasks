package com.example.FeignProg.service.PostgreService.impl;

import com.example.FeignProg.dto.CourseStatus;
import com.example.FeignProg.dto.PostgreDTO.StudentDTO;
import com.example.FeignProg.feign.PostgreFeign.StudentInterface;
import com.example.FeignProg.service.PostgreService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostStudentServiceImpl implements StudentService {

    @Autowired
    private StudentInterface studentInterface;


    @Override
    public ResponseEntity<ApiResponse<StudentDTO>> savePostStudent(StudentDTO studentDTO) {
        return studentInterface.saveStudent(studentDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<StudentDTO>> findPostOne(Long studentId) {
        return studentInterface.getStud(studentId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deletePostStudent(Long studentId) {
        return studentInterface.deleteStud(studentId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> registerPostStud(Long studentId, Long courseId) {
        return studentInterface.registerToCourse(studentId, courseId);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> withdrawPostStudent(Long studentId, Long courseId) {
        return studentInterface.withdrawFromCourse(studentId, courseId);
    }

    @Override
    public ResponseEntity<ApiResponse<CourseStatus>> findPostProgress(Long studentId, Long courseId) {
        return studentInterface.findProgress(studentId, courseId);
    }
}
