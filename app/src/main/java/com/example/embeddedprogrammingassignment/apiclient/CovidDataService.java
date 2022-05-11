package com.example.embeddedprogrammingassignment.apiclient;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidDataService {

    private static Retrofit retrofit;
    private static String WORLDOMETERS_API_URL = "https://corona.lmao.ninja/v2/countries/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(WORLDOMETERS_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Call<CovidData> findAll(boolean isYesterday){
        CovidDataRepository repository = getRetrofitInstance().create(CovidDataRepository.class);
        return repository.findAll(isYesterday);
    }
}
