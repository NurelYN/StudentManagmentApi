package com.example.studentdomain.service.Impl;

import com.example.studentdomain.dto.studentEnrollment.StudentEnrollmentSaveRequest;
import com.example.studentdomain.entity.Course;
import com.example.studentdomain.entity.Student;
import com.example.studentdomain.entity.StudentEnrollment;
import com.example.studentdomain.exception.exceptions.InvalidGradeException;
import com.example.studentdomain.exception.exceptions.RecordNotFoundException;
import com.example.studentdomain.repository.CourseRepository;
import com.example.studentdomain.repository.StudentEnrollmentRepository;
import com.example.studentdomain.repository.StudentRepository;
import com.example.studentdomain.service.StudentEnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;

@Service
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService{

    private final StudentEnrollmentRepository studentEnrollmentRepository;

    private final CourseRepository courseRepository;


    private final StudentRepository studentRepository;

    public StudentEnrollmentServiceImpl(StudentEnrollmentRepository studentEnrollmentRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.courseRepository = courseRepository;

        this.studentRepository = studentRepository;
    }

    @Override
    public StudentEnrollment save(StudentEnrollmentSaveRequest studentEnrollmentSaveRequest) throws RecordNotFoundException{
        Course course;
        Student student;
        try {
           course = courseRepository.findByName(studentEnrollmentSaveRequest.getCourseName()).get();
        } catch (NoSuchElementException ex){
            throw new RecordNotFoundException(
                    String.format("Course with name:%s is not found",studentEnrollmentSaveRequest.getCourseName()));
        }
      try {
         student = studentRepository.findById(studentEnrollmentSaveRequest.getStudentId()).get();
      } catch(NoSuchElementException ex )
      {
          throw new RecordNotFoundException(
                  String.format("Student with id:%s is not found",studentEnrollmentSaveRequest.getStudentId()));
      }


        StudentEnrollment saved = studentEnrollmentRepository.save(StudentEnrollment.builder()
                .student(student)
                .course(course)
                .build());
        return saved;
    }

    @Override
    public StudentEnrollment addGrade(StudentEnrollmentSaveRequest studentEnrollmentSaveRequest, Double grade) {
        StudentEnrollment studentEnrollment = findByStudentIdAndCourseName(studentEnrollmentSaveRequest.getStudentId(), studentEnrollmentSaveRequest.getCourseName());
        if(grade<2 || grade >6){
            throw new InvalidGradeException("Grade should be between 2 and 6");
        }
        studentEnrollment.addGrade(grade);
        studentEnrollmentRepository.save(studentEnrollment);
        return studentEnrollment;
    }

    @Override
    public StudentEnrollment findByStudentIdAndCourseName(Long studentId,String courseName)  {
        return studentEnrollmentRepository.findByStudentIdAndCourseName(studentId,courseName)
                .orElseThrow(()->new RecordNotFoundException("Student or course is not found"));
    }

    @Override
    public Double getAverageForCourse(String name) {
        List<StudentEnrollment> allByCourseName = studentEnrollmentRepository.findAllByCourseName(name);
        OptionalDouble average = allByCourseName.stream().filter(s->s.getAverage()>0.0).mapToDouble(s -> s.getAverage()).average();
        return average.getAsDouble();
    }

    @Override
    public Double getAverageForStudentInAllCourses(Long id) {
        List<StudentEnrollment> allByStudentId = studentEnrollmentRepository.findAllByStudentId(id);
        OptionalDouble average = allByStudentId.stream().filter(s->s.getAverage()>0.01).mapToDouble(s -> s.getAverage()).average();
        return average.getAsDouble();
    }

}
