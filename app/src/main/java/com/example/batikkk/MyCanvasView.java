package com.example.batikkk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyCanvasView extends View {

    private int currentBrushSize = 15;
    public static final int COLOR_PEN = Color.BLACK;
    public static final int COLOR_ERASER =  Color.WHITE;
    public static final int BACKGROUND_COLOR =  Color.WHITE;
    public static final int TOUCH_TOLERANCE = 4;

    private float mx, my;
    private Path mPath;
    private Paint mPaint;
    private int currentColor;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private boolean isEraserOn = false;

    public MyCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(COLOR_PEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }


    public void init(DisplayMetrics metrics){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = COLOR_PEN;
    }
    public void pen(){
        currentColor = COLOR_PEN;
        isEraserOn = false;
    }

    public void eraser(){
        currentColor = COLOR_ERASER;
        isEraserOn = true;
    }
    public void setBrushSize(int size) {
        this.currentBrushSize = size;
    }

    public int getBrushSize() {
        return currentBrushSize;
    }



    public void clear(){
        Log.d("MyCanvasView", "clear() dipanggil!");

        if (mCanvas == null) {
            Log.e("MyCanvasView", "Canvas belum diinisialisasi!");
            return;
        }


        paths.clear();
        mCanvas.drawColor(Color.WHITE);
        pen();
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(BACKGROUND_COLOR);

        for (FingerPath fp : paths){
            mPaint.setColor(fp.getColor());
            mPaint.setStrokeWidth(fp.getStrokeWidth());
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(fp.getPath(), mPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }
    private void touchStart(float x, float y){
        mPath = new Path();
        int color = isEraserOn ? COLOR_ERASER : currentColor;
        FingerPath fp = new FingerPath(currentColor, currentBrushSize, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mx = x;
        my = y;
    }

    private void touchMove(float x, float y){
        float dx = Math.abs(x - mx);
        float dy = Math.abs(y - my);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mx, my, (x + mx) / 2, (y + my) / 2);
            mx = x;
            my = y;
        }
    }

    private void touchUp(){
        mPath.lineTo(mx, my);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }
}
