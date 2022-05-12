package com.example.embeddedprogrammingassignment.apiclient.Worldometers;

import retrofit2.Call;

public class WorldometersDataController {

    private WorldometersDataService worldometersDataService;

    public WorldometersDataController(WorldometersDataService worldometersDataService) {
        this.worldometersDataService = worldometersDataService;
    }

    public Call<WorldometersData> findAll(boolean isYesterday){
        return worldometersDataService.findAll(isYesterday);
    }
}
