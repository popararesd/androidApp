package com.example.demoapp.models;

public class AttendaceCode {

    private Long id;
    private String code;
    private Course Course;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public com.example.demoapp.models.Course getCourse() {
        return Course;
    }
}
