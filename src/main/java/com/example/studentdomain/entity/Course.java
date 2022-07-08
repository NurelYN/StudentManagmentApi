package com.example.studentdomain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private int hours;

    @ManyToOne
    @JoinColumn(name="teacher_id",nullable = true)
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    @JsonBackReference
    private Set<StudentEnrollment> studentsWithGrades;

    public Course(){
        studentsWithGrades=new TreeSet<>();
    }


}
