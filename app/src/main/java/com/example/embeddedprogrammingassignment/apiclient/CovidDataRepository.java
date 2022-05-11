package com.example.embeddedprogrammingassignment.apiclient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CovidDataRepository {

    @GET("Malaysia?")
    Call<CovidData> findAll(@Query("yesterday") boolean isYesterday);
}
