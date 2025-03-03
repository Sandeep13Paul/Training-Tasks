package com.example.FeignProg.service.MongoService.impl;

import com.example.FeignProg.dto.MongoDTO.MongoStudentDTO;
import com.example.FeignProg.dto.MongoDTO.StudentDTO;
import com.example.FeignProg.feign.MongoFeign.StudentInterface;
import com.example.FeignProg.service.MongoService.StudentService;
import com.example.FeignProg.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MongoStudentServiceImpl implements StudentService {

    @Autowired
    private StudentInterface feignMongoInterface;

    @Override
    public ResponseEntity<ApiResponse<List<MongoStudentDTO>>> findAll() {
        List<StudentDTO> studentDTOS = Objects.requireNonNull(feignMongoInterface.getStudents().getBody()).getData();
        List<MongoStudentDTO> mongoStudentDTOS = new ArrayList<>();
        for (StudentDTO studentDTO : studentDTOS) {
            MongoStudentDTO mongoStudentDTO = new MongoStudentDTO();
            BeanUtils.copyProperties(studentDTO, mongoStudentDTO);
            mongoStudentDTOS.add(mongoStudentDTO);
        }
        return ResponseEntity.ok(new ApiResponse<>("success", "Found", mongoStudentDTOS));
    }

    @Override
    public ResponseEntity<ApiResponse<MongoStudentDTO>> saveStudent(StudentDTO studentDTO) {
        MongoStudentDTO mongoStudentDTO = new MongoStudentDTO();
        BeanUtils.copyProperties(Objects.requireNonNull(feignMongoInterface.saveStudent(studentDTO).getBody()), mongoStudentDTO);
        return ResponseEntity.ok(new ApiResponse<>("success", "saved", mongoStudentDTO));
    }

    @Override
    public ResponseEntity<ApiResponse<MongoStudentDTO>> findOne(boolean isMongo, String id) {
        if (isMongo) {
            MongoStudentDTO mongoStudentDTO = new MongoStudentDTO();
            BeanUtils.copyProperties(Objects.requireNonNull(feignMongoInterface.getOneStud(id).getBody()), mongoStudentDTO);
            return ResponseEntity.ok(new ApiResponse<>("success", "Found", mongoStudentDTO));
        }
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deleteStudent(boolean isMongo, String id) {
        if (isMongo) return feignMongoInterface.deleteOneStudent(id);
        return null;
    }
}
