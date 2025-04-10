package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText cityInput = findViewById(R.id.cityInput);
        MaterialButton searchButton = findViewById(R.id.searchButton);


        searchButton.setOnClickListener(view -> {
            String city = cityInput.getText().toString().trim();
            if (!city.isEmpty()) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("cityName", city);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter a city", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
