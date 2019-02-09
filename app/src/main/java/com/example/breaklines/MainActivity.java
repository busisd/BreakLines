package com.example.breaklines;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    BreakLineDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawable = new BreakLineDrawable();
        ImageView image = findViewById(R.id.imageView);
        //image.setImageDrawable(drawable);

        Bitmap bitmap = Bitmap.createBitmap(500,500, Bitmap.Config.RGB_565);
        for (int i = 0; i<1000; i++) {
            int x = (int) Math.random() * 500;
            int y = (int) Math.random() * 500;
            bitmap.setPixel(x, y, Color.RED);
        }
        Canvas c = new Canvas(bitmap);

    }
}
