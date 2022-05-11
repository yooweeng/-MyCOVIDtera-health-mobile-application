package com.example.embeddedprogrammingassignment.apiclient;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CovidDataController {

    private CovidDataService covidDataService;

    public CovidDataController(CovidDataService covidDataService) {
        this.covidDataService = covidDataService;
    }

    public Call<CovidData> findAll(boolean isYesterday){
        return covidDataService.findAll(isYesterday);
    }
}
