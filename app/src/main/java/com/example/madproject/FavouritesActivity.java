package com.example.madproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONObject;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private LinearLayout cardContainer;
    private final String API_KEY = "68a703a3805ca7ab29eef3513c31a3d3";
    private boolean useCelsius = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        cardContainer = findViewById(R.id.favCardContainer);
        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        // Load unit preference from Settings
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        useCelsius = prefs.getBoolean("useCelsius", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cardContainer.removeAllViews();

        // Fetch city names from DB
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<String> favoriteCities = dbHelper.getFavorites();

        for (String city : favoriteCities) {
            fetchAndDisplayWeather(city);
        }
    }

    private void fetchAndDisplayWeather(String city) {
        String units = useCelsius ? "metric" : "imperial";
        String unitSymbol = useCelsius ? "°C" : "°F";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + API_KEY + "&units=" + units;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        String condition = response.getJSONArray("weather").getJSONObject(0).getString("main");

                        String temp = main.getString("temp") + " " + unitSymbol;
                        String humidity = "Humidity: " + main.getString("humidity") + "%";
                        String windSpeed = "Wind: " + wind.getString("speed") + " km/h";

                        addWeatherCard(city, temp, condition, humidity, windSpeed);
                    } catch (Exception e) {
                        Toast.makeText(this, "Error parsing data for " + city, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Failed to load weather for " + city, Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void addWeatherCard(String city, String temp, String condition, String humidity, String wind) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View card = inflater.inflate(R.layout.weather_card, cardContainer, false);

        ((TextView) card.findViewById(R.id.cardCityName)).setText(city);
        ((TextView) card.findViewById(R.id.cardTemp)).setText(temp);
        ((TextView) card.findViewById(R.id.cardCondition)).setText(condition);
        ((TextView) card.findViewById(R.id.cardHumidity)).setText(humidity);
        ((TextView) card.findViewById(R.id.cardWind)).setText(wind);

        Button removeBtn = card.findViewById(R.id.removeBtn);
        removeBtn.setOnClickListener(v -> {
            DBHelper dbHelper = new DBHelper(this);
            dbHelper.removeFavorite(city);
            cardContainer.removeView(card);
        });

        cardContainer.addView(card);
    }
}
