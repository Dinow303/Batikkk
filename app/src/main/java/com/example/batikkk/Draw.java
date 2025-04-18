package com.example.batikkk;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Draw extends AppCompatActivity {
    MyCanvasView myCanvasView;
    private Shape Shape;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        myCanvasView = findViewById(R.id.myCanvasView);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        myCanvasView.init(metrics);

        //Tools
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvasView.clear();
                Toast.makeText(Draw.this, "Canvas dibersihkan!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.pen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvasView.pen();
                Toast.makeText(Draw.this, "Mode pena aktif!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCanvasView.eraser();
                Toast.makeText(Draw.this,"Mode Penghapus Aktif", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout brushSizeBtn = findViewById(R.id.brushSize);
        brushSizeBtn.setOnClickListener(v -> showBrushSizeDialog());


    }

    private void showBrushSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ukuran kuas anda");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 24, 32, 0);

        final TextView sizeText = new TextView(this);
        int currentSize = myCanvasView.getBrushSize();
        sizeText.setText("Ukuran: " + currentSize);
        sizeText.setTextColor(Color.WHITE);
        sizeText.setPadding(0, 0, 0, 16);

        final SeekBar seekBar = new SeekBar(this);
        seekBar.setMax(100);
        seekBar.setProgress(currentSize);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 1) progress = 1; // jangan nol
                sizeText.setText("Ukuran: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        layout.addView(sizeText);
        layout.addView(seekBar);

        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            int selectedSize = seekBar.getProgress();
            if (selectedSize < 1) selectedSize = 1;
            myCanvasView.setBrushSize(selectedSize);
            Toast.makeText(this, "Ukuran kuas: " + selectedSize, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

}

