package com.example.batikkk;

import android.graphics.Bitmap;

public class BitmapStorage {
    private static Bitmap bitmap;

    public static void setBitmap(Bitmap bmp) {
        bitmap = bmp;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }
}

