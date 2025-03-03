package com.example.FeignProg.dto.MongoDTO;

import com.example.FeignProg.dto.CourseStatus;

public class StudentCourse {

    private String courseId;

    private String courseName;

    private CourseStatus status;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }
}
