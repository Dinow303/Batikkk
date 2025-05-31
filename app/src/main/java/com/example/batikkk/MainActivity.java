package com.example.batikkk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnIsiData, btnDesain, btnStatusProduksi, btnStatusPengiriman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary_dark));

        btnIsiData = findViewById(R.id.btnIsiData);
        btnDesain = findViewById(R.id.btnDesain);
        btnStatusProduksi = findViewById(R.id.btnStatusProduksi);
        btnStatusPengiriman = findViewById(R.id.btnStatusPengiriman);

        btnIsiData.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, IsiData.class));
        });

        btnDesain.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Design.class));
        });

        btnStatusProduksi.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StatusProduksi.class));
        });

        btnStatusPengiriman.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StatusPengiriman.class));
        });
    }
}
