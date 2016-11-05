package com.home.graphplot;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.home.graphplot.bluetooth.BluetoothSetup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class Plotting extends AppCompatActivity {
    Handler graphHandler = new Handler();
    Handler timerHandler = new Handler();
    private LineGraphSeries<DataPoint> seriesCH1;
    private LineGraphSeries<DataPoint> seriesCH2;
    private LineGraphSeries<DataPoint> seriesCH3;
    private LineGraphSeries<DataPoint> seriesCH4;
    private LineGraphSeries<DataPoint> seriesCH5;
    private LineGraphSeries<DataPoint> seriesCH6;

    private boolean isActivityRunning = false;

    // Buffer for building EMG value
    // contains highByte & lowByte of an Integer
    private byte[] buffer = new byte[12];

    //Debug
    long starttime = System.currentTimeMillis();
    long stoptime = System.currentTimeMillis();

    private Runnable timerRunnable = new Runnable() {
        ByteBuffer wrapper;
        InputStream inputStream;
        float x_Axis = 0;
        int amountBytes = 0;
        BluetoothSetup btSetup = Main.btSetup;

        @Override
        public void run() {
            if (btSetup.isConnected() && isActivityRunning) {
                Log.w("Tag", "Graph view running");
                try {
                    inputStream = btSetup.getBtData();
                    stoptime = System.currentTimeMillis();
                    Log.d("tag", stoptime - starttime + "");
                    starttime = System.currentTimeMillis();
                    if (inputStream.available() > 0) {
                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        amountBytes = inputStream.read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);
                        seriesCH1.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH2.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH3.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH4.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH5.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH6.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            x_Axis = x_Axis + 0.01f;
            graphHandler.post(this);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotting);
        isActivityRunning = true;
        GraphView graphView = (GraphView) findViewById(R.id.graph1);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);


        seriesCH1 = new LineGraphSeries<>();
        seriesCH2 = new LineGraphSeries<>();
        seriesCH3 = new LineGraphSeries<>();
        seriesCH4 = new LineGraphSeries<>();
        seriesCH5 = new LineGraphSeries<>();
        seriesCH6 = new LineGraphSeries<>();

        seriesCH1.setThickness(3);
        seriesCH2.setThickness(3);
        seriesCH3.setThickness(3);
        seriesCH4.setThickness(3);
        seriesCH5.setThickness(3);
        seriesCH6.setThickness(3);

        seriesCH1.setColor(Color.BLUE);
        seriesCH2.setColor(Color.RED);
        seriesCH3.setColor(Color.GREEN);
        seriesCH4.setColor(Color.GRAY);
        seriesCH5.setColor(Color.BLACK);
        seriesCH6.setColor(Color.MAGENTA);

        graphView.addSeries(seriesCH1);
        graphView.addSeries(seriesCH2);
        graphView.addSeries(seriesCH3);
        graphView.addSeries(seriesCH4);
        graphView.addSeries(seriesCH5);
        graphView.addSeries(seriesCH6);

        //Fetch Data in own thread
        //Thread thread = new Thread(timerRunnable);
        //thread.start();
        timerRunnable.run();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityRunning = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityRunning = true;
    }
}
