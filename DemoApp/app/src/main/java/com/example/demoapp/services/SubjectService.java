package com.example.demoapp.services;

import com.example.demoapp.models.Account;
import com.example.demoapp.models.Subject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SubjectService {
    @POST("addSubject")
    Call<Subject> addSubject(@Query("name")String name,
                                @Query("cr")String credits,
                                @Query("prof_id")String profId);
    @PUT("addCourse")
    Call<Subject> addCourse(@Query("s_id")String subjectId,
                             @Query("c_id")String courseId);

    @GET("getSubjectByProf")
    Call<Subject> getSubject(@Query("prof_id")String profId);
}
