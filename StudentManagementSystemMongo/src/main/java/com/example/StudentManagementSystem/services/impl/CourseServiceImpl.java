package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.entity.StudentCourse;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.StudentRepository;
import com.example.StudentManagementSystem.services.CourseService;
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
public class CourseServiceImpl implements CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public CourseDTO getOne(String id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new RuntimeException("Course is Not Present");
        }

        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        return courseDTO;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        if (courses.isEmpty()) {
            return courseDTOS;
        }

        for (Course course : courses) {
            CourseDTO courseDTO = new CourseDTO();
            BeanUtils.copyProperties(course, courseDTO);
            courseDTOS.add(courseDTO);
        }
        return courseDTOS;
    }

    @Override
    public CourseDTO save(CourseDTO courseDTO, String instructorId) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
        if (instructor == null) {
            log.info("Instructor Not Present");
            throw new RuntimeException("Instructor Not Found");
        }
        if (!Objects.equals(instructor.getOrganizationId(), courseDTO.getOrganizationId())) {
            log.info("Both Should be in the same Organization");
            throw new RuntimeException("Organization should be same");
        }
        course.setInstructorId(instructorId);
        if (courseDTO.getStudentIds().isEmpty()) {
            courseDTO.setStudentIds(new ArrayList<>());
        }
        course = courseRepository.save(course);
        BeanUtils.copyProperties(course, courseDTO);
        return courseDTO;
    }

    @Override
    public CourseDTO update(CourseDTO courseDTO, String instructorId) {
        instructorRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Instructor Not Found"));
        if (!courseRepository.findById(courseDTO.getId()).isPresent()) {
            return save(courseDTO, instructorId);
        }
        Course course = courseRepository.findById(courseDTO.getId()).get();
        BeanUtils.copyProperties(courseDTO, course);
        course.setInstructorId(instructorId);

        course = courseRepository.save(course);
        BeanUtils.copyProperties(course, courseDTO);
        return courseDTO;
    }

    @Override
    public boolean deleteCourse(String id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) throw new RuntimeException("Course Not Found");
        Instructor instructor = instructorRepository.findAll().stream().filter(instructor1 -> Objects.equals(instructor1.getCourseId(), id)).collect(Collectors.toList()).get(0);
        instructor.setCourseId(null);
        course.setInstructorId(null);
                studentRepository
                        .findAll()
                        .forEach(
                                student -> {
                                    student.getStudentCourse()
                                            .removeIf(
                                                    studentCourse ->
                                                        Objects.equals(studentCourse.getCourseId(), id)
                                            );
                                    studentRepository.save(student);
                                }
                        );
        courseRepository.deleteById(id);
        return !courseRepository.findById(id).isPresent();
    }
}
