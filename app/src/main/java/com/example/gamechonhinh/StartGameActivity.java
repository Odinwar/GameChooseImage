package com.example.gamechonhinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class StartGameActivity extends AppCompatActivity {
    private ImageButton btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        btnStart = findViewById(R.id.btn_play_game);
        btnStart.setOnClickListener(v -> {
            startActivity(new Intent(StartGameActivity.this, MainActivity.class));
        });
    }
}
