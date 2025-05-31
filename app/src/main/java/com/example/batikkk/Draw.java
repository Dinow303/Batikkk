package com.example.batikkk;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class Draw extends AppCompatActivity {
    MyCanvasView myCanvasView;
    private LinearLayout Shapes;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);


        myCanvasView = findViewById(R.id.myCanvasView);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        myCanvasView.init(metrics);

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary_dark));

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvasView.clearCanvas();
                Toast.makeText(Draw.this, "Canvas dibersihkan!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.pen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCanvasView.setToolMode(MyCanvasView.MODE_PEN);
                myCanvasView.setBrushSize(15);
                myCanvasView.pen();
                Toast.makeText(Draw.this, "Mode pena aktif!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCanvasView.setToolMode(MyCanvasView.MODE_ERASER);
                myCanvasView.setBrushSize(50);
                myCanvasView.eraser();
                Toast.makeText(Draw.this,"Mode Penghapus Aktif", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById((R.id.undo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                myCanvasView.undo();
            }
        });

        findViewById((R.id.redo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View view) {
                myCanvasView.redo();
            }
        });

        LinearLayout saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(v -> {
            Bitmap bitmap = myCanvasView.getBitmap();

            // Simpan bitmap sementara
            BitmapStorage.setBitmap(bitmap);

            // Buka FractalActivity
            Intent intent = new Intent(Draw.this, FractalActivity.class);
            startActivity(intent);
        });



        LinearLayout brushSizeBtn = findViewById(R.id.brushSize);
        brushSizeBtn.setOnClickListener(v -> showBrushSizeDialog());


        Shapes = findViewById(R.id.shapes);
        Shapes.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.shape_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.shape_line) {
                    myCanvasView.setToolMode(MyCanvasView.MODE_LINE);
                    myCanvasView.setBrushSize(15);
                    myCanvasView.shapeLine();
                    Toast.makeText(this, "Mode: Garis", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.shape_rect) {
                    myCanvasView.setToolMode(MyCanvasView.MODE_RECT);
                    myCanvasView.setBrushSize(15);
                    myCanvasView.shapeRect();
                    Toast.makeText(this, "Mode: Kotak", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.shape_circle) {
                    myCanvasView.setToolMode(MyCanvasView.MODE_CIRCLE);
                    myCanvasView.setBrushSize(15);
                    myCanvasView.shapeCircle();
                    Toast.makeText(this, "Mode: Lingkaran", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });
            popup.show();
        });


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
                if (progress < 1) progress = 1;
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

