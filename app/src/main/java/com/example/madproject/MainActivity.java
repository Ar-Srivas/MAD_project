package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    LinearLayout cardContainer;
    String API_KEY = "68a703a3805ca7ab29eef3513c31a3d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardContainer = findViewById(R.id.cardContainer);

        findViewById(R.id.searchBtn).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        });

        String city = getIntent().getStringExtra("cityName");
        if (city != null && !city.isEmpty()) {
            getWeather(city);
        }
    }

    private void getWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + API_KEY + "&units=metric";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        String condition = response.getJSONArray("weather").getJSONObject(0).getString("main");

                        String temp = main.getString("temp") + " °C";
                        String humidity = "Humidity: " + main.getString("humidity") + "%";
                        String windSpeed = "Wind: " + wind.getString("speed") + " km/h";

                        addWeatherCard(city, temp, condition, humidity, windSpeed);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void addWeatherCard(String city, String temp, String condition, String humidity, String wind) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View card = inflater.inflate(R.layout.weather_card, cardContainer, false);

        ((TextView) card.findViewById(R.id.cardCityName)).setText(city);
        ((TextView) card.findViewById(R.id.cardTemp)).setText(temp);
        ((TextView) card.findViewById(R.id.cardCondition)).setText(condition);
        ((TextView) card.findViewById(R.id.cardHumidity)).setText(humidity);
        ((TextView) card.findViewById(R.id.cardWind)).setText(wind);

        cardContainer.addView(card);
    }
}
