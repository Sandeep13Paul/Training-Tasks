package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.entity.*;
import com.example.StudentManagementSystem.repository.*;
import com.example.StudentManagementSystem.services.InstructorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public InstructorDTO getOne(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor Not Found"));
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setName(instructor.getName());
        instructorDTO.setDateOfBirth(instructor.getDateOfBirth());
        instructorDTO.setOrganizationId(instructor.getOrganization().getId());
        return instructorDTO;
    }

    @Override
    public List<InstructorDTO> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        List<InstructorDTO> instructorDTOS = new ArrayList<>();
        for (Instructor instructor : instructors) {
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setId(instructor.getId());
            instructorDTO.setName(instructor.getName());
            instructorDTO.setDateOfBirth(instructor.getDateOfBirth());
            instructorDTO.setOrganizationId(instructor.getOrganization().getId());
            instructorDTOS.add(instructorDTO);
        }

        return instructorDTOS;
    }

    @Override
    public InstructorDTO insertOrUpdate(InstructorDTO instructorDTO, Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization Not Found"));
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDTO, instructor);
        instructor.setOrganization(organization);

        instructorRepository.save(instructor);

//        instructorDTO.setId(instructor.getId());
//        instructorDTO.setName(instructor.getName());
//        instructorDTO.setDateOfBirth(instructor.getDateOfBirth());
//        instructorDTO.setOrganizationId(instructor.getOrganization().getId());

        return instructorDTO;
    }

    @Override
    public boolean deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
        return !instructorRepository.findById(id).isPresent();
    }

    @Override
    public boolean deleteAllInstructors() {
        instructorRepository.deleteAll();
        return instructorRepository.findAll().isEmpty();
    }

    @Override
    public boolean updateCourseStatus(Long instructorId, Long courseId, Long studentId, CourseStatus courseStatus) {
        instructorRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Instructor not found"));
        courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));

        StudentCourse studentCourse = studentCourseRepository.findByStudentAndCourseAndInstructor(studentId, courseId, instructorId);
        if (studentCourse == null) {
            return false;
        }
        studentCourse.setStatus(courseStatus);
        studentCourseRepository.save(studentCourse);
        return true;
    }
}
