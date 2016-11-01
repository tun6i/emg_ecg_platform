package com.home.graphplot;

import android.graphics.Color;
import android.os.Handler;
import android.os.Process;
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
    Handler timerHandler1 = new Handler();
    LineGraphSeries<DataPoint> seriesCH1;
    LineGraphSeries<DataPoint> seriesCH2;
    LineGraphSeries<DataPoint> seriesCH3;
    LineGraphSeries<DataPoint> seriesCH4;
    LineGraphSeries<DataPoint> seriesCH5;
    LineGraphSeries<DataPoint> seriesCH6;

    GraphView graphView;
    private float i = 0;

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
                            ByteBuffer wrapper = ByteBuffer.wrap(buffer);
                            short number = wrapper.getShort();
                            switch (actualCH) {
                                case 1:
                                    seriesCH1.appendData(new DataPoint(i, number), true, 500);
                                    break;
                                case 2:
                                    seriesCH2.appendData(new DataPoint(i, number), true, 500);
                                    break;
                                case 3:
                                    seriesCH3.appendData(new DataPoint(i, number), true, 500);
                                    break;
                                case 4:
                                    seriesCH4.appendData(new DataPoint(i, number), true, 500);
                                    break;
                                case 5:
                                    seriesCH5.appendData(new DataPoint(i, number), true, 500);
                                    break;
                                case 6:
                                    seriesCH6.appendData(new DataPoint(i, number), true, 500);
                                    break;
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i = i + 0.01f;
                graphView.getViewport().scrollToEnd();
            }

            timerHandler.postDelayed(this, 1);
        }

    };

    private Runnable reset = new Runnable() {
        @Override
        public void run() {
            timerHandler1.postDelayed(this, 20000);
            seriesCH1.resetData(new DataPoint[]{});
            seriesCH2.resetData(new DataPoint[]{});
            seriesCH3.resetData(new DataPoint[]{});
            seriesCH4.resetData(new DataPoint[]{});
            seriesCH5.resetData(new DataPoint[]{});
            seriesCH6.resetData(new DataPoint[]{});
            graphView.invalidate();

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
        /*graphView.addSeries(seriesCH2);
        graphView.addSeries(seriesCH3);
        graphView.addSeries(seriesCH4);
        graphView.addSeries(seriesCH5);
        graphView.addSeries(seriesCH6);*/

        timerRunnable.run();
        //android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_MORE_FAVORABLE);
        //reset.run();
    }
}
