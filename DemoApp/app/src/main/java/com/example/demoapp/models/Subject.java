package com.example.demoapp.models;

import java.util.List;

public class Subject {
    private Long id;
    private int credits;
    private String name;
    private List<Student> enrolledStudents;
    private Professor professor;
    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public int getCredits() {
        return credits;
    }

    public String getName() {
        return name;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Professor getProfessor() {
        return professor;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
