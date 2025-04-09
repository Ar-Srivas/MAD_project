package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnSearch, btnNearby, btnFavourites, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        btnNearby = findViewById(R.id.btnNearby);
        btnFavourites = findViewById(R.id.btnFavourites);
        btnSettings = findViewById(R.id.btnSettings);

        btnSearch.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        btnNearby.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, NearbyWeatherActivity.class)));

        btnFavourites.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, FavouritesActivity.class)));

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }
}
