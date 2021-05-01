package com.example.drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyCanvas extends View {
    List <Stroke> activeStrokes;
    List<Icon> icons;
    List<Object> all;
    String color = "blue";

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        activeStrokes = new ArrayList<>();
        all = new ArrayList<>();
        icons = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(Stroke stroke: activeStrokes){
            canvas.drawPath(stroke.getPath(), stroke.getPaint());
        }
        for (Icon icon : icons) {
            Paint pathPaint = new Paint();
            Log.i("Canvas: ", "" + R.drawable.ic_baseline_favorite_24);
            pathPaint.setStyle(Paint.Style.STROKE);
            pathPaint.setStrokeWidth(5);
            canvas.drawBitmap(icon.getIcon(), icon.getX(), icon.getY(), pathPaint);
        }
        super.onDraw(canvas);
    }

    public void addPath(int id, float x, float y) {
        Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(20);
        if (color.equals("red")) {
            pathPaint.setColor(Color.RED);
        }
        else if (color.equals("blue")) {
            pathPaint.setColor(Color.BLUE);
        }
        else if (color.equals("green")) {
            pathPaint.setColor(Color.GREEN);
        }

        Path path = new Path();
        path.moveTo(x, y);
        Stroke stroke = new Stroke(path, pathPaint);
        activeStrokes.add(0, stroke);
        all.add(0, stroke);

        invalidate();
    }

    public void addIcon(Bitmap img, float x, float y) {
        Icon icon = new Icon(img, x, y);
        icons.add(0, icon);
        all.add(0, icon);

        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        Stroke stroke = activeStrokes.get(id);
        if(stroke != null){
            stroke.getPath().lineTo(x, y);
        }
        invalidate();
    }

    public void closePath() {
        activeStrokes.remove(0);
        invalidate();
    }

    public void undo() {
        Object obj = all.remove(0);

        if(obj.getClass().equals(Stroke.class) && activeStrokes.size() > 0){
            activeStrokes.remove(0);
        }
        if(obj.getClass().equals(Icon.class) && icons.size() > 0){
            icons.remove(0);
        }

        invalidate();
    }

    public void clear() {
        activeStrokes.clear();
        icons.clear();
        invalidate();
    }

    public void setColor(String color) {
        this.color = color;
    }
}
