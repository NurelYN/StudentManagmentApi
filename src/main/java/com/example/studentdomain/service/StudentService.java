package com.example.studentdomain.service;

import com.example.studentdomain.dto.student.StudentResponse;
import com.example.studentdomain.dto.student.StudentSaveRequest;
import com.example.studentdomain.dto.student.StudentUpdateRequest;

import java.util.Set;

public interface StudentService {

    StudentResponse save(StudentSaveRequest studentRequest);

    Set<StudentResponse> findAll();

    StudentResponse delete(Long id);

    StudentResponse findById(Long id);

    StudentResponse update(StudentUpdateRequest studentUpdateRequest, Long id);
}
