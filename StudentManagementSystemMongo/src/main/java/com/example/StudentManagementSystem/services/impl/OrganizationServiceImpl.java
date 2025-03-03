package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.InstructorDTO;
import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.dto.StudentDTO;
import com.example.StudentManagementSystem.entity.*;
import com.example.StudentManagementSystem.repository.CourseRepository;
import com.example.StudentManagementSystem.repository.InstructorRepository;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.repository.StudentRepository;
import com.example.StudentManagementSystem.services.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {


    private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

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

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream().map(org -> {
            OrganizationDTO dto = new OrganizationDTO();
            BeanUtils.copyProperties(org, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO getOrganizationById(String organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }

    @Override
    public OrganizationDTO addOrganization(OrganizationDTO organizationDto) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDto, organization);
        if(organizationDto.getOrganizationId() != null) {
            if(organizationRepository.findById(organizationDto.getOrganizationId()).isPresent()){
                throw new RuntimeException("Organization already exists");
            }
        }
        organization.setOrganizationId(null);
        Organization savedOrganization = organizationRepository.save(organization);
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(savedOrganization, dto);
        return dto;
    }

    @Override
    public OrganizationDTO updateOrganization(String organizationId, OrganizationDTO updatedOrganizationDto) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        BeanUtils.copyProperties(updatedOrganizationDto, organization);
        organization.setOrganizationId(organizationId);
        Organization updatedOrganization = organizationRepository.save(organization);
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(updatedOrganization, dto);
        return dto;
    }

    @Override
    public void deleteOrganization(String organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        organizationRepository.delete(organization);
    }

    @Override
    public long getStudentCount(String orgId) {
        log.info("Fetching student count for organization: {}", orgId);
        return organizationRepository.getStudentCount(orgId);
    }

//    @Override
//    public List<Long> getStudentCountByCourse(String orgId) {
//        log.info("Fetching student count by course for organization: {}", orgId);
//        return organizationRepository.getStudentCountByCourse(orgId);
//    }

    @Override
    public List<InstructorDTO> getInstructorsByCourse(String courseId) {
        log.info("Fetching instructors for course: {}", courseId);
        List<Instructor> instructors = organizationRepository.getInstructorsByCourse(courseId);
        return instructors.stream().map(instructor -> {
            InstructorDTO dto = new InstructorDTO();
            BeanUtils.copyProperties(instructor, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public long getInstructorCount(String orgId) {
        log.info("Fetching instructor count for organization: {}", orgId);
        return organizationRepository.getInstructorCount(orgId);
    }

    @Override
    public List<StudentDTO> getStudentsByCourseStatus(CourseStatus status) {
        log.info("Fetching students by course status: {}", status);
        List<Student> students = organizationRepository.getStudentsByCourseStatus(status);
        return students.stream().map(student -> {
            StudentDTO dto = new StudentDTO();
            BeanUtils.copyProperties(student, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}
