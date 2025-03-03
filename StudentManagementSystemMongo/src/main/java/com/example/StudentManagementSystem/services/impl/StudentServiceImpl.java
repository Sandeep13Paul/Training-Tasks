package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.*;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.StudentRepository;
import com.example.StudentManagementSystem.services.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Cacheable(cacheNames = "students", key = "#id")
    public StudentDTO getOne(String id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new RuntimeException("Student Not Present");
        }
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    @Override

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) throw new RuntimeException("There are no students");
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            StudentDTO studentDTO = new StudentDTO();
            BeanUtils.copyProperties(student, studentDTO);
            studentDTOS.add(studentDTO);
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO insert(StudentDTO studentDTO) {
        // id is null || empty
        Student student = new Student();
        List<StudentCourse> studentCourses = new ArrayList<>();
        for (StudentCourse studentCourse : studentDTO.getStudentCourse()) {
            if (courseRepository.findById(studentCourse.getCourseId()).isPresent() && Objects.equals(courseRepository.findById(studentCourse.getCourseId()).get().getOrganizationId(), studentDTO.getOrganizationId())) {
                studentCourses.add(studentCourse);
            }
        }



        studentDTO.setStudentCourse(studentCourses);
        BeanUtils.copyProperties(studentDTO, student);

        student = studentRepository.save(student);
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    @Override
    @CacheEvict(cacheNames = "students", key = "#studentId")
    public StudentDTO update(String studentId, StudentDTO studentDTO) {
        if (studentId == null) {
            return insert(studentDTO);
        }

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) throw new RuntimeException("Student Not Present");

        student = studentRepository.save(student);
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    @Override
    @CacheEvict(cacheNames = "students", key = "#id")
    public boolean deleteStudent(String id) {
        studentRepository.deleteById(id);
        return !studentRepository.findById(id).isPresent();
    }

    @Override
    public boolean deleteAllStudent() {
        studentRepository.deleteAll();
        return studentRepository.findAll().isEmpty();
    }

    @Override
    public boolean enrollToCourse(String studentId, String courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));

        if (!(student.getStudentCourse().stream().filter((studentCourse -> Objects.equals(studentCourse.getCourseId(), courseId))).count() == 0)) {
            return false;
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(courseId);
        studentCourse.setCourseName(course.getName());
        studentCourse.setStatus(CourseStatus.TO_DO);
        student.getStudentCourse().add(studentCourse);
        course.getStudentIds().add(studentId);
        courseRepository.save(course);
        studentRepository.save(student);
        return true;
    }

    @Override
    public boolean withdrawFromCourse(String studentId, String courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));

        if (student.getStudentCourse().stream().filter((studentCourse -> Objects.equals(studentCourse.getCourseId(), courseId))).count() == 0) {
            return false;
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(courseId);
        studentCourse.setCourseName(course.getName());
        studentCourse.setStatus(student
                .getStudentCourse()
                .stream()
                .filter((studentCourse1 -> Objects.equals(studentCourse1.getCourseId(), courseId)))
                .collect(Collectors.toList())
                .get(0)
                .getStatus());
        student.getStudentCourse().remove(studentCourse);
        studentRepository.save(student);
        return true;
    }

    @Override
    public CourseStatus getProgress(String studentId, String courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));

        StudentCourse studentCourse = student
                .getStudentCourse()
                .stream()
                .filter(
                        studentCourse1 ->
                                Objects.equals(studentCourse1.getCourseId(), courseId))
                .collect(Collectors.toList())
                .get(0);
        return studentCourse.getStatus();
    }
}
