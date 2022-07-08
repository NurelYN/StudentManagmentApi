package com.example.studentdomain.service;

import com.example.studentdomain.dto.studentEnrollment.StudentEnrollmentSaveRequest;
import com.example.studentdomain.entity.Course;
import com.example.studentdomain.entity.Student;
import com.example.studentdomain.entity.StudentEnrollment;
import com.example.studentdomain.exception.RecordNotFoundException;
import com.example.studentdomain.repository.CourseRepository;
import com.example.studentdomain.repository.StudentEnrollmentRepository;
import com.example.studentdomain.repository.StudentRepository;
import com.example.studentdomain.service.Impl.StudentEnrollmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentEnrollmentServiceTest {


    @Mock
    private StudentEnrollmentRepository studentEnrollmentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentRepository studentRepository;
    private StudentEnrollmentServiceImpl studentEnrollmentServiceImpl;
    private Course course;
    private Student student;
    private List<StudentEnrollment> studentEnrollmentList;
    private StudentEnrollmentSaveRequest studentEnrollmentSaveRequest;

    @BeforeEach
    public void setUp() {
        studentEnrollmentServiceImpl = new StudentEnrollmentServiceImpl(studentEnrollmentRepository, courseRepository, studentRepository);

        course = Course.builder()
                .id(1L)
                .name("Java")
                .hours(200)
                .build();

        student = Student.builder()
                .id(1L)
                .name("Nurel")
                .age(22)
                .build();

        List<Double> grades = new ArrayList<>();
        grades.add(5.0);
        grades.add(6.0);
        StudentEnrollment studentEnrollment = StudentEnrollment.builder()
                .grades(grades)
                .build();
        studentEnrollmentList = new ArrayList<>();
        studentEnrollmentList.add(studentEnrollment);

        studentEnrollmentSaveRequest = StudentEnrollmentSaveRequest.builder()
                .courseName("Java")
                .studentId(1L)
                .build();
    }

    @Test
    public void verifySave() {

        when(courseRepository.findByName("Java"))
                .thenReturn(Optional.of(Course.builder().build()));
        when(studentRepository.findById(1L))
                .thenReturn((Optional.of(Student.builder().build())));
        when(studentEnrollmentRepository.save(any(StudentEnrollment.class)))
                .thenReturn(StudentEnrollment.builder()
                        .course(course)
                        .student(student)
                        .build());
        StudentEnrollment saved = studentEnrollmentServiceImpl.save(studentEnrollmentSaveRequest);
        verify(studentEnrollmentRepository, times(1)).save(StudentEnrollment.builder().build());
        assertAll(
                () -> assertEquals(saved.getStudent().getId(), student.getId()),
                () -> assertTrue(saved.getCourse().getName().equals(course.getName()))
        );
    }

    @Test
    public void verifySaveThrowsRecordNotFoundExceptionWhenStudentNotExist() {
        String expectedMessage = "Student with id:1 is not found";
        when(courseRepository.findByName("Java"))
                .thenReturn(Optional.of(Course.builder().build()));

        Throwable ex = assertThrows(RecordNotFoundException.class, () -> {
            studentEnrollmentServiceImpl.save(studentEnrollmentSaveRequest);
        });
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void verifySaveThrowsRecordNotFoundExceptionWhenCourseNotExist() {
        String expectedMessage = "Course with name:Java is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class, () -> {
            studentEnrollmentServiceImpl.save(studentEnrollmentSaveRequest);
        });
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void verifyFindByStudentIdAndCourseName() {
        StudentEnrollment studentEnrollment = StudentEnrollment.builder()
                .course(course)
                .student(student)
                .build();

        when(studentEnrollmentRepository.findByStudentIdAndCourseName(1L, "Java"))
                .thenReturn(Optional.ofNullable(studentEnrollment));
        StudentEnrollment result = studentEnrollmentServiceImpl.findByStudentIdAndCourseName(1L, "Java");
        verify(studentEnrollmentRepository, times(1)).findByStudentIdAndCourseName(1L, "Java");
        assertAll(
                () -> assertEquals(studentEnrollment.getStudent().getId(), result.getStudent().getId()),
                () -> assertEquals(studentEnrollment.getCourse().getName(), result.getCourse().getName())
        );
    }

    @Test
    public void verifyFindByStudentIdAndCourseNameThrowsRecordNotFoundException() {
        String expectedMessage = "Student or course is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class, () -> {
            studentEnrollmentServiceImpl.findByStudentIdAndCourseName(1L, "Java");
        });
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    public void verifyGetAverageForCourse() {
        when(studentEnrollmentRepository.findAllByCourseName("Java"))
                .thenReturn(studentEnrollmentList);
        Double result = studentEnrollmentServiceImpl.getAverageForCourse("Java");
        assertEquals(result, 5.5);
    }

    @Test
    public void verifyGetAverageForStudent() {
        when(studentEnrollmentRepository.findAllByStudentId(1L))
                .thenReturn(studentEnrollmentList);
        Double result = studentEnrollmentServiceImpl.getAverageForStudentInAllCourses(1L);
        assertEquals(result, 5.5);
    }


}
