package com.example.studentdomain.service.Impl;

import com.example.studentdomain.dto.student.StudentResponse;
import com.example.studentdomain.dto.student.StudentSaveRequest;
import com.example.studentdomain.dto.student.StudentUpdateRequest;
import com.example.studentdomain.entity.Student;
import com.example.studentdomain.exception.exceptions.RecordNotFoundException;
import com.example.studentdomain.mapper.StudentMapper;
import com.example.studentdomain.repository.StudentRepository;
import com.example.studentdomain.service.StudentService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final StudentMapper studentMapper;


    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }


    @Override
    public StudentResponse save(@NonNull StudentSaveRequest studentRequest) {
        Student student = studentMapper.convert(studentRequest);
        Student savedStudent = studentRepository.save(student);
        StudentResponse response = studentMapper.convert(savedStudent);
        return response;
    }

    @Override
    public Set<StudentResponse> findAll() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> response = studentMapper.convert(students);
        return new HashSet<>(response);
    }

    @Override
    public StudentResponse delete(Long id) throws RecordNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                String.format("Student with id:%s is not found", id))
        );
        StudentResponse response = studentMapper.convert(student);
        studentRepository.deleteById(id);
        return response;

    }

    @Override
    public StudentResponse findById(Long id) throws RecordNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                String.format("Student with id:%s is not found", id))
        );
        StudentResponse response = studentMapper.convert(student);
        return response;
    }

    @Override
    public StudentResponse update(StudentUpdateRequest studentUpdateRequest, Long id) throws RecordNotFoundException {
        StudentResponse student = findById(id); // maybe i should use repository here too
        Student dbStudent = studentMapper.convert(student);
        dbStudent.setAge(studentUpdateRequest.getAge());
        StudentResponse response = studentMapper.convert(studentRepository.save(dbStudent));
        return response;
    }
}
