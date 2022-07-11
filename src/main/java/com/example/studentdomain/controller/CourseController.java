package com.example.studentdomain.controller;

import com.example.studentdomain.dto.course.CourseResponse;
import com.example.studentdomain.dto.course.CourseSaveRequest;
import com.example.studentdomain.entity.Teacher;
import com.example.studentdomain.exception.exceptions.DuplicateRecordException;
import com.example.studentdomain.exception.exceptions.RecordNotFoundException;
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
            CourseResponse response = courseService.findById(id);
            return ResponseEntity.ok().body(response);
    }

    @GetMapping(value="/name/{name}")
    public ResponseEntity<CourseResponse> findByName(@PathVariable String name){
            CourseResponse response = courseService.findByName(name);
            return ResponseEntity.ok().body(response);
    }
    @PostMapping
    public ResponseEntity<CourseResponse> save(@RequestBody CourseSaveRequest courseSaveRequest){
            CourseResponse response = courseService.save(courseSaveRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value="/course/{courseName}/teacher")
    public ResponseEntity<CourseResponse> addTeacher(@PathVariable String courseName,@RequestBody Teacher teacher){
            CourseResponse courseResponse = courseService.addTeacher(courseName, teacher);
            return ResponseEntity.ok().body(courseResponse);
    }

    @DeleteMapping(value="/id/{id}")
    public ResponseEntity<CourseResponse> delete(@PathVariable Long id){
            CourseResponse deleted = courseService.delete(id);
            return ResponseEntity.ok().body(deleted);
    }

    @DeleteMapping(value="/name/{name}")
    public ResponseEntity<CourseResponse> delete(@PathVariable String name){
            CourseResponse deleted = courseService.delete(name);
            return ResponseEntity.ok().body(deleted);
    }
}
