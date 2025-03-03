package com.example.StudentManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "organization", orphanRemoval = true)
//    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties("organization")
    private List<Student> students;

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "organization", orphanRemoval = true)
//    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties("organization")
    private List<Instructor> instructors;

    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "organization", orphanRemoval = true)
//    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties("organization")
    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
