package com.Kary.Futbolito_Sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mSensor;
    int vx, vy, aceleracion= 5,px,py;
    float  inicialY, inicialX;
    TextView lbBaloon,lbAX,lbAX3,lbDX,lbDX2,lbGoles;
    Display display ;
    int golArriba=0,golAbajo=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = getWindowManager().getDefaultDisplay();
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        lbBaloon = findViewById(R.id.lbBalon);
        lbAX = findViewById(R.id.lbXA);
        lbAX3 = findViewById(R.id.lbXA3);
        lbDX = findViewById(R.id.lbDX);
        lbDX2 = findViewById(R.id.lbDAX2);
        lbGoles = findViewById(R.id.lbGoles);
        lbGoles.setText("Goles:" + golArriba + "-" + golAbajo);

        Point size = new Point();
        display.getSize(size);
        px = size.x;
        py = size.y;
        inicialX = lbBaloon.getX();
        inicialY = lbBaloon.getY();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (lbBaloon.getX()>lbAX.getX() & lbBaloon.getX()< lbAX3.getX() &
                (lbBaloon.getY() >lbAX.getY() & lbBaloon.getY() <=lbAX3.getY())  ){
            vx=0;
            vy=0;
            lbBaloon.setY(inicialY);
            lbBaloon.setX(inicialX);
            golArriba++;
            lbGoles.setText("Marcador:"+golArriba+"-"+golAbajo);

        }else if (lbBaloon.getX()>lbDX.getX() & lbBaloon.getX()< lbDX2.getX() & lbBaloon.getY() >=py-400){
          //  Log.d("Prueba","Gol abajo");
            vx=0;
            vy=0;
            lbBaloon.setY(inicialY);
            lbBaloon.setX(inicialX);
            golAbajo++;
            lbGoles.setText("Marcador:"+golArriba+"-"+golAbajo);

        }else {
            if (event.values[0] < 0.1 & lbBaloon.getX() < px - lbBaloon.getWidth()) {
                vx += 5;
                lbBaloon.setTranslationX(vx * aceleracion);
            } else if (event.values[0] > 0.1 & lbBaloon.getX() > 0) {

                vx -= 5;
                lbBaloon.setTranslationX(vx * aceleracion);
            }

            if (event.values[1] > 0.1 & lbBaloon.getY() < py - 400) {
                vy += 5;
                lbBaloon.setTranslationY(vy * aceleracion);
            } else if (event.values[1] < 0.1 & lbBaloon.getY() > 50) {

                vy -= 5;
                lbBaloon.setTranslationY(vy * aceleracion);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}