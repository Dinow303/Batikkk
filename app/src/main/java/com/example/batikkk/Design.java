package com.example.batikkk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Design extends AppCompatActivity {

    private LinearLayout btnScanner, btnGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_design);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary_dark));

        btnScanner = findViewById(R.id.btnScanner);
        btnGambar = findViewById(R.id.btnGambar);

        btnScanner.setOnClickListener(v -> {
            startActivity(new Intent(Design.this, Scanner.class));
        });

        btnGambar.setOnClickListener(v -> {
            startActivity(new Intent(Design.this, Draw.class));
        });
    }
}