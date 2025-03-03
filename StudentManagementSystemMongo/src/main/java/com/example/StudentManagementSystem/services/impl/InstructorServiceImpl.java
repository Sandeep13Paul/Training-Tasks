package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.entity.*;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.repository.StudentRepository;
import com.example.StudentManagementSystem.services.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    private static final Logger log = LoggerFactory.getLogger(InstructorServiceImpl.class);
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public InstructorDTO getOne(String id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new RuntimeException("Instructor Not Found"));
        InstructorDTO instructorDTO = new InstructorDTO();
        BeanUtils.copyProperties(instructor, instructorDTO);
        return instructorDTO;
    }

    @Override
    public List<InstructorDTO> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        List<InstructorDTO> instructorDTOS = new ArrayList<>();
        for (Instructor instructor : instructors) {
            InstructorDTO instructorDTO = new InstructorDTO();
            BeanUtils.copyProperties(instructor, instructorDTO);
            instructorDTOS.add(instructorDTO);
        }

        return instructorDTOS;
    }

    @Override
    public InstructorDTO save(InstructorDTO instructorDTO) {
//        Instructor instructor = instructorRepository.findById(instructorDTO.getId()).orElse(null);
//        if (instructor != null) {
//            return update(instructorDTO);
//        }
        Instructor instructor =  new Instructor();
        BeanUtils.copyProperties(instructorDTO, instructor);
        Organization organization = organizationRepository.findById(instructorDTO.getOrganizationId()).orElse(null);
        if (organization == null) {
            log.info("Organization should be present");
            throw new RuntimeException("Organization not found");
        }
        instructor = instructorRepository.save(instructor);
        BeanUtils.copyProperties(instructor, instructorDTO);
        return instructorDTO;
    }

    @Override
    public InstructorDTO update(InstructorDTO instructorDTO) {
        Instructor instructor = instructorRepository.findById(instructorDTO.getId()).orElse(null);
        if (instructor == null) {
            return save(instructorDTO);
        }
        BeanUtils.copyProperties(instructorDTO, instructor);
        instructor =  instructorRepository.save(instructor);
        BeanUtils.copyProperties(instructor, instructorDTO);
        return instructorDTO;
    }

    @Override
    public boolean deleteInstructor(String id) {
        instructorRepository.deleteById(id);
        return !instructorRepository.findById(id).isPresent();
    }

    @Override
    public boolean deleteAllInstructors() {
        instructorRepository.deleteAll();
        return instructorRepository.findAll().isEmpty();
    }

    public boolean enrollToCourse(String instructorId, String courseId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        if (Objects.equals(instructor.getCourseId(), courseId)) {
            return false;
        }

        instructor.setCourseId(courseId);
        course.setInstructorId(instructorId);
        instructorRepository.save(instructor);
        courseRepository.save(course);

        return true;
    }

    public boolean withdrawFromCourse(String instructorId, String courseId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Instructor not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        if (!Objects.equals(instructor.getCourseId(), courseId)) {
            return false;
        }

        if (instructor.getCourseId() == courseId) {
            instructor.setCourseId(null);
            course.setInstructorId(null);
        }
        instructorRepository.save(instructor);
        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean updateCourseStatus(String instructorId, String courseId, String studentId, CourseStatus courseStatus) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Instructor not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));

        if (!Objects.equals(instructor.getCourseId(), courseId)) throw new RuntimeException("Course Not Present in the Instructor");

        if (course.getStudentIds().stream().anyMatch(studentId1 -> Objects.equals(studentId1, studentId)))
            throw new RuntimeException("Student Not Opted for the Course");

        StudentCourse studentCourse = student
                .getStudentCourse()
                .stream()
                .filter(
                        studentCourse1 ->
                                Objects.equals(studentCourse1.getCourseId(), courseId))
                .collect(Collectors.toList())
                .get(0);
        student.getStudentCourse().remove(studentCourse);
        studentCourse.setStatus(courseStatus);
        student.getStudentCourse().add(studentCourse);

        studentRepository.save(student);
        return true;
    }
}
