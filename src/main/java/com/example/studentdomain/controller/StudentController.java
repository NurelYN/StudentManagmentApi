package com.example.studentdomain.controller;


import com.example.studentdomain.dto.student.StudentResponse;
import com.example.studentdomain.dto.student.StudentSaveRequest;
import com.example.studentdomain.dto.student.StudentUpdateRequest;
import com.example.studentdomain.exception.RecordNotFoundException;
import com.example.studentdomain.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping(value="/students")
public class StudentController {

    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Set<StudentResponse>> findAll(){
        Set<StudentResponse> students = studentService.findAll();
        return ResponseEntity.ok().body(students);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        try{
            StudentResponse response = studentService.findById(id);
           return ResponseEntity.ok().body(response);
        } catch(RecordNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<StudentResponse> save(@RequestBody StudentSaveRequest studentRequest){
        StudentResponse savedStudent = studentService.save(studentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<StudentResponse> deleteById(@PathVariable Long id){
        try{
            StudentResponse deletedStudent = studentService.delete(id);
            return ResponseEntity.ok().body(deletedStudent);
        } catch(RecordNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<StudentResponse> update(@RequestBody StudentUpdateRequest studentUpdateRequest, @PathVariable Long id){
        try {
            StudentResponse updated = studentService.update(studentUpdateRequest, id);
            return ResponseEntity.ok().body(updated);
        } catch(RecordNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
        }
    }

//    @ExceptionHandler(RecordNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<String> handleNoSuchElementFoundException(
//            RecordNotFoundException exception
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(exception.getMessage());
//    }

}
