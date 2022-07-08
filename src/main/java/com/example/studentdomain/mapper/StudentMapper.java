package com.example.studentdomain.mapper;

import com.example.studentdomain.dto.student.StudentResponse;
import com.example.studentdomain.dto.student.StudentSaveRequest;
import com.example.studentdomain.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public Student convert(StudentSaveRequest studentRequest){
        return Student.builder()
                .name(studentRequest.getName())
                .age(studentRequest.getAge())
                .build();
    }

    public StudentResponse convert(Student student){
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .build();
    }

    public Student convert(StudentResponse studentResponse){
        return Student.builder()
                .id(studentResponse.getId())
                .name(studentResponse.getName())
                .age(studentResponse.getAge())
                .build();
    }

    public List<StudentResponse> convert(List<Student> students){
        return students.stream().map(s->convert(s)).collect(Collectors.toList());
    }

}
