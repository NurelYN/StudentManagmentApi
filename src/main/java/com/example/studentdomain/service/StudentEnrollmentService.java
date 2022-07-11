package com.example.studentdomain.service;

import com.example.studentdomain.dto.studentEnrollment.StudentEnrollmentSaveRequest;
import com.example.studentdomain.entity.StudentEnrollment;

public interface StudentEnrollmentService {

    StudentEnrollment save(StudentEnrollmentSaveRequest studentEnrollmentSaveRequest);

    StudentEnrollment addGrade(StudentEnrollmentSaveRequest studentEnrollmentSaveRequest, Double grade);

    StudentEnrollment findByStudentIdAndCourseName(Long studentId,String courseName);

    Double getAverageForCourse(String name);

    Double getAverageForStudentInAllCourses(Long id);
}
