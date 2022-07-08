package com.example.studentdomain.service;

import com.example.studentdomain.dto.course.CourseResponse;
import com.example.studentdomain.dto.course.CourseSaveRequest;
import com.example.studentdomain.entity.Teacher;

import java.util.Set;

public interface CourseService {

    CourseResponse save(CourseSaveRequest courseSaveRequest);

    CourseResponse delete(Long id);

    CourseResponse delete(String name);

    CourseResponse addTeacher(String courseName, Teacher teacher);

    Set<CourseResponse> findAll();

    CourseResponse findById(Long id);

    CourseResponse findByName(String name);


}
