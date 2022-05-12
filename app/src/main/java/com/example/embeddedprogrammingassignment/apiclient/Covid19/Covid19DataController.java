package com.example.embeddedprogrammingassignment.apiclient.Covid19;

import java.util.List;

import retrofit2.Call;

public class Covid19DataController {

    private Covid19DataService covid19DataService;

    public Covid19DataController(Covid19DataService covid19DataService) {
        this.covid19DataService = covid19DataService;
    }

    public Call<List<Covid19Data>> findAll(String fromDate, String toDate){
        return covid19DataService.findAll(fromDate,toDate);
    }
}
