package com.example.studentdomain.repository;


import com.example.studentdomain.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    Optional<Course> findByName(String name);

    void deleteByName(String name);

    List<Course> findAllByOrderByName();
}
