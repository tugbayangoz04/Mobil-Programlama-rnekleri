package com.example.mynotbook;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textSensorData;
    private SensorManager sensorManager;
    private Sensor currentSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSensorData = findViewById(R.id.textSensorData);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        setupSensorButton(R.id.btnAccelerometer, Sensor.TYPE_ACCELEROMETER);
        setupSensorButton(R.id.btnGyroscope, Sensor.TYPE_GYROSCOPE);
        setupSensorButton(R.id.btnHumidity, Sensor.TYPE_RELATIVE_HUMIDITY);
        setupSensorButton(R.id.btnLight, Sensor.TYPE_LIGHT);
        setupSensorButton(R.id.btnMagnetometer, Sensor.TYPE_MAGNETIC_FIELD);
        setupSensorButton(R.id.btnPressure, Sensor.TYPE_PRESSURE);
        setupSensorButton(R.id.btnProximity, Sensor.TYPE_PROXIMITY);
        setupSensorButton(R.id.btnThermometer, Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    private void setupSensorButton(int buttonId, int sensorType) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {

            if (currentSensor != null) {
                sensorManager.unregisterListener(this);
            }


            currentSensor = sensorManager.getDefaultSensor(sensorType);
            if (currentSensor != null) {
                sensorManager.registerListener(this, currentSensor, SensorManager.SENSOR_DELAY_NORMAL);
                Toast.makeText(this, currentSensor.getName() + " aktif", Toast.LENGTH_SHORT).show();
            } else {
                textSensorData.setText("Bu sensör cihazda mevcut değil.");
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append("Sensör: ").append(event.sensor.getName()).append("\n");
        for (int i = 0; i < event.values.length; i++) {
            builder.append("Değer ").append(i + 1).append(": ").append(event.values[i]).append("\n");
        }
        textSensorData.setText(builder.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

