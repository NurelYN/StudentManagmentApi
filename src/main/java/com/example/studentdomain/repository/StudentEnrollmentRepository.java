package com.example.studentdomain.repository;

import com.example.studentdomain.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment,Long> {

    Optional<StudentEnrollment> findByStudentIdAndCourseName(Long id,String name);

    List<StudentEnrollment> findAllByCourseName(String name);

    List<StudentEnrollment> findAllByStudentId(Long id);

}
