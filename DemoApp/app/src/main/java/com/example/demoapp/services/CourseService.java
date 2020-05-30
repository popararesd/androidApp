package com.example.demoapp.services;

import com.example.demoapp.models.Account;
import com.example.demoapp.models.Course;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CourseService {
    @POST("addCourse")
    Call<Course> addCourse(@Query("name")String name,
                           @Query("day")String dat,
                           @Query("mon")String month,
                           @Query("year")String year);
    @PUT("markAttendance")
    Call<Course> markAttendance(@Query("code")String code,
                             @Query("s_id")String studentId);
}
