package com.example.demoapp.models;

import java.util.Date;
import java.util.List;

public class Course {
    private Long courseId;
    private String name;
    private Subject subject;
    private List<Student> attendace;
    private Date date;
    private String attCode;

    public String getAttCode() {
        return attCode;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public Subject getSubject() {
        return subject;
    }

    public List<Student> getAttendace() {
        return attendace;
    }

    public Date getDate() {
        return date;
    }
}
