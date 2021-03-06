package com.example.gwer.manbogi;


import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class StepCheckService extends Service implements SensorEventListener {


    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;

    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 1100;

    static int stepCount=0;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    } // end of onCreate

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        } // end of if

        return START_REDELIVER_INTENT;
    } // end of onStartCommand

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        } // end of if
    } // end of onDestroy

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);

            if (gabOfTime > 100) { //  gap of time of step count
                lastTime = currentTime;

                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Intent myFilteredResponse = new Intent("com.example.manbogi");

                    stepCount+=1;
                    myFilteredResponse.putExtra("stepService", stepCount);

                    sendBroadcast(myFilteredResponse);
                } // end of if

                lastX = event.values[0];
                lastY = event.values[1];
                lastZ = event.values[2];
            } // end of if
        } // end of if

    } // end of onSensorChanged

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
