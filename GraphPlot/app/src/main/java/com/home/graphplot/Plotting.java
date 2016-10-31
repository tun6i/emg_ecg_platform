package com.home.graphplot;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.home.graphplot.bluetooth.BluetoothSetup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Plotting extends AppCompatActivity {
    private LineGraphSeries<DataPoint> seriesCH1;
    private LineGraphSeries<DataPoint> seriesCH2;
    private LineGraphSeries<DataPoint> seriesCH3;
    private LineGraphSeries<DataPoint> seriesCH4;
    private LineGraphSeries<DataPoint> seriesCH5;
    private LineGraphSeries<DataPoint> seriesCH6;
    private GraphView graphView;
    private float i = 0.0f;
    private Handler graphHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1337) {
                int channel = msg.arg1;
                int value = msg.arg2;
                switch (channel) {
                    case 1:
                        seriesCH1.appendData(new DataPoint(i, value), true, 100);
                        break;
                    case 2:
                        seriesCH2.appendData(new DataPoint(i, value), true, 100);
                        break;
                    case 3:
                        seriesCH3.appendData(new DataPoint(i, value), true, 100);
                        break;
                    case 4:
                        seriesCH4.appendData(new DataPoint(i, value), true, 100);
                        break;
                    case 5:
                        seriesCH5.appendData(new DataPoint(i, value), true, 100);
                        break;
                    case 6:
                        seriesCH6.appendData(new DataPoint(i, value), true, 100);
                        break;
                }
                i = i + 0.01f;
                graphView.getViewport().scrollToEnd();
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotting);
        Main.getDataThread.setViewHandler(graphHandler);
        graphView = (GraphView) findViewById(R.id.graph1);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setScalable(true);

        seriesCH1 = new LineGraphSeries<>();
        seriesCH2 = new LineGraphSeries<>();
        seriesCH3 = new LineGraphSeries<>();
        seriesCH4 = new LineGraphSeries<>();
        seriesCH5 = new LineGraphSeries<>();
        seriesCH6 = new LineGraphSeries<>();

        seriesCH1.setThickness(2);
        seriesCH2.setThickness(2);
        seriesCH3.setThickness(2);
        seriesCH4.setThickness(2);
        seriesCH5.setThickness(2);
        seriesCH6.setThickness(2);

        seriesCH1.setColor(Color.CYAN);
        seriesCH2.setColor(Color.GREEN);
        seriesCH3.setColor(Color.BLUE);
        seriesCH4.setColor(Color.RED);
        seriesCH5.setColor(Color.BLACK);
        seriesCH6.setColor(Color.MAGENTA);

        graphView.addSeries(seriesCH1);
        graphView.addSeries(seriesCH2);
        graphView.addSeries(seriesCH3);
        graphView.addSeries(seriesCH4);
        graphView.addSeries(seriesCH5);
        graphView.addSeries(seriesCH6);
    }

}
