package com.example.embeddedprogrammingassignment.apiclient.Covid19;

import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Covid19DataRepository {

    @GET("Malaysia?")
    Call<List<Covid19Data>> findAll(@Query("from") String fromDate, @Query("to") String toDate);
}
