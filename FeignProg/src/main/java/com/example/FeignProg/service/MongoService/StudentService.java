package com.example.FeignProg.service.MongoService;

import com.example.FeignProg.dto.MongoDTO.MongoStudentDTO;
import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    public ResponseEntity<ApiResponse<List<MongoStudentDTO>>> findAll();

    public ResponseEntity<ApiResponse<MongoStudentDTO>> saveStudent(StudentDTO studentDTO);

    public ResponseEntity<ApiResponse<MongoStudentDTO>> findOne(boolean isMongo, String id);

    public ResponseEntity<ApiResponse<Boolean>> deleteStudent(boolean isMongo, String id);
}
