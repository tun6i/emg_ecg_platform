package com.home.graphplot;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.home.graphplot.bluetooth.BluetoothSetup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Plotting extends AppCompatActivity {
    private Handler timerHandler = new Handler();
    Random random = new Random();
    LineGraphSeries<DataPoint> series;
    GraphView graphView;
    private float i = 0.5f;


    private Runnable timerRunnable = new Runnable() {
        byte[] buffer = new byte[2];

        @Override
        public void run() {
           BluetoothSetup btSetup = Main.btSetup;
            int channels = 1;
            if (btSetup.isConnected()) {
                try {
                    InputStream inputStream = btSetup.getBtData();
                    if (inputStream.available() > 0) {
                        for (int actualCH = 1; actualCH <= channels; actualCH++) {
                            inputStream.read(buffer);
                            ByteBuffer wrapper = ByteBuffer.wrap(buffer);
                            short number = wrapper.getShort();
                            switch (actualCH) {
                                case 1:
                                    series.appendData(new DataPoint(i, number), true, 100);
                                    //graphView.addSeries(series);
                                    i = i + 0.01f;
                                    //Polling rate
                                    graphView.getViewport().scrollToEnd();
                                    break;
                                case 2:

                                    break;
                                case 3:

                                    break;
                                case 4:

                                    break;
                                case 5:

                                    break;
                                case 6:

                                    break;
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            timerHandler.postDelayed(this, 10);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotting);
        graphView = (GraphView) findViewById(R.id.graph);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        //graphView.getGridLabelRenderer().set
        graphView.getViewport().setScalable(true);

        series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 999),
                new DataPoint(0.01, 500),
                new DataPoint(0.02, 356),
                new DataPoint(0.03, 624),
                new DataPoint(0.04, 1023)
        });
        series.setThickness(5);
        graphView.addSeries(series);
        timerRunnable.run();}
}
