package com.example.demoapp.services;

import com.example.demoapp.models.AttendaceCode;
import com.example.demoapp.models.Course;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AttendaceCodeService {
    @POST("createCode")
    Call<AttendaceCode> createCode(@Query("course_id")String courseId);
    @GET("getCourseByCode")
    Call<Course> getCourseByCode(@Query("code")String code);
    @GET("findByCourse")
    Call<AttendaceCode> findByCourse(@Query("c_id") String courseId);
}
