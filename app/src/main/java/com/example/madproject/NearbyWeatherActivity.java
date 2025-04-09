package com.example.madproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class NearbyWeatherActivity extends AppCompatActivity {

    private TextView tempText, conditionText, cityText, humidityText, windText;
    private final String API_KEY = "68a703a3805ca7ab29eef3513c31a3d3";
    private boolean useCelsius = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_weather);

        tempText = findViewById(R.id.tempText);
        conditionText = findViewById(R.id.conditionText);
        cityText = findViewById(R.id.cityText);
        humidityText = findViewById(R.id.humidityText);
        windText = findViewById(R.id.windText);

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        useCelsius = prefs.getBoolean("useCelsius", true);

        fetchWeatherForMumbai();
    }

    private void fetchWeatherForMumbai() {
        String units = useCelsius ? "metric" : "imperial";
        String symbol = useCelsius ? "°C" : "°F";

        String url = "https://api.openweathermap.org/data/2.5/weather?q=Mumbai&appid=" + API_KEY + "&units=" + units;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);

                        String city = response.getString("name");
                        String temp = main.getString("temp") + symbol;
                        String condition = weather.getString("main");
                        String humidity = main.getString("humidity") + "%";
                        String windSpeed = wind.getString("speed") + " km/h";

                        cityText.setText(city);
                        tempText.setText("Temp: " + temp);
                        conditionText.setText("Condition: " + condition);
                        humidityText.setText("Humidity: " + humidity);
                        windText.setText("Wind: " + windSpeed);

                    } catch (Exception e) {
                        showHardcodedFallback();
                    }
                },
                error -> showHardcodedFallback()
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void showHardcodedFallback() {
        cityText.setText("Mumbai");
        tempText.setText("Temp: 30°C");
        conditionText.setText("Condition: Clear");
        humidityText.setText("Humidity: 60%");
        windText.setText("Wind: 10 km/h");
        Toast.makeText(this, "API unavailable, showing fallback data.", Toast.LENGTH_SHORT).show();
    }
}
