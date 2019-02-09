package com.example.breaklines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    Bitmap bitmap;
    Canvas bitmapEditor;
    Rect size;

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

    private void init(){
        grayPaint = new Paint();
        grayPaint.setColor(Color.GRAY);
        grayPaint.setStyle(Paint.Style.STROKE);
        grayPaint.setStrokeWidth(8);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(8);
    }

    public Rect getSize(){
        return size;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    //Note: We override this so that the size of our view will be defined.
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        size = new Rect(0,0,xNew,yNew);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //Note: We have to wait for the first onDraw() to be called before size will be defined by onSizeChanged
        if (bitmap == null){
            bitmap = Bitmap.createBitmap(1000,1000, Bitmap.Config.RGB_565);

            for (int i=0; i<1000/2; i++) {
                for (int j = 0; j < 1000/2; j++) {
                    bitmap.setPixel(i, j, Color.RED);
                }
            }
            bitmapEditor = new Canvas(bitmap);
        }

        bitmapEditor.drawLine(0, lineYPos, 1000, lineYPos, grayPaint);
        bitmapEditor.drawLine(0, lineYPosPrev, 1000, lineYPosPrev, blackPaint);
        canvas.drawBitmap(bitmap, null, size, null);
    }

    public void addSquare(){
        int a = (int)(Math.random()*(size.width()-100));
        int b = (int)(Math.random()*(size.height()-100));

        for (int i=a; i<a+100; i++) {
            for (int j = b; j < b + 100; j++) {
                bitmap.setPixel(i, j, Color.BLUE);
            }
        }
    }

    private float lineYPosPrev = 0;
    private float lineYPos = 4;
    private Paint grayPaint;
    private Paint blackPaint;
    public void shiftUp(){
//        for (int i=0; i<size.width(); i++) {
//            for (int j=0; j<size.height()-30; j++) {
//                bitmap.setPixel(i, j, bitmap.getPixel(i, j+30));
//            }
//        }
        //bitmap = Bitmap.createBitmap(size.width(),size.height(), Bitmap.Config.RGB_565);
        lineYPosPrev++;
        lineYPos++;
        if (lineYPos>1000) {
            lineYPos = 0;
        }
        if (lineYPosPrev>1000) {
            lineYPosPrev = 0;
        }
        if (lineYPos == 230){
            bitmapEditor.drawLine(0, lineYPos, size.width(), lineYPos, grayPaint);
        }
    }
}

//IDEAS:
//Use fewer pixels
//Use getpixels() instead of getpixel()
//Use a canvas to draw onto the bitmap directly.
//Possibley, I could use a clever use of drawlines()?