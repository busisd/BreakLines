package com.example.breaklines;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    Bitmap bitmap;
    Rect size;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        int width = getWidth();
        int height = getHeight();

    }

    //Note: We override this so that the size of our view will be defined.
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        size = new Rect(0,0,xNew,yNew);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //Note: We have to wait for the first onDraw() to be called before size will be defined by onSizeChanged
        if (bitmap == null){
            bitmap = Bitmap.createBitmap(size.width(),size.height(), Bitmap.Config.RGB_565);

            for (int i=0; i<size.width()/2; i++) {
                for (int j = 0; j < size.height()/2; j++) {
                    bitmap.setPixel(i, j, Color.RED);
                }
            }

        }

        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, null, size, null);
    }
}