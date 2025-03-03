package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.*;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.repository.StudentCourseRepository;
import com.example.StudentManagementSystem.repository.StudentRepository;
import com.example.StudentManagementSystem.services.StudentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

//    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public StudentDTO getOne(Long id) {
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            throw new RuntimeException("Student Not Found");
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setOrganizationId(student.getOrganization().getId());
        studentDTO.setName(student.getName());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        List<Long> courseIds = new ArrayList<>();
        student.getStudentCourse().forEach(studentCourse ->
                courseIds.add(studentCourse.getCourse().getId())
        );
        studentDTO.setCourseIds(courseIds);
//        logger.info("This is StudentDTO {}", studentDTO);
        return studentDTO;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students =  studentRepository.findAll();
        if (students.isEmpty()) {
            throw new RuntimeException("Student Not Present");
        }

//        logger.info("{}", students);

        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(student.getId());
            studentDTO.setName(student.getName());
            List<Long> courseIds = new ArrayList<>();
            student.getStudentCourse().forEach((studentCourse -> {
                courseIds.add(studentCourse.getCourse().getId());
            }));
            studentDTO.setCourseIds(courseIds);
            studentDTO.setDateOfBirth(student.getDateOfBirth());
            studentDTO.setOrganizationId(student.getOrganization().getId());
            studentDTOS.add(studentDTO);
        }
//        logger.info("This is StudentDTO {}", studentDTOS);
        return studentDTOS;
    }

    @Override
    public StudentDTO insertOrUpdate(StudentDTO studentDTO, List<Long> courseIds, Long organizationId) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setId(studentDTO.getId());

        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization Not Found"));
        student.setOrganization(organization);
        if (student.getId() == null) {
            student.setStudentCourse(new ArrayList<>());
            studentRepository.save(student);
        }
        if (!courseIds.isEmpty()) {
            List<Course> courses = courseRepository.findAllById(courseIds);

            List<StudentCourse> studentCourses = courses.stream().map(course -> {
                if (!Objects.equals(course.getOrganization().getId(), organizationId)) return null;
                StudentCourse sc = new StudentCourse();
                sc.setStudent(student);
                sc.setCourse(course);
                sc.setStatus(CourseStatus.TO_DO);
                if (student.getStudentCourse().contains(sc)) return null;
                return sc;
            }).collect(Collectors.toList());

            student.getStudentCourse().addAll(studentCourses);
            studentCourseRepository.saveAll(studentCourses);
//
        }
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    @Override
    public boolean deleteStudent(Long id) {
        studentRepository.deleteById(id);
        return !studentRepository.findById(id).isPresent();
    }

    @Override
    public boolean deleteAllStudent() {
        studentRepository.deleteAll();
        return studentRepository.findAll().isEmpty();
    }

    @Override
    public boolean enrollToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));
        if (!Objects.equals(student.getOrganization().getId(), course.getOrganization().getId())) {
            throw new RuntimeException("Student and Course Should be in the same organization");
        }
        if (!(student.getStudentCourse().stream().filter((studentCourse -> studentCourse.getCourse() == course)).count() == 0)) {
            return false;
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setStatus(CourseStatus.TO_DO);
        student.getStudentCourse().add(studentCourse);
        studentCourseRepository.save(studentCourse);
        studentRepository.save(student);
        return true;
    }

    @Override
    @Transactional
    public boolean withdrawFromCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));

        if (student.getStudentCourse().stream().filter((studentCourse -> studentCourse.getCourse() == course)).count() == 0) {
            return false;
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setStatus(student.getStudentCourse().stream().filter(
                studentCourse1 -> Objects.equals(studentCourse1.getStudent(), student)).collect(Collectors.toList())
                .get(0).getStatus());
        student.getStudentCourse().remove(studentCourse);
        studentCourseRepository.deleteFromStudentCourse(studentCourse.getCourse().getId(), studentCourse.getStudent().getId());
        studentRepository.save(student);
        return true;
    }

    @Override
    public CourseStatus getProgress(Long studentId, Long courseId) {
        studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));

        StudentCourse studentCourse = studentCourseRepository.findByStudentAndCourse(studentId, courseId);
        if (studentCourse == null) {
            throw new RuntimeException("Student is not enrolled to the given Course");
        }

        return studentCourse.getStatus();
    }
}
