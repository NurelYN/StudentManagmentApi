package com.example.studentdomain.service.Impl;

import com.example.studentdomain.dto.course.CourseResponse;
import com.example.studentdomain.dto.course.CourseSaveRequest;
import com.example.studentdomain.entity.Course;
import com.example.studentdomain.entity.Teacher;
import com.example.studentdomain.exception.DuplicateRecordException;
import com.example.studentdomain.exception.RecordNotFoundException;
import com.example.studentdomain.mapper.CourseMapper;
import com.example.studentdomain.repository.CourseRepository;
import com.example.studentdomain.service.CourseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseResponse save(@NonNull CourseSaveRequest courseSaveRequest) throws DuplicateRecordException {
        Course course = courseMapper.convert(courseSaveRequest);
        Course savedCourse;
        try {
            savedCourse = courseRepository.save(course);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException(
                    String.format("Course with name:%s already exist", courseSaveRequest.getName()));
        }
        CourseResponse response = courseMapper.convert(savedCourse);
        return response;
    }

    @Override
    public CourseResponse delete(Long id) throws RecordNotFoundException {
        CourseResponse dbCourse = findById(id);
        courseRepository.deleteById(id);
        return dbCourse;
    }

    @Override
    public CourseResponse delete(String name) throws RecordNotFoundException {
        CourseResponse dbCourse = findByName(name);
        courseRepository.deleteByName(name);
        return dbCourse;
    }

    @Override
    public CourseResponse addTeacher(String courseName, Teacher teacher) throws RecordNotFoundException {
        Course dbCourse = courseRepository.findByName(courseName).orElseThrow(() -> new RecordNotFoundException(
                String.format("Course with name:%s is not found", courseName)));
        dbCourse.setTeacher(teacher);
        Course savedCourse = courseRepository.save(dbCourse);
        CourseResponse response = courseMapper.convert(savedCourse);
        return response;
    }

    @Override
    public Set<CourseResponse> findAll() {
        List<CourseResponse> response = courseMapper.convert(courseRepository.findAllByOrderByName());
        return new LinkedHashSet<>(response);
    }

    @Override
    public CourseResponse findById(Long id) throws RecordNotFoundException {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                String.format("Course with id:%s is not found", id)));
        CourseResponse courseResponse = courseMapper.convert(course);
        return courseResponse;
    }

    @Override
    public CourseResponse findByName(String name) throws RecordNotFoundException {
        Course course = courseRepository.findByName(name).orElseThrow(() -> new RecordNotFoundException(
                String.format("Course with name:%s is not found", name)));
        CourseResponse courseResponse = courseMapper.convert(course);
        return courseResponse;
    }
}
