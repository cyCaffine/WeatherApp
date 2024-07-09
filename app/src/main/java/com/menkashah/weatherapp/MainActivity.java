package com.menkashah.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText etCity;
    private Button btnGetWeather;
    private LinearLayout weatherContainer;
    private TextView tvTemperature, tvWeatherCondition, tvWindSpeed, tvDayMode, tvNightMode;

    private Retrofit retrofit;
    private WeatherService weatherService;
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "f30c07ba4e5d9bdac2ad38943317d49d";
    private static final String UNITS = "metric";


//    f30c07ba4e5d9bdac2ad38943317d49d
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.etCity);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        weatherContainer = findViewById(R.id.weatherContainer);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvDayMode = findViewById(R.id.tvDayMode);
        tvNightMode = findViewById(R.id.tvNightMode);


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherService = retrofit.create(WeatherService.class);


        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = etCity.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    getWeather(cityName);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getWeather(String cityName) {
        Call<WeatherResponse> call = weatherService.getWeather(cityName, API_KEY, UNITS);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    tvTemperature.setText(String.valueOf(weatherResponse.main.temp) + "°C");
                    tvWeatherCondition.setText(weatherResponse.weather.get(0).description);
                    tvWindSpeed.setText(String.valueOf(weatherResponse.wind.speed) + " m/s");

                    // Update Day/Night Mode here if applicable

                    weatherContainer.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get weather data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






    }
}