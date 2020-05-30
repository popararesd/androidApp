package com.example.demoapp.models;

import java.util.List;

public class Student extends com.example.demoapp.models.User {
    private String registrationNumber;
    private String identificationNumber;
    private List<Subject> enrolledSubjects;
    private List<Course> attendedCourses;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public List<Subject> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public List<Course> getAttendedCourses() {
        return attendedCourses;
    }
}
