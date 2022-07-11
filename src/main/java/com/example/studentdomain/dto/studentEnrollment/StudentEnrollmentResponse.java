package com.example.studentdomain.dto.studentEnrollment;

import com.example.studentdomain.entity.Course;
import com.example.studentdomain.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEnrollmentResponse {

    private Long id;

    private Course course;

    private Student student;

    private List<Double> grades;

}
