package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        textViews = new ArrayList<>();
        for (Sensor sensor : sensors) {
            TextView nameTextView = new TextView(this);
            nameTextView.setText(sensor.getName());
            root.addView(nameTextView);
            LinearLayout sensorLinearLayout = new LinearLayout(this);
            root.addView(sensorLinearLayout);
            TextView[] sensorValuesTextViews = new TextView[MAX_SENSOR_VALUES];
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
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
