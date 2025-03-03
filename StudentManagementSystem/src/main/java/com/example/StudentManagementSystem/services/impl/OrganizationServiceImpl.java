package com.example.StudentManagementSystem.services.impl;

import com.example.StudentManagementSystem.dto.OrganizationDTO;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.Organization;
import com.example.StudentManagementSystem.repository.OrganizationRepository;
import com.example.StudentManagementSystem.repository.StudentCourseRepository;
import com.example.StudentManagementSystem.services.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public OrganizationDTO getOne(Long id) {
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Organization Not Found"));
        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanUtils.copyProperties(organization, organizationDTO);
        return organizationDTO;
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        List<OrganizationDTO> organizationDTOS = new ArrayList<>();
        for (Organization organization : organizations) {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            BeanUtils.copyProperties(organization, organizationDTO);
            organizationDTOS.add(organizationDTO);
        }

        return organizationDTOS;
    }

    @Override
    public OrganizationDTO insertOrUpdate(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDTO, organization);
        organization = organizationRepository.save(organization);
        BeanUtils.copyProperties(organization, organizationDTO);
        return organizationDTO;
    }

    @Override
    public boolean deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
        return !organizationRepository.findById(id).isPresent();
    }

    @Override
    public boolean deleteAllOrganizations() {
        organizationRepository.deleteAll();
        return organizationRepository.findAll().isEmpty();
    }

    public Long getStudentCountInOrganization(Long orgId) {
        return studentCourseRepository.countStudentsInOrganization(orgId);
    }

    public Long getStudentCountInCourse(Long courseId) {
        return studentCourseRepository.countStudentsInCourse(courseId);
    }

    public Long getInstructorCount(Long orgId) {
        return studentCourseRepository.countInstructors();
    }

    @Override
    public List<Object[]> getAllDetailsByCourseId(Long courseId) {
        return studentCourseRepository.getStudentCourseInstructorDetailsByCourseId(courseId);
    }

    @Override
    public List<Object[]> getStudentDetailsByStatus(CourseStatus courseStatus) {
        return studentCourseRepository.getStudentsByCourseStatus(courseStatus);
    }

    public List<Object[]> getInstructorDetails(Long orgId) {
        return studentCourseRepository.getInstructorDetailsForCourses();
    }

}
