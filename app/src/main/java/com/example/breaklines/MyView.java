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
    Bitmap bitmap2;
    Canvas bitmapEditor;
    Canvas bitmap2Editor;
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

    private Paint grayPaint;
    private Paint blackPaint;
    private Paint bluePaint;
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

    public Rect getScreenSize(){
        return screenSize;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private void drawRedSquare(){
        for (int i=0; i<bitmapSize.width()/2; i++) {
            for (int j = 0; j < bitmapSize.height()/2; j++) {
                bitmap.setPixel(i, j, Color.RED);
                colorArray[j*bitmapSize.width()+i] = Color.RED;
            }
        }
    }

    public void shiftBitmap2Up(){
        for (int i=0; i<bitmapSize.width(); i++) {
            for (int j = 0; j < bitmapSize.height()-1; j++) {
                colorArray[j*bitmapSize.width()+i] = colorArray[(j+1)*bitmapSize.width()+i];
            }
        }
    }

    //Note: We override this so that the screenSize of our view will be defined.
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        screenSize = new Rect(0,0,xNew,yNew);
        bitmapSize = new Rect(0,0,xNew/4,yNew/4);
        bitmap = Bitmap.createBitmap(bitmapSize.width(),bitmapSize.height(), Bitmap.Config.RGB_565);
        bitmap2 = Bitmap.createBitmap(bitmapSize.width(),bitmapSize.height(), Bitmap.Config.RGB_565);
        bitmapEditor = new Canvas(bitmap);
        bitmap2Editor = new Canvas(bitmap2);
        colorArray = new int[bitmapSize.width()*bitmapSize.height()];
        drawRedSquare();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //Note: We have to wait for the first onDraw() to be called before screenSize will be defined by onSizeChanged
//        if (bitmap == null){
//            bitmap = Bitmap.createBitmap(bitmapSize.width(),bitmapSize.height(), Bitmap.Config.RGB_565);
//
//            for (int i=0; i<bitmapSize.width()/2; i++) {
//                for (int j = 0; j < bitmapSize.height()/2; j++) {
//                    bitmap.setPixel(i, j, Color.RED);
//                }
//            }
//            bitmapEditor = new Canvas(bitmap);
//        }

        bitmapEditor.drawLine(0, lineYPos, bitmapSize.width(), lineYPos, grayPaint);
        bitmapEditor.drawLine(0, lineYPosPrev, bitmapSize.width(), lineYPosPrev, blackPaint);
        bitmap2.setPixels(colorArray, 0, bitmapSize.width(),0,0,bitmapSize.width(),bitmapSize.height());
        canvas.drawBitmap(bitmap2, null, screenSize, null);
    }

    public void addSquare(){
        int a = (int)(Math.random()*(bitmapSize.width()-100));
        int b = (int)(Math.random()*(bitmapSize.height()-100));

        for (int i=a; i<a+100; i++) {
            for (int j = b; j < b + 100; j++) {
//                bitmap.setPixel(i, j, Color.BLUE);
                colorArray[i+j*bitmapSize.width()] = Color.BLUE;
            }
        }
    }

    private float lineYPosPrev = 0;
    private float lineYPos = 4;
    public void shiftUp(){
//        for (int i=0; i<screenSize.width(); i++) {
//            for (int j=0; j<screenSize.height()-30; j++) {
//                bitmap.setPixel(i, j, bitmap.getPixel(i, j+30));
//            }
//        }
        //bitmap = Bitmap.createBitmap(screenSize.width(),screenSize.height(), Bitmap.Config.RGB_565);
        lineYPosPrev++;
        lineYPos++;
        if (lineYPos>bitmapSize.height()) {
            lineYPos = 0;
        }
        if (lineYPosPrev>bitmapSize.height()) {
            lineYPosPrev = 0;
        }
//        if (lineYPos == 230){
//            bitmapEditor.drawLine(0, lineYPos, screenSize.width(), lineYPos, grayPaint);
//        }
    }
}

//IDEAS:
//Use fewer pixels
//Use getpixels() instead of getpixel()
//Use a canvas to draw onto the bitmap directly.
//Possibley, I could use a clever use of drawlines()?