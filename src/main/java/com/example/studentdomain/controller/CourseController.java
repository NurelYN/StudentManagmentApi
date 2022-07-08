package com.example.studentdomain.controller;

import com.example.studentdomain.dto.course.CourseResponse;
import com.example.studentdomain.dto.course.CourseSaveRequest;
import com.example.studentdomain.entity.Teacher;
import com.example.studentdomain.exception.DuplicateRecordException;
import com.example.studentdomain.exception.RecordNotFoundException;
import com.example.studentdomain.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping(value="/courses")
public class CourseController {

    @Autowired
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Set<CourseResponse>> findAll(){
        Set<CourseResponse> response = courseService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value="/id/{id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable Long id){
        try {
            CourseResponse response = courseService.findById(id);
            return ResponseEntity.ok().body(response);
        } catch(RecordNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
        }
    }

    @GetMapping(value="/name/{name}")
    public ResponseEntity<CourseResponse> findByName(@PathVariable String name){
        try {
            CourseResponse response = courseService.findByName(name);
            return ResponseEntity.ok().body(response);
        } catch(RecordNotFoundException ex){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<CourseResponse> save(@RequestBody CourseSaveRequest courseSaveRequest){
        try {
            CourseResponse response = courseService.save(courseSaveRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DuplicateRecordException ex){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,ex.getMessage());
        }
    }

    @PostMapping(value="/course/{courseName}/teacher")
    public ResponseEntity<CourseResponse> addTeacher(@PathVariable String courseName,@RequestBody Teacher teacher){
        try{
            CourseResponse courseResponse = courseService.addTeacher(courseName, teacher);
            return ResponseEntity.ok().body(courseResponse); }
         catch (RecordNotFoundException ex){
             throw  new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
         }
    }

    @DeleteMapping(value="/id/{id}")
    public ResponseEntity<CourseResponse> delete(@PathVariable Long id){
        try {
            CourseResponse deleted = courseService.delete(id);
            return ResponseEntity.ok().body(deleted);
        } catch (RecordNotFoundException ex){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,ex.getMessage());
        }
    }

    @DeleteMapping(value="/name/{name}")
    public ResponseEntity<CourseResponse> delete(@PathVariable String name){
        try {
            CourseResponse deleted = courseService.delete(name);
            return ResponseEntity.ok().body(deleted);
        } catch (RecordNotFoundException ex){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,ex.getMessage());
        }
    }




}
