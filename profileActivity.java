package com.example.ncc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class profileActivity extends AppCompatActivity {

    String platoon, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        platoon = intent.getStringExtra("platoon");
        name = intent.getStringExtra("name");

        TextView username = findViewById(R.id.name);
        TextView userplatoon = findViewById(R.id.platoon);

        username.setText("NAME: " + name);
        userplatoon.setText("PLATOON: " + platoon);
    }
}