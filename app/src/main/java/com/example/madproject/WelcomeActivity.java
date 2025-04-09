package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        MaterialButton continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeActivity.this, SearchActivity.class);
            startActivity(intent);
            finish(); // optional: close welcome screen
        });
    }
}
