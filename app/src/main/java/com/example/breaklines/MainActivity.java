package com.example.breaklines;

import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MyView backgroundView;
    CountDownTimer repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundView = findViewById(R.id.breakLinesView);
        repeat = new CountDownTimer((long) 166.66666,20) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                drawTick();
            }
        }.start();
    }

    public void drawTick(){
        backgroundView.addSquare();
        backgroundView.invalidate();
        repeat.start();
    }

}
