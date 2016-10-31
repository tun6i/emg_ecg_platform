package com.home.graphplot;

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
    private Handler timerHandler = new Handler();
    LineGraphSeries<DataPoint> series;
    GraphView graphView;
    private float i = 0;
    int counter = 1;

    private Runnable timerRunnable = new Runnable() {
        byte[] buffer = new byte[2];
        ByteBuffer wrapper;
        InputStream inputStream;

        @Override
        public void run() {
           BluetoothSetup btSetup = Main.btSetup;
            int channels = 6;

            if (btSetup.isConnected()) {
                try {
                    inputStream = btSetup.getBtData();
                    if (inputStream.available() > 0) {
                        do {
                            inputStream.read(buffer);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);
                        for (int actualCH = 1; actualCH <= channels; actualCH++) {
                            inputStream.read(buffer);
                            counter++;
                            ByteBuffer wrapper = ByteBuffer.wrap(buffer);
                            short number = wrapper.getShort();
                            switch (actualCH) {
                                case 1:
                                    series.appendData(new DataPoint(i, number), true, 500);
                                    //graphView.addSeries(series);
                                    i = i + 0.01f;
                                    //Polling rate
                                    graphView.getViewport().scrollToEnd();
                                    graphView.invalidate();
                                    Log.d("Tag", counter + " ");
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

                /*series.appendData(new DataPoint(i, 512), true, 100);
                //graphView.addSeries(series);
                i = i + 0.01f;
                //Polling rate
                graphView.getViewport().scrollToEnd();*/

            }
            timerHandler.postDelayed(this, 10);
        }

    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timerHandler.removeCallbacks(timerRunnable);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotting);
        graphView = (GraphView) findViewById(R.id.graph1);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        //graphView.getGridLabelRenderer().set
        graphView.getViewport().setScalable(true);

        series = new LineGraphSeries<>();
        series.setThickness(1);
        graphView.addSeries(series);
        timerRunnable.run();
    }
}
