package com.example.batikkk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnIsiData, btnDesain, btnStatusProduksi, btnStatusPengiriman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIsiData = (LinearLayout)findViewById(R.id.btnIsiData);
        btnDesain = (LinearLayout)findViewById(R.id.btnDesain);
        btnStatusProduksi = (LinearLayout)findViewById(R.id.btnStatusProduksi);
        btnStatusPengiriman = (LinearLayout)findViewById(R.id.btnStatusPengiriman);

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
