package com.example.batikkk;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FractalActivity extends AppCompatActivity {
    private Bitmap originalBitmap;
    private ImageView fractalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fractal);

        fractalView = findViewById(R.id.fractalView);
        originalBitmap = BitmapStorage.getBitmap();

        if (originalBitmap != null) {
            fractalView.setImageBitmap(originalBitmap);
        }

        Button duplicateRight = findViewById(R.id.duplicateRight);
        Button duplicateUp = findViewById(R.id.duplicateUp);
        Button rotateAndDuplicate = findViewById(R.id.rotateAndDuplicate);
        Button diagonalDuplicate = findViewById(R.id.diagonalDuplicate);
        Button customButton = findViewById(R.id.customButton);

        duplicateRight.setOnClickListener(v -> duplicateImage(2, 1));
        duplicateUp.setOnClickListener(v -> duplicateImage(1, 2));
        rotateAndDuplicate.setOnClickListener(v -> rotateAndDuplicateImage());
        diagonalDuplicate.setOnClickListener(v -> diagonalDuplicateImage());
        customButton.setOnClickListener(v -> {
            Toast.makeText(this, "Custom fitur belum dibuat", Toast.LENGTH_SHORT).show();
        });
    }

    private void duplicateImage(int timesX, int timesY) {
        if (originalBitmap == null) return;

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        Bitmap newBitmap = Bitmap.createBitmap(width * timesX, height * timesY, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);

        for (int x = 0; x < timesX; x++) {
            for (int y = 0; y < timesY; y++) {
                canvas.drawBitmap(originalBitmap, width * x, height * y, null);
            }
        }

        fractalView.setImageBitmap(newBitmap);
    }

    private void rotateAndDuplicateImage() {
        if (originalBitmap == null) return;

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        Bitmap rotatedBitmap = rotateBitmap(originalBitmap, 90);

        Bitmap newBitmap = Bitmap.createBitmap(width, height * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);

        canvas.drawBitmap(originalBitmap, 0, 0, null);
        canvas.drawBitmap(rotatedBitmap, 0, height, null);

        fractalView.setImageBitmap(newBitmap);
    }

    private void diagonalDuplicateImage() {
        if (originalBitmap == null) return;

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        Bitmap newBitmap = Bitmap.createBitmap(width * 2, height * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);

        canvas.drawBitmap(originalBitmap, 0, 0, null);
        canvas.drawBitmap(originalBitmap, width, height, null);

        fractalView.setImageBitmap(newBitmap);
    }

    private Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}