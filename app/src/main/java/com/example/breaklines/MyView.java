package com.example.breaklines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import static java.lang.System.arraycopy;

public class MyView extends View {
    Bitmap bitmap;
    Rect screenSize;
    Rect bitmapSize;
    int[] colorArray;

    public MyView(Context context) {
        super(context);
        init();
    }

    // This constructor required when the view is in xml.
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
    }

    public Rect getScreenSize() {
        return screenSize;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private int rowDelta = 3; //Number of rows back the new pixel is copied from
    public void shiftBitmap2Down(double tiltAmount) {
        int a = (int) (Math.random() * tiltAmount);
        arraycopy(colorArray, 0, colorArray, bitmapSize.width() * rowDelta + a, (bitmapSize.width() * (bitmapSize.height() - rowDelta) - a));
        //Note: The above line copies the old array into the new array, shifted by one row.
        //However, it leaves the very bottom row as it was before, instead of shifting in blank pixels.
        //So the below loop replaces those pixels with all black.
        for (int i = 0; i < bitmapSize.width() * rowDelta + a; i++) {
            colorArray[i] = Color.BLACK;
        }
    }

    //Note: We override this so that the screenSize of our view will be defined.
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        screenSize = new Rect(0, 0, xNew, yNew);
        bitmapSize = new Rect(0, 0, xNew / 4, yNew / 4);
        bitmap = Bitmap.createBitmap(bitmapSize.width(), bitmapSize.height(), Bitmap.Config.RGB_565);
        colorArray = new int[bitmapSize.width() * bitmapSize.height()];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bitmap.setPixels(colorArray, 0, bitmapSize.width(), 0, 0, bitmapSize.width(), bitmapSize.height());
        canvas.drawBitmap(bitmap, null, screenSize, null);
    }

    private final Rect dropSize = new Rect(0,0,3,8);
    public void addDrop() {
        int horizStart = (int) (Math.random() * (bitmapSize.width() - dropSize.width()));

        for (int i = horizStart; i < horizStart+dropSize.width(); i++) {
            for (int j = 0; j < dropSize.height(); j++) {
                colorArray[i + j * bitmapSize.width()] = Color.BLUE;
            }
        }
    }
}
//IDEAS:
//Use fewer pixels
//Use getpixels() instead of getpixel()
//Use a canvas to draw onto the bitmap directly.
//Possibly, I could use a clever use of drawlines()?