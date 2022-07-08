package com.example.studentdomain.mapper;

import com.example.studentdomain.dto.course.CourseResponse;
import com.example.studentdomain.dto.course.CourseSaveRequest;
import com.example.studentdomain.entity.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course convert(CourseSaveRequest courseSaveRequest){
        return Course.builder()
                .name(courseSaveRequest.getName())
                .hours(courseSaveRequest.getHours())
                .build();
    }

    public CourseResponse convert(Course course){
       return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .teacher(course.getTeacher())
               .studentWithGrades(course.getStudentsWithGrades())
                .hours(course.getHours())
                .build();
    }

    public Course convert(CourseResponse courseResponse){
        return Course.builder()
                .id(courseResponse.getId())
                .name(courseResponse.getName())
                .teacher(courseResponse.getTeacher())
                .hours(courseResponse.getHours())
                .studentsWithGrades(courseResponse.getStudentWithGrades())
                .build();
    }

    public List<CourseResponse> convert(List<Course> courses){
        return courses.stream().map((s)->convert(s)).collect(Collectors.toList());
    }


}
