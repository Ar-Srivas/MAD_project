package com.example.madproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchResultActivity extends AppCompatActivity {

    String API_KEY = "68a703a3805ca7ab29eef3513c31a3d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        String city = getIntent().getStringExtra("cityName");

        TextView cityName = findViewById(R.id.cityName);
        TextView temp = findViewById(R.id.temp);
        TextView condition = findViewById(R.id.condition);
        TextView humidity = findViewById(R.id.humidity);
        TextView wind = findViewById(R.id.wind);

        MaterialButton backBtn = findViewById(R.id.backBtn);
        MaterialButton favBtn = findViewById(R.id.favBtn);

        backBtn.setOnClickListener(v -> finish());


        favBtn.setOnClickListener(v -> {
            if (city != null && !city.isEmpty()) {
                DBHelper dbHelper = new DBHelper(this);

                if (dbHelper.isFavorite(city)) {
                    Toast.makeText(this, city + " is already in favourites!", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addFavorite(city);
                    Toast.makeText(this, city + " added to favourites!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + API_KEY + "&units=metric";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        JSONObject windObj = response.getJSONObject("wind");
                        JSONObject weather = response.getJSONArray("weather").getJSONObject(0);

                        cityName.setText(city);
                        temp.setText(main.getString("temp") + " °C");
                        condition.setText(weather.getString("main"));
                        humidity.setText("Humidity: " + main.getString("humidity") + "%");
                        wind.setText("Wind: " + windObj.getString("speed") + " km/h");

                    } catch (Exception e) {
                        Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "City not found", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}
