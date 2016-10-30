package com.home.graphplot;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.home.graphplot.bluetooth.BluetoothSetup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Plotting extends AppCompatActivity {
    private volatile Handler timerHandler = new Handler();
    LineGraphSeries<DataPoint> seriesCH1;
    LineGraphSeries<DataPoint> seriesCH2;
    LineGraphSeries<DataPoint> seriesCH3;
    LineGraphSeries<DataPoint> seriesCH4;
    LineGraphSeries<DataPoint> seriesCH5;
    LineGraphSeries<DataPoint> seriesCH6;

    GraphView graphView;
    private float i = 0.5f;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
           BluetoothSetup btSetup = Main.btSetup;
            if (btSetup.isConnected()) {
                seriesCH1.appendData(new DataPoint(i, Main.getDataThread.getValue().get(0)), true, 100);
                seriesCH2.appendData(new DataPoint(i, Main.getDataThread.getValue().get(1)), true, 100);
                seriesCH3.appendData(new DataPoint(i, Main.getDataThread.getValue().get(2)), true, 100);
                seriesCH4.appendData(new DataPoint(i, Main.getDataThread.getValue().get(3)), true, 100);
                seriesCH5.appendData(new DataPoint(i, Main.getDataThread.getValue().get(4)), true, 100);
                seriesCH6.appendData(new DataPoint(i, Main.getDataThread.getValue().get(5)), true, 100);

                i = i + 0.01f;
                graphView.getViewport().scrollToEnd();
            }
            timerHandler.postDelayed(this, 10);
            Log.d("Tag", "Plotting " + i);
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
        if (isFinishing()) {
            timerHandler.removeCallbacks(timerRunnable);
        } else {

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

        timerRunnable.run();


    }
}
