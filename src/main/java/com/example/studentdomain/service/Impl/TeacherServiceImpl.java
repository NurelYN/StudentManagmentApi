package com.example.studentdomain.service.Impl;

import com.example.studentdomain.dto.teacher.TeacherResponse;
import com.example.studentdomain.dto.teacher.TeacherSaveRequest;
import com.example.studentdomain.dto.teacher.TeacherUpdateRequest;
import com.example.studentdomain.entity.Teacher;
import com.example.studentdomain.exception.RecordNotFoundException;
import com.example.studentdomain.mapper.TeacherMapper;
import com.example.studentdomain.repository.TeacherRepository;
import com.example.studentdomain.service.TeacherService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private final TeacherRepository teacherRepository;

    @Autowired
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherResponse save(@NonNull TeacherSaveRequest teacherSaveRequest) {
        Teacher teacher = teacherMapper.convert(teacherSaveRequest);
        Teacher teacherSaved = teacherRepository.save(teacher);
        TeacherResponse response = teacherMapper.convert(teacherSaved);
        return response;
    }

    @Override
    public Set<TeacherResponse> findAll() {
        List<TeacherResponse> response = teacherMapper.convert(teacherRepository.findAll());
        return new HashSet<>(response);
    }

    @Override
    public TeacherResponse delete(Long id) throws RecordNotFoundException { // may be this can be reworked to delete by given object
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                String.format("Teacher with id:%s is not found", id)));
        TeacherResponse response = teacherMapper.convert(teacher);
        teacherRepository.deleteById(id);
        return response;
    }

    @Override
    public TeacherResponse findById(Long id) throws RecordNotFoundException {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                String.format("Teacher with id:%s is not found", id)));
        TeacherResponse response = teacherMapper.convert(teacher);
        return response;
    }
    @Override
    public TeacherResponse update(TeacherUpdateRequest teacherUpdateRequest, Long id) throws RecordNotFoundException {
        TeacherResponse teacher = findById(id);
        Teacher dbTeacher = teacherMapper.convert(teacher);
        dbTeacher.setDegree(teacherUpdateRequest.getDegree());
        TeacherResponse response = teacherMapper.convert(teacherRepository.save(dbTeacher));
        return response;
    }
}
