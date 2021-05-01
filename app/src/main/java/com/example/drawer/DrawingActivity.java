package com.example.drawer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Random;

public class DrawingActivity extends AppCompatActivity {

    MyCanvas myCanvas;
    TouchListener touchListener;
    Button red;
    Button blue;
    Button green;

    Button undo;
    Button clear;
    Button done;

    Random rd = new Random();

    static final int REQUEST_IMAGE= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        red = findViewById(R.id.bt_red);
        blue = findViewById(R.id.bt_blue);
        green = findViewById(R.id.bt_green);

        undo = findViewById(R.id.bt_undo);
        clear = findViewById(R.id.bt_clear);
        done = findViewById(R.id.bt_done);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            Bitmap thumbnail = (Bitmap) extras.get("photo");
            myCanvas.setBackground(new BitmapDrawable(getResources(), thumbnail));
        }
        touchListener = new TouchListener(this);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.bt_red:
                        myCanvas.setColor("red");
                        break;
                    case R.id.bt_blue:
                        myCanvas.setColor("blue");
                        break;
                    case R.id.bt_green:
                        myCanvas.setColor("green");
                        break;
                    case R.id.bt_undo:
                        myCanvas.undo();
                        break;
                    case R.id.bt_clear:
                        myCanvas.clear();
                        break;
                    case R.id.bt_done:
                        saveCanvas();
                        Intent intent = new Intent(DrawingActivity.this, MainActivity.class);
                        startActivity(intent);
                }

            }
        };
        Log.i("Draw", "" + R.drawable.ic_baseline_favorite_24);

        red.setOnClickListener(onClickListener);
        blue.setOnClickListener(onClickListener);
        green.setOnClickListener(onClickListener);

        undo.setOnClickListener(onClickListener);
        clear.setOnClickListener(onClickListener);
        done.setOnClickListener(onClickListener);

        myCanvas.setOnTouchListener(touchListener);
    }

    public void saveCanvas() {
        Bitmap bitmap = Bitmap.createBitmap(myCanvas.getWidth(), myCanvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        myCanvas.draw(canvas);
        MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Masterpiece",
                "picture"
        );
    }

    public void addPath(int id, float x, float y) {
        myCanvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        myCanvas.updatePath(id, x, y);
    }

    public void removePath() {
        myCanvas.closePath();
    }

    public void onDoubleTap(MotionEvent e) {
        Bitmap img = getBitmapFromVectorDrawable(this, R.drawable.ic_baseline_star_24);
        myCanvas.addIcon(img, e.getX(), e.getY());
    }

    public void onLongPress(MotionEvent e) {
        Bitmap img = getBitmapFromVectorDrawable(this, R.drawable.ic_baseline_favorite_24);
        myCanvas.addIcon(img, e.getX(), e.getY());
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}