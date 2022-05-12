package com.example.embeddedprogrammingassignment.apiclient.Worldometers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WorldometersDataRepository {

    @GET("Malaysia?")
    Call<WorldometersData> findAll(@Query("yesterday") boolean isYesterday);
}
