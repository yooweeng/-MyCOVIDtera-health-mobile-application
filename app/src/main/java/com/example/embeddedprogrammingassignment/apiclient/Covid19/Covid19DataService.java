package com.example.embeddedprogrammingassignment.apiclient.Covid19;

import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersData;
import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersDataRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Covid19DataService {

    private static Retrofit retrofit;
    private static String COVID19_API_URL = "https://api.covid19api.com/country/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(COVID19_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Call<List<Covid19Data>> findAll(String fromDate, String toDate){
        Covid19DataRepository repository = getRetrofitInstance().create(Covid19DataRepository.class);
        return repository.findAll(fromDate,toDate);
    }
}
