package com.example.studentdomain.service;


import com.example.studentdomain.dto.student.StudentResponse;
import com.example.studentdomain.dto.student.StudentSaveRequest;
import com.example.studentdomain.entity.Student;
import com.example.studentdomain.exception.RecordNotFoundException;
import com.example.studentdomain.mapper.StudentMapper;
import com.example.studentdomain.repository.StudentRepository;
import com.example.studentdomain.service.Impl.StudentServiceImpl;
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
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentServiceImpl studentServiceImpl;

    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    public void setUp(){
        studentServiceImpl = new StudentServiceImpl(studentRepository,studentMapper);
    }

    @Test
    public void verifySave(){
        StudentSaveRequest student = StudentSaveRequest.builder()
                .name("Nurel")
                .age(22)
                .build();

        when(studentMapper.convert(any(StudentSaveRequest.class)))
                .thenReturn(Student.builder().build());
        when(studentRepository.save(any(Student.class)))
                .thenReturn(Student.builder().build());
        when(studentMapper.convert(any(Student.class)))
                .thenReturn(StudentResponse.builder()
                        .id(1L)
                        .name("Nurel")
                        .age(22)
                        .build());

        StudentResponse savedStudent = studentServiceImpl.save(student);

        verify(studentRepository,times(1)).save(Student.builder().build());
        assertAll(
                ()-> assertEquals(savedStudent.getId(),1L),
                ()-> assertEquals(savedStudent.getAge(),22),
                ()-> assertTrue((savedStudent.getName().equals("Nurel"))));
    }

    @Test
    public void verifySaveThrowNullPointerException(){
        assertThrows(NullPointerException.class,()->{
            studentServiceImpl.save(null);
        });
    }

    @Test
    public void verifyFindAll(){
        studentServiceImpl.findAll();
        verify(studentRepository,times(1)).findAll();
    }

    @Test
    public void verifyDelete(){
      when(studentRepository.findById(1L))
              .thenReturn(Optional.of(Student.builder().build()));
      when(studentMapper.convert(any(Student.class))).thenReturn(StudentResponse.builder()
              .id(1L)
              .build());
        StudentResponse deleted = studentServiceImpl.delete(1L);
        verify(studentRepository,times(1)).deleteById(1L);
        assertEquals(deleted.getId(),1L);
    }

    @Test
    public void verifyDeleteThrowsRecordNotFoundException(){
        String expectedMessage= "Student with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            studentServiceImpl.delete(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    @Test
    public void verifyFindById(){
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(Student.builder().build()));
        when(studentMapper.convert(any(Student.class)))
                .thenReturn(StudentResponse.builder()
                        .id(1L)
                        .build());
        StudentResponse response = studentServiceImpl.findById(1L);
        verify(studentRepository,times(1)).findById(1L);
        assertEquals(response.getId(),1L);
    }

    @Test
    public void verifyFindByIdThrowsRecordNotFoundException(){
        String expectedMessage= "Student with id:1 is not found";
        Throwable ex = assertThrows(RecordNotFoundException.class,()->{
            studentServiceImpl.findById(1L);
        });
        assertEquals(expectedMessage,ex.getMessage());
    }

    //TODO Make Update test for student :)
    @Test
    public void verifyUpdate(){

    }
}
