package com.example.studentdomain.service;

import com.example.studentdomain.dto.course.CourseResponse;
import com.example.studentdomain.dto.course.CourseSaveRequest;
import com.example.studentdomain.entity.Course;
import com.example.studentdomain.entity.Teacher;
import com.example.studentdomain.exception.exceptions.RecordNotFoundException;
import com.example.studentdomain.mapper.CourseMapper;
import com.example.studentdomain.repository.CourseRepository;
import com.example.studentdomain.service.Impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    private CourseServiceImpl courseServiceImpl;

    @BeforeEach
    public void setUp(){
        courseServiceImpl = new CourseServiceImpl(courseRepository,courseMapper);
    }

    @Test
    public void verifySave(){

        CourseSaveRequest courseSaveRequest = CourseSaveRequest.builder()
                .name("Java")
                .hours(200)
                .build();

        when(courseMapper.convert(any(CourseSaveRequest.class)))
                .thenReturn(Course.builder().build());
        when(courseRepository.save(any(Course.class)))
                .thenReturn(Course.builder().build());
        when(courseMapper.convert(any(Course.class)))
                .thenReturn(CourseResponse.builder()
                        .id(1L)
                        .name("Java")
                        .hours(200)
                        .build());
        CourseResponse savedCourse = courseServiceImpl.save(courseSaveRequest);
        verify(courseRepository,times(1)).save(Course.builder().build());
        assertAll(
                ()->assertEquals(savedCourse.getId(),1L),
                ()->assertEquals(savedCourse.getHours(),200),
                ()->assertTrue(savedCourse.getName().equals("Java"))
        );
    }

    @Test
    public void verifySaveThrowNullPointerException(){
        assertThrows(NullPointerException.class,()->{
            courseServiceImpl.save(null);
        });
    }


    @Test
    public void verifyDeleteById(){
        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(Course.builder().build()));
        when(courseMapper.convert(any(Course.class))).thenReturn(CourseResponse.builder()
                .id(1L)
                .build());
        CourseResponse deleted = courseServiceImpl.delete(1L);
        verify(courseRepository,times(1)).deleteById(1L);
        assertEquals(deleted.getId(),1L);
    }

    @Test
    public void verifyDeleteByName(){
        String name = "Java";
        when(courseRepository.findByName(name))
                .thenReturn(Optional.of(Course.builder().build()));
        when(courseMapper.convert(any(Course.class))).thenReturn(CourseResponse.builder()
                .name("Java")
                .build());
        CourseResponse deleted = courseServiceImpl.delete(name);
        verify(courseRepository,times(1)).deleteByName(name);
        assertTrue(deleted.getName().equals(name));
    }

    @Test
    public void verifyAddTeacher(){
        String courseName = "Java";
        Teacher teacher = Teacher.builder()
                .id(1L)
                .name("Ivanka")
                .build();
        when(courseRepository.findByName(courseName))
                .thenReturn(Optional.of(Course.builder().build()));
        when(courseRepository.save(any(Course.class)))
                .thenReturn(Course.builder().build());
        when(courseMapper.convert(any(Course.class)))
                .thenReturn(CourseResponse.builder()
                        .name("Java")
                        .teacher(teacher)
                        .build());
        CourseResponse courseResponse = courseServiceImpl.addTeacher(courseName, teacher);

        assertEquals(courseResponse.getTeacher().getId(),teacher.getId());
    }

    @Test
    public void verifyFindAll(){
        courseServiceImpl.findAll();
        verify(courseRepository,times(1)).findAllByOrderByName();
    }

    @Test
    public void verifyFindById(){
        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(Course.builder().build()));
        when(courseMapper.convert(any(Course.class)))
                .thenReturn(CourseResponse.builder()
                        .id(1L)
                        .build());
        CourseResponse response = courseServiceImpl.findById(1L);
        verify(courseRepository,times(1)).findById(1L);
        assertEquals(response.getId(),1L);
    }

    @Test
    public void verifyFindByIdThrowsRecordNotFoundException(){
        String expectedMessage= "Course with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            courseServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindByName(){
        String name = "Java";
        when(courseRepository.findByName(name))
                .thenReturn(Optional.of(Course.builder().build()));
        when(courseMapper.convert(any(Course.class)))
                .thenReturn(CourseResponse.builder()
                        .id(1L)
                        .name(name)
                        .build());
        CourseResponse response = courseServiceImpl.findByName(name);
        verify(courseRepository,times(1)).findByName(name);
        assertEquals(response.getName(),name);
    }

    @Test
    public void verifyFindByNameThrowsRecordNotFoundException(){
        String expectedMessage= "Course with name:Java is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            courseServiceImpl.findByName("Java");
        });
        assertEquals(expectedMessage,ex.getMessage());
    }
}
