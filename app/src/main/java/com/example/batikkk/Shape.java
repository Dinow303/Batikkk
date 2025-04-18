package com.example.batikkk;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Shape {
    float startX, startY, endX, endY;
    Paint paint;

    public Shape(float startX, float startY, Paint paint) {
        this.startX = startX;
        this.startY = startY;
        this.paint = new Paint(paint);
    }

    public void setEnd(float x, float y) {
        this.endX = x;
        this.endY = y;
    }

    public abstract void draw(Canvas canvas);

//    LineShape
    public class LineShape extends Shape {
        public LineShape(float startX, float startY, Paint paint) {
            super(startX, startY, paint);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }



//    Sub Class buat kotak

    public class RectShape extends Shape {
        public RectShape(float startX, float startY, Paint paint) {
            super(startX, startY, paint);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawRect(startX, startY, endX, endY, paint);
        }
    }


//    Sub class buat lingkaran

    public class CircleShape extends Shape {
        public CircleShape(float startX, float startY, Paint paint) {
            super(startX, startY, paint);
        }

        @Override
        public void draw(Canvas canvas) {
            float radius = (float) Math.hypot(endX - startX, endY - startY);
            canvas.drawCircle(startX, startY, radius, paint);
        }
    }
    public enum ShapeType {
        LINE,
        RECTANGLE,
        CIRCLE
    }
}

