package app.gesturesensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";

    private SensorManager sm;
    Sensor accelerometer, gyroscope;

    TextView tvX, tvY, tvZ, tvGX, tvGY, tvGZ, gesto;

    private static DecimalFormat df2 = new DecimalFormat("#.#");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvX = (TextView) findViewById(R.id.textViewX);
        tvY = (TextView) findViewById(R.id.textViewY);
        tvZ = (TextView) findViewById(R.id.textViewZ);

        tvGX = (TextView) findViewById(R.id.tvGX);
        tvGY = (TextView) findViewById(R.id.tvGY);
        tvGZ = (TextView) findViewById(R.id.tvGZ);

        gesto = (TextView) findViewById(R.id.gesto);

        Log.d(TAG, "onCreate: Initializing Sensor Services");

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer != null){
            sm.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered accelerometer listener");
        }
        else{
            tvX.setText("Acelerômetro não suportado.");
            tvY.setText("Acelerômetro não suportado.");
            tvZ.setText("Acelerômetro não suportado.");
        }

        gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroscope != null){
            sm.registerListener(MainActivity.this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "onCreate: Registered gyroscope listener");
        }
        else{
            tvGX.setText("Giroscópio não suportado.");
            tvGY.setText("Giroscópio não suportado.");
            tvGZ.setText("Giroscópio não suportado.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor s = event.sensor;

        if(s.getType() == Sensor.TYPE_ACCELEROMETER) {

            //Log.d(TAG, "onSensorChanged: X: " + event.values[0] + "Y: " + event.values[1] + "Z : " + event.values[2]);

            tvX.setText("X: " + df2.format(event.values[0]));
            tvY.setText("Y: " + df2.format(event.values[1]));
            tvZ.setText("Z: " + df2.format(event.values[2]));
        }
        else if(s.getType() == Sensor.TYPE_GYROSCOPE){

            //Log.d(TAG, "onSensorChanged: X: " + event.values[0] + "Y: " + event.values[1] + "Z : " + event.values[2]);

            tvGX.setText("X: " + df2.format(event.values[0]));
            tvGY.setText("Y: " + df2.format(event.values[1]));
            tvGZ.setText("Z: " + df2.format(event.values[2]));

            verificaGesto(event);
        }
    }

    void verificaGesto(SensorEvent event){
        if(event.values[0] > 3){
            gesto.setText("Giro cima");
        }
        else if(event.values[0] < -3){
            gesto.setText("Giro baixo");
        }
        else if(event.values[1] > 3){
            gesto.setText("Giro esquerda");
        }
        else if(event.values[1] < -3){
            gesto.setText("Giro direita");
        }
    }
}
