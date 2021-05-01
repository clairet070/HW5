package com.example.drawer;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Icon {
    private Bitmap bm;
    private float x;
    private float y;

    public Icon(Bitmap bm, float x, float y) {
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    public Bitmap getIcon() {
        return bm;
    }

    public void setIcon(Bitmap bm) {
        this.bm = bm;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
