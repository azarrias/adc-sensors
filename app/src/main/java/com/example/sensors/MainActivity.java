package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private List<Sensor> sensors;
    private List<TextView[]> textViews;
    private static final int MAX_SENSOR_VALUES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout root = findViewById(R.id.root);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        textViews = new ArrayList<>();
        for (Sensor sensor : sensors) {
            TextView nameTextView = new TextView(this);
            nameTextView.setText(sensor.getName());
            root.addView(nameTextView);
            LinearLayout sensorLinearLayout = new LinearLayout(this);
            root.addView(sensorLinearLayout);
            TextView[] sensorValuesTextViews = new TextView[MAX_SENSOR_VALUES];
            textViews.add(sensorValuesTextViews);
            for (int i = 0; i < MAX_SENSOR_VALUES; i++) {
                sensorValuesTextViews[i] = new TextView(this);
                sensorValuesTextViews[i].setText("?");
                sensorValuesTextViews[i].setWidth(87);
            }
            TextView xTextView = new TextView(this);
            TextView yTextView = new TextView(this);
            TextView zTextView = new TextView(this);
            xTextView.setText(" X: ");
            yTextView.setText(" Y: ");
            zTextView.setText(" Z: ");
            sensorLinearLayout.addView(xTextView);
            sensorLinearLayout.addView(sensorValuesTextViews[0]);
            sensorLinearLayout.addView(yTextView);
            sensorLinearLayout.addView(sensorValuesTextViews[1]);
            sensorLinearLayout.addView(zTextView);
            sensorLinearLayout.addView(sensorValuesTextViews[2]);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            int n = 0;
            for (Sensor sensor : sensors) {
                if (event.sensor == sensor) {
                    for (int i = 0; i < event.values.length && i < MAX_SENSOR_VALUES; i++) {
                        textViews.get(n)[i].setText(Float.toString(event.values[i]));
                    }
                }
                n++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
