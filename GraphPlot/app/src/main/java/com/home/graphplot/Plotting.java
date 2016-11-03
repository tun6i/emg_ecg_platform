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
    static Handler graphHandler = new Handler();
    private LineGraphSeries<DataPoint> seriesCH1;
    private LineGraphSeries<DataPoint> seriesCH2;
    private LineGraphSeries<DataPoint> seriesCH3;
    private LineGraphSeries<DataPoint> seriesCH4;
    private LineGraphSeries<DataPoint> seriesCH5;
    private LineGraphSeries<DataPoint> seriesCH6;
    final int channels = 6;

    private boolean isActivityRunning = false;

    // Buffer for building EMG value
    // contains highByte & lowByte of an Integer
    private byte[] buffer = new byte[2];

    private Runnable timerRunnable = new Runnable() {
        ByteBuffer wrapper;
        InputStream inputStream;
        float x_Axis = 0;
        int amountBytes = 0;
        BluetoothSetup btSetup = Main.btSetup;
        @Override
        public void run() {
            if (btSetup.isConnected() && isActivityRunning) {
                try {
                    inputStream = btSetup.getBtData();
                    if (inputStream.available() > 0) {
                        do {
                            amountBytes = inputStream.read(buffer);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        for (int actualCH = 1; actualCH <= channels; actualCH++) {
                            amountBytes = inputStream.read(buffer);
                            final ByteBuffer wrapper = ByteBuffer.wrap(buffer);
                            short number = wrapper.getShort();
                            switch (actualCH) {
                                case 1:
                                    seriesCH1.appendData(new DataPoint(x_Axis, number), true, 100);
                                    break;
                                case 2:
                                    seriesCH2.appendData(new DataPoint(x_Axis, number), true, 100);
                                    break;
                                case 3:
                                    seriesCH3.appendData(new DataPoint(x_Axis, number), true, 100);
                                    break;
                                case 4:
                                    seriesCH4.appendData(new DataPoint(x_Axis, number), true, 100);
                                    break;
                                case 5:
                                    seriesCH5.appendData(new DataPoint(x_Axis, number), true, 100);
                                    break;
                                case 6:
                                    seriesCH6.appendData(new DataPoint(x_Axis, number), true, 100);
                                    break;
                            }

                            /* Own thread
                            switch (actualCH) {
                                case 1:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seriesCH1.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                                        }
                                    });
                                    break;
                                case 2:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seriesCH2.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                                        }
                                    });
                                    break;
                                case 3:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seriesCH3.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                                        }
                                    });
                                    break;
                                case 4:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seriesCH4.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                                        }
                                    });
                                    break;
                                case 5:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seriesCH5.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                                        }
                                    });
                                    break;
                                case 6:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            seriesCH6.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                                        }
                                    });
                                    break;
                            }*/
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.w("Tag", "Graph view running");
                x_Axis = x_Axis + 0.01f;

                graphHandler.postDelayed(this,18);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotting);
        GraphView graphView = (GraphView) findViewById(R.id.graph1);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        isActivityRunning = true;

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
