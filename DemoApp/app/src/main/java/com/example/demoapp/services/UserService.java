package com.example.demoapp.services;

import com.example.demoapp.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {


    @POST("addUser")
    Call<User> addUser(@Query("type") String type,
                          @Query("first_name") String first,
                          @Query("last_name") String last,
                          @Query("email") String email,
                          @Query("phone") String phone,
                          @Query("rn") String rn,
                          @Query("in") String in,
                          @Query("dep") String dep);
    @GET("findUser")
    Call<User> getUser(@Query("id") String id);

}
