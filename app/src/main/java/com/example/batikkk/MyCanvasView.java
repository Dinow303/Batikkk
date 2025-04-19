package com.example.batikkk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class MyCanvasView extends View {

    public static final int MODE_PEN = 0;
    public static final int MODE_ERASER = 1;
    public static final int MODE_LINE = 2;
    public static final int MODE_RECT = 3;
    public static final int MODE_CIRCLE = 4;

    private int currentMode = MODE_PEN;
    private int currentColor = COLOR_PEN;
    private int currentBrushSize = 15;

    public static final int COLOR_PEN = Color.BLACK;
    public static final int COLOR_ERASER = Color.WHITE;
    public static final int BACKGROUND_COLOR = Color.WHITE;

    private ArrayList<FingerPath> paths = new ArrayList<>();
    private List<DrawPath> undonePaths = new ArrayList<>();
    private Path mPath;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);


    private float startX, startY;
    private float mx, my;
    private float cursorX = -1, cursorY = -1;
    private boolean showCursor = false;
    // Titik awal dan akhir untuk bentuk
    private PointF startPoint = new PointF();
    private boolean isDrawingShape = false;

    public MyCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
    }

    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(currentColor);
        mPaint.setStrokeWidth(currentBrushSize);
    }

    public void init(DisplayMetrics metrics) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        clearCanvas();
    }

    public void setBrushSize(int size) {
        currentBrushSize = size;
    }

    public int getBrushSize() {
        return currentBrushSize;
    }

    public void pen() {
        currentMode = MODE_PEN;
        currentColor = COLOR_PEN;
    }

    public void eraser() {
        currentMode = MODE_ERASER;
        currentColor = COLOR_ERASER;
    }

    public void shapeLine() {
        currentMode = MODE_LINE;
    }

    public void shapeRect() {
        currentMode = MODE_RECT;
    }

    public void shapeCircle() {
        currentMode = MODE_CIRCLE;
    }

    public void clearCanvas() {
        paths.clear();
        mCanvas.drawColor(BACKGROUND_COLOR);
        pen();
        invalidate();
    }

    public enum Tool {
        PEN, ERASER, RECT, CIRCLE, LINE
    }

    private Tool currentTool = Tool.PEN;

    public void setTool(Tool tool) {
        currentTool = tool;
        switch (tool) {
            case PEN:
                pen();
                break;
            case ERASER:
                eraser();
                break;
            case LINE:
                shapeLine();
                break;
            case RECT:
                shapeRect();
                break;
            case CIRCLE:
                shapeCircle();
                break;
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawColor(BACKGROUND_COLOR);
        for (FingerPath fp : paths) {
            mPaint.setColor(fp.getColor());
            mPaint.setStrokeWidth(fp.getStrokeWidth());
            mCanvas.drawPath(fp.getPath(), mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        if (showCursor && currentMode != MODE_LINE && currentMode != MODE_RECT && currentMode != MODE_CIRCLE) {
            Paint cursorPaint = new Paint();
            cursorPaint.setColor(Color.RED);
            cursorPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mx, my, 10, cursorPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                mx = x; my = y;
                if (currentMode == MODE_PEN || currentMode == MODE_ERASER) {
                    mPath = new Path();
                    paths.add(new FingerPath(currentColor, currentBrushSize, mPath));
                    mPath.moveTo(x, y);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentMode == MODE_PEN || currentMode == MODE_ERASER) {
                    float dx = Math.abs(x - mx);
                    float dy = Math.abs(y - my);
                    if (dx >= TouchTolerance || dy >= TouchTolerance) {
                        mPath.quadTo(mx, my, (x + mx) / 2, (y + my) / 2);
                        mx = x; my = y;
                    }
                }
                showCursor = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (currentMode == MODE_LINE || currentMode == MODE_RECT || currentMode == MODE_CIRCLE) {
                    Path shape = new Path();
                    switch (currentMode) {
                        case MODE_LINE:
                            shape.moveTo(startX, startY);
                            shape.lineTo(x, y);
                            break;
                        case MODE_RECT:
                            shape.addRect(startX, startY, x, y, Path.Direction.CW);
                            break;
                        case MODE_CIRCLE:
                            float radius = (float) Math.hypot(x - startX, y - startY);
                            shape.addCircle(startX, startY, radius, Path.Direction.CW);
                            showCursor = false;
                            break;
                    }
                    paths.add(new FingerPath(currentColor, currentBrushSize, shape));
                } else {
                    mPath.lineTo(mx, my);
                }
                showCursor = true;
                invalidate();
                break;
        }
        return true;
    }

    private void drawShape(float startX, float startY, float endX, float endY) {
        switch (currentMode) {
            case MODE_LINE:
                mPath.moveTo(startX, startY);
                mPath.lineTo(endX, endY);
                break;
            case MODE_RECT:
                mPath.addRect(startX, startY, endX, endY, Path.Direction.CW);
                break;
            case MODE_CIRCLE:
                float radius = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                mPath.addCircle(startX, startY, radius, Path.Direction.CW);
                break;
        }
    }
    private static final float TouchTolerance = 4;

    private class DrawPath {
        Path path;
        Paint paint;

        DrawPath(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }
}
