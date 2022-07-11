package com.example.studentdomain.controller;
import com.example.studentdomain.dto.teacher.TeacherResponse;
import com.example.studentdomain.dto.teacher.TeacherSaveRequest;
import com.example.studentdomain.dto.teacher.TeacherUpdateRequest;
import com.example.studentdomain.exception.exceptions.RecordNotFoundException;
import com.example.studentdomain.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping(value="/teachers")
public class TeacherController {

    @Autowired
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<Set<TeacherResponse>> findAll(){
        Set<TeacherResponse> teachers = teacherService.findAll();
        return ResponseEntity.ok().body(teachers);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<TeacherResponse> findById(@PathVariable Long id){
            TeacherResponse response = teacherService.findById(id);
            return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> save(@RequestBody TeacherSaveRequest teacherSaveRequest){
        TeacherResponse savedTeacher = teacherService.save(teacherSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<TeacherResponse> deleteById(@PathVariable Long id){
            TeacherResponse deletedTeacher = teacherService.delete(id);
            return ResponseEntity.ok().body(deletedTeacher);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<TeacherResponse> update(@RequestBody TeacherUpdateRequest teacherUpdateRequest, @PathVariable Long id) {
        TeacherResponse updated = teacherService.update(teacherUpdateRequest, id);
        return ResponseEntity.ok().body(updated);
    }

}
