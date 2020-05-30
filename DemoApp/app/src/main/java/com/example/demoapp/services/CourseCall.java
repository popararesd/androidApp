package com.example.demoapp.services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseCall {
    private final static String API_URL = "http://10.0.2.2:8080/course/";

    private static CourseService webApiService;

    public static CourseService getInstance() {
        if (webApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttp())
                    .build();

            webApiService = retrofit.create(CourseService.class);
        }
        return webApiService;
    }

    private static OkHttpClient provideOkHttp() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(30, TimeUnit.SECONDS);

        return httpBuilder.build();
    }
}
