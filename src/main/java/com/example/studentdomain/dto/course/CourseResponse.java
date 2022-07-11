package com.example.studentdomain.dto.course;

import com.example.studentdomain.entity.StudentEnrollment;
import com.example.studentdomain.entity.Teacher;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {

    private Long id ;

    private String name;

    private int hours;

    private Teacher teacher;

    private Set<StudentEnrollment> studentWithGrades;

    @Override
    public String toString() {
        return "CourseResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hours=" + hours +
                ", teacher=" + teacher +
                ", studentWithGrades=" + studentWithGrades +
                '}';
    }
}
