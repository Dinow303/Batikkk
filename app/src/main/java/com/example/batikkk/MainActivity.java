package com.example.batikkk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnIsiData, btnDesain, btnStatusProduksi, btnStatusPengiriman;

    private RecyclerView recyclerViewHighlights;
    private TodayHighlightAdapter highlightAdapter;
    private List<TodayHighlight> highlightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary_dark));

        //Main Button

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

        //Recycler View

        recyclerViewHighlights = findViewById(R.id.recyclerViewHighlights);
        recyclerViewHighlights.setLayoutManager(new LinearLayoutManager(this));

        highlightList = new ArrayList<>();
        highlightList.add(new TodayHighlight("Berita 1", "Deskripsi berita 1", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 2", "Deskripsi berita 2", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 3", "Deskripsi berita 3", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 1", "Deskripsi berita 1", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 2", "Deskripsi berita 2", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 3", "Deskripsi berita 3", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 1", "Deskripsi berita 1", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 2", "Deskripsi berita 2", R.drawable.ic_launcher_foreground));
        highlightList.add(new TodayHighlight("Berita 3", "Deskripsi berita 3", R.drawable.ic_launcher_foreground));


        highlightAdapter = new TodayHighlightAdapter(highlightList);
        recyclerViewHighlights.setAdapter(highlightAdapter);
    }
}
