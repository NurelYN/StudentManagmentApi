package com.example.studentdomain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@Builder
@Entity
@Table(name="student_enrolment")
public class StudentEnrollment implements Comparable<Object>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonManagedReference
    @JsonIgnore
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonManagedReference
    private Student student;

    @ElementCollection(fetch=FetchType.LAZY)
    private List<Double> grades;

    public StudentEnrollment() {
        grades = new ArrayList<>();
    }

    public void addGrade(Double grade){
        this.grades.add(grade);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEnrollment that = (StudentEnrollment) o;
        return Objects.equals(getAverage(), that.getAverage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAverage());
    }

    public double getAverage(){
        if(this.grades==null){
            return 0.0;
        }
        return this.grades.stream().mapToDouble(Double::doubleValue).summaryStatistics().getAverage();
    }

    @Override
    public int compareTo(Object o) {
        StudentEnrollment other = (StudentEnrollment) o;
        if(this.getAverage()-other.getAverage()==0){
            return 0;
        }
        if(this.getAverage()-other.getAverage()>0){
            return 1;
        } else
            return -1;
    }
}
