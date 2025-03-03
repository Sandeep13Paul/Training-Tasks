package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.CourseDTO;
import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.Instructor;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.services.CourseService;
import com.example.StudentManagementSystem.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
//@Slf4j
public class CourseServiceImpl implements CourseService {

//    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public CourseDTO getOne(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new RuntimeException("Course Not Found");
        }
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setFee(course.getFee());
        courseDTO.setInstructorId(course.getInstructor().getId());
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setOrganizationId(course.getOrganization().getId());


        return courseDTO;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = courses.stream().map((course) -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setFee(course.getFee());
            courseDTO.setInstructorId(course.getInstructor().getId());
            courseDTO.setId(course.getId());
            courseDTO.setName(course.getName());
            courseDTO.setOrganizationId(course.getOrganization().getId());
            return courseDTO;
        }).collect(Collectors.toList());
        return courseDTOS;
    }

    @Override
    public CourseDTO insertOrUpdate(CourseDTO courseDTO, Long instructorId, Long organizationId) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        Instructor instructor;
        if (instructorId != null) instructor = instructorRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Instructor Not Found"));
        else throw new RuntimeException("Give a Proper instructor id");
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization Not Found"));

        if (instructor != null && instructor.getCourse() != null) {
            throw new RuntimeException("Instructor already is assigned to a course");
        }
        if (!Objects.equals(Objects.requireNonNull(instructor).getOrganization().getId(), organizationId)) {
            throw new RuntimeException("Instructor Not Present in that organization");
        }
        course.setInstructor(instructor);
        course.setOrganization(organization);
        Course course1 = courseRepository.save(course);
        BeanUtils.copyProperties(course1, courseDTO);
        return courseDTO;
    }

    @Override
    public boolean deleteCourse(Long id) {
        courseRepository.deleteById(id);
        return !courseRepository.findById(id).isPresent();
    }

    @Override
    public boolean deleteAllCourses() {
        courseRepository.deleteAll();
        return courseRepository.findAll().isEmpty();
    }

    @Override
    public boolean deleteByOrg(Long orgId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));
        organizationRepository.findById(orgId).orElseThrow(() -> new RuntimeException("Organization Not Found"));

        if (!Objects.equals(course.getOrganization().getId(), orgId)) {
            return false;
        }
        courseRepository.deleteById(courseId);
        return !courseRepository.findById(courseId).isPresent();
    }
}
