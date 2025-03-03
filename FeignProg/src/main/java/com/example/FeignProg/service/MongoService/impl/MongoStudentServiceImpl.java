package com.example.FeignProg.service.MongoService.impl;

import com.example.FeignProg.dto.MongoDTO.MongoStudentDTO;
import com.example.FeignProg.feign.MongoFeign.StudentInterface;
import com.example.FeignProg.service.MongoService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoStudentServiceImpl implements StudentService {

    @Autowired
    private StudentInterface feignMongoInterface;

    @Override
    public ResponseEntity<ApiResponse<List<MongoStudentDTO>>> findAll() {
        return feignMongoInterface.getStudents();
    }

    @Override
    public ResponseEntity<ApiResponse<MongoStudentDTO>> saveStudent(MongoStudentDTO studentDTO) {
        return feignMongoInterface.saveStudent(studentDTO);
    }

    @Override
    public ResponseEntity<ApiResponse<MongoStudentDTO>> findOne(boolean isMongo, String id) {
        if (isMongo) {
            return feignMongoInterface.getOneStud(id);
        }
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deleteStudent(boolean isMongo, String id) {
        if (isMongo) return feignMongoInterface.deleteOneStudent(id);
        return null;
    }
}
