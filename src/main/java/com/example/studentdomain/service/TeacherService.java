package com.example.studentdomain.service;

import com.example.studentdomain.dto.teacher.TeacherResponse;
import com.example.studentdomain.dto.teacher.TeacherSaveRequest;
import com.example.studentdomain.dto.teacher.TeacherUpdateRequest;

import java.util.Set;

public interface TeacherService {


    TeacherResponse save(TeacherSaveRequest teacherSaveRequest);

    Set<TeacherResponse> findAll();

    TeacherResponse delete(Long id);

    TeacherResponse findById(Long id);

    TeacherResponse update(TeacherUpdateRequest teacherUpdateRequest, Long id);
}
