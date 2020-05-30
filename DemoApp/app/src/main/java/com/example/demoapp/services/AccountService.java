package com.example.demoapp.services;

import com.example.demoapp.models.Account;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountService {
    @POST("createAccount")
    Call<Account> createAccount(@Query("user_id")String userId,
                                @Query("pass")String pass,
                                @Query("pass_confirm")String confirmPass);
    @GET("validateLogin")
    Call<Account> checkLogin(@Query("email")String email,
                             @Query("pass")String pass);
}
