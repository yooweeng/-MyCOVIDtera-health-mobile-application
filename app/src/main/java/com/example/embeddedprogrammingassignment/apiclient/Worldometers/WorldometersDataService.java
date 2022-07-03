package com.example.embeddedprogrammingassignment.apiclient.Worldometers;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WorldometersDataService {

    private static Retrofit retrofit;
    // private static String WORLDOMETERS_API_URL = "https://corona.lmao.ninja/v2/countries/";
    private static String WORLDOMETERS_API_URL = "https://disease.sh/v2/countries/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(WORLDOMETERS_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public Call<WorldometersData> findAll(boolean isYesterday){
        WorldometersDataRepository repository = getRetrofitInstance().create(WorldometersDataRepository.class);
        return repository.findAll(isYesterday);
    }
}
