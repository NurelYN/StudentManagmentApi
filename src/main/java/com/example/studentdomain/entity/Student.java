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
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;



    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private Set<StudentEnrollment> studentWithGrades;

    public Student(){
        studentWithGrades= new TreeSet<>();
    }

}
