package com.example.breaklines;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class BreakLineDrawable extends Drawable {
    Bitmap bitmap;
    Rect bounds = new Rect(0,0,500,500);

    public BreakLineDrawable(){
        super();
        bitmap = Bitmap.createBitmap(bounds.width(),bounds.height(), Bitmap.Config.RGB_565);
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i<1000; i++) {
            int x = (int) Math.random() * 500;
            int y = (int) Math.random() * 500;
            bitmap.setPixel(x, y, Color.RED);
        }

        Canvas c = new Canvas(bitmap);
        c.drawBitmap(bitmap, null, bounds, null);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
