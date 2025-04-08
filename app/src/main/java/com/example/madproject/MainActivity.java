package com.example.madproject;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText cityInput;
    Button searchBtn;
    TextView tempText, conditionText, humidityText, windText;
    String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.cityInput);
        searchBtn = findViewById(R.id.searchBtn);
        tempText = findViewById(R.id.tempText);
        conditionText = findViewById(R.id.conditionText);
        humidityText = findViewById(R.id.humidityText);
        windText = findViewById(R.id.windText);

        searchBtn.setOnClickListener(view -> {
            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                getWeather(city);
            } else {
                Toast.makeText(MainActivity.this, "Enter city name", Toast.LENGTH_SHORT).show();
            }
        });
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

                        tempText.setText(temp);
                        conditionText.setText(condition);
                        humidityText.setText(humidity);
                        windText.setText(windSpeed);

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
