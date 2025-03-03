package com.example.StudentManagementSystem.repository;

import com.example.StudentManagementSystem.entity.Course;
import com.example.StudentManagementSystem.entity.CourseStatus;
import com.example.StudentManagementSystem.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    @Query("SELECT s FROM StudentCourse s WHERE s.student.id = :studentId AND s.course.id = :courseId")
    StudentCourse findByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("SELECT s FROM StudentCourse s WHERE s.student.id = :studentId AND s.course.id = :courseId AND s.course.instructor.id = :instructorId")
    StudentCourse findByStudentAndCourseAndInstructor(@Param("studentId") Long studentId, @Param("courseId") Long courseId, @Param("instructorId") Long instructorId);

    @Query("SELECT COUNT(s.id) FROM Student s WHERE s.organization.id = :organizationId")
    Long countStudentsInOrganization(@Param("organizationId") Long organizationId);

    @Query("SELECT COUNT(sc.student) FROM StudentCourse sc WHERE sc.course.id = :courseId")
    Long countStudentsInCourse(@Param("courseId") Long courseId);

    @Query("SELECT c.id, c.name, i.id, i.name FROM Course c LEFT JOIN c.instructor i")
    List<Object[]> getInstructorDetailsForCourses();

    @Query("SELECT COUNT(i.id) FROM Instructor i")
    Long countInstructors();

    @Query("SELECT s.id, s.name, c.id, c.name, i.id, i.name FROM StudentCourse sc JOIN sc.student s JOIN sc.course c LEFT JOIN c.instructor i WHERE c.id = :courseId")
    List<Object[]> getStudentCourseInstructorDetailsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT s.id, s.name, c.id, c.name, sc.status FROM StudentCourse sc JOIN sc.student s JOIN sc.course c WHERE sc.status = :status")
    List<Object[]> getStudentsByCourseStatus(@Param("status") CourseStatus status);

    @Modifying
    @Query(value = "DELETE FROM StudentCourse s WHERE s.course.id = :id AND s.student.id = :studId")
    void deleteFromStudentCourse(@Param("id") Long id, @Param("studId") Long studId);
}
