package com.example.studentdomain.mapper;

import com.example.studentdomain.dto.teacher.TeacherResponse;
import com.example.studentdomain.dto.teacher.TeacherSaveRequest;
import com.example.studentdomain.entity.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherMapper {

    public Teacher convert(TeacherSaveRequest teacherSaveRequest){
        return Teacher.builder()
                .name(teacherSaveRequest.getName())
                .degree(teacherSaveRequest.getDegree())
                .build();
    }

    public TeacherResponse convert(Teacher teacher){
        return TeacherResponse.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .degree(teacher.getDegree())
                .build();
    }

    public Teacher convert(TeacherResponse teacherResponse){
        return Teacher.builder()
                .id(teacherResponse.getId())
                .name(teacherResponse.getName())
                .degree(teacherResponse.getDegree())
                .build();
    }

    public List<TeacherResponse> convert(List<Teacher> teachers){
        return teachers.stream().map(t->convert(t)).collect(Collectors.toList());
    }
}
