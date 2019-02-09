package com.example.breaklines;

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
        repeat = new CountDownTimer((long) 16.6666,20) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                drawTick();
            }
        }.start();
    }

    int counter = 0;
    public void drawTick(){
        //Note: this if statement is needed or else the timer starts trying to draw stuff before the program is loaded.
        if (backgroundView.getScreenSize() != null && backgroundView.getBitmap() != null) {
            backgroundView.shiftUp();
            if (counter%600 == 0) backgroundView.addSquare();
            backgroundView.invalidate();
        }
        repeat.start();
        counter++;
    }

}
