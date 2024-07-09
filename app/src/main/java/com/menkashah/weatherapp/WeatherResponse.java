package com.menkashah.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    public Main main;
    @SerializedName("weather")
    public List<Weather> weather;
    @SerializedName("wind")
    public Wind wind;

    public class Main {
        @SerializedName("temp")
        public float temp;
    }

    public class Weather {
        @SerializedName("description")
        public String description;
    }

    public class Wind {
        @SerializedName("speed")
        public float speed;
    }
}
