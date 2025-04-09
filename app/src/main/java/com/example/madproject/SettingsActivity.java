package com.example.madproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    SwitchMaterial unitSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        unitSwitch = findViewById(R.id.unitSwitch);

        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean useCelsius = prefs.getBoolean("useCelsius", true);

        unitSwitch.setChecked(useCelsius);
        unitSwitch.setText(useCelsius ? "Celsius (°C)" : "Fahrenheit (°F)");

        unitSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("useCelsius", isChecked);
            editor.apply();
            unitSwitch.setText(isChecked ? "Celsius (°C)" : "Fahrenheit (°F)");
        });
    }
}
