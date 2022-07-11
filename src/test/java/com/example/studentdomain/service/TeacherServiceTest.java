package com.example.studentdomain.service;

import com.example.studentdomain.dto.teacher.TeacherResponse;
import com.example.studentdomain.dto.teacher.TeacherSaveRequest;
import com.example.studentdomain.entity.Teacher;
import com.example.studentdomain.exception.exceptions.RecordNotFoundException;
import com.example.studentdomain.mapper.TeacherMapper;
import com.example.studentdomain.repository.TeacherRepository;
import com.example.studentdomain.service.Impl.TeacherServiceImpl;
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
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;
    private TeacherServiceImpl teacherServiceImpl;

    @BeforeEach
    public void setUp(){
        teacherServiceImpl=new TeacherServiceImpl(teacherRepository,teacherMapper);
    }

    @Test
    public void verifySave(){
        TeacherSaveRequest teacherSaveRequest = TeacherSaveRequest.builder()
                .name("Ivanka")
                .degree("BSc")
                .build();

        when(teacherMapper.convert(any(TeacherSaveRequest.class)))
                .thenReturn(Teacher.builder().build());
        when(teacherRepository.save(any(Teacher.class)))
                .thenReturn(Teacher.builder().build());
        when(teacherMapper.convert(any(Teacher.class)))
                .thenReturn(TeacherResponse.builder()
                        .id(1L)
                        .name("Ivanka")
                        .degree("BSc")
                        .build());

        TeacherResponse savedTeacher = teacherServiceImpl.save(teacherSaveRequest);
        verify(teacherRepository,times(1)).save(Teacher.builder().build());
        assertAll(
                ()-> assertEquals(savedTeacher.getId(),1L),
                ()->assertTrue(savedTeacher.getDegree().equals("BSc")),
                ()-> assertTrue((savedTeacher.getName().equals("Ivanka"))));
    }

    @Test
    public void verifySaveThrowNullPointerException(){
        assertThrows(NullPointerException.class,()->{
            teacherServiceImpl.save(null);
        });
    }

    @Test
    public void verifyFindAll(){
        teacherServiceImpl.findAll();
        verify(teacherRepository,times(1)).findAll();
    }

    @Test
    public void verifyDelete(){
        when(teacherRepository.findById(1L))
                .thenReturn(Optional.of(Teacher.builder().build()));
        when(teacherMapper.convert(any(Teacher.class)))
                .thenReturn(TeacherResponse.builder()
                        .id(1L)
                        .build());
        TeacherResponse deletedTeacher = teacherServiceImpl.delete(1L);
        verify(teacherRepository,times(1)).deleteById(1L);
        assertEquals(deletedTeacher.getId(),1L);
    }

    @Test
    public void verifyDeleteThrowsRecordNotFoundException(){
        String expectedMessage = "Teacher with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
                teacherServiceImpl.delete(1L);
         });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindById(){
        when(teacherRepository.findById(1L))
                .thenReturn(Optional.of(Teacher.builder().build()));
        when(teacherMapper.convert(any(Teacher.class)))
                .thenReturn(TeacherResponse.builder()
                        .id(1L)
                        .build());
        TeacherResponse response = teacherServiceImpl.findById(1L);
        verify(teacherRepository,times(1)).findById(1L);
        assertEquals(response.getId(),1L);
    }

    @Test
    public void verifyFindByIdThrowsRecordNotFoundException(){
        String expectedMessage= "Teacher with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            teacherServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    //TODO Make Update test for teacher :)
    @Test
    public void verifyUpdate(){

    }
}
