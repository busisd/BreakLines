package com.example.breaklines;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private MyView backgroundView;
    CountDownTimer repeat;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetic;
    private float[] gravityMat;
    private float[] magneticMat;
    private float[] orientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

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
            backgroundView.shiftBitmap2Down(orientation[2]);
            if (counter%3 == 0) backgroundView.addDrop();
            backgroundView.invalidate();
        }
        repeat.start();
        counter++;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            gravityMat = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magneticMat = event.values;
        }
        float[] Rota = new float[9];
        float[] Incl = new float[9];
        if (gravityMat == null || magneticMat == null){
            return;
        }
        if (SensorManager.getRotationMatrix(Rota, Incl, gravityMat, magneticMat)){
            SensorManager.getOrientation(Rota, orientation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
