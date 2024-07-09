package com.menkashah.weatherapp;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.GET;


    public interface WeatherService {
        @GET("weather")
        Call<WeatherResponse> getWeather(
                @Query("q") String cityName,
                @Query("appid") String apiKey,
                @Query("units") String units
        );
    }

