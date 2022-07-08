package com.example.studentdomain.controller;

import com.example.studentdomain.dto.studentEnrollment.StudentEnrollmentSaveRequest;
import com.example.studentdomain.entity.StudentEnrollment;
import com.example.studentdomain.exception.InvalidGradeException;
import com.example.studentdomain.exception.RecordNotFoundException;
import com.example.studentdomain.service.StudentEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value="/student/enrollment")
public class StudentEnrollmentController {

    @Autowired
    private final StudentEnrollmentService studentEnrollmentService;

    public StudentEnrollmentController(StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @PostMapping
    public ResponseEntity<StudentEnrollment> save(@RequestBody StudentEnrollmentSaveRequest studentEnrollmentSaveRequest){ // ? maybe it's better to give in path courseName and in body the full students
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(studentEnrollmentService.save(studentEnrollmentSaveRequest));
        } catch(RecordNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }

    }

    //(value="/course/{courseName}/student/{id}/grade/{grade}") to refactor
    @PostMapping(value="/grade/{grade}")
    public ResponseEntity<StudentEnrollment> addGrade(@RequestBody StudentEnrollmentSaveRequest studentEnrollmentSaveRequest, @PathVariable Double grade){
        try{
            return ResponseEntity.ok().body(studentEnrollmentService.addGrade(studentEnrollmentSaveRequest,grade));
        } catch (RecordNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
        } catch(InvalidGradeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
        }

    }

    @GetMapping(value="/average/course/{courseName}")
    public ResponseEntity<Double> getAverageForCourse(@PathVariable String courseName){
        return ResponseEntity.ok(studentEnrollmentService.getAverageForCourse(courseName));
    }

    @GetMapping(value="average/student/{studentId}")
    public ResponseEntity<Double> getAverageForStudentInAllCourses(@PathVariable Long studentId){
        return ResponseEntity.ok(studentEnrollmentService.getAverageForStudentInAllCourses(studentId));
    }

}
