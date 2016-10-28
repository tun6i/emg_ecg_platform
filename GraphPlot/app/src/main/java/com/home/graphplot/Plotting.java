package com.home.graphplot;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Plotting extends AppCompatActivity {
    private Handler timerHandler = new Handler();

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (Main.btSetup.isConnected()) {
                String line;
                try {
                    InputStream inputStream = Main.btSetup.getBtSocket().getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ASCII"));
                    if (inputStream.available() > 0) {
                        if (( line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                            Integer dataValue = Integer.parseInt(line);
                            long millis = System.currentTimeMillis();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Polling rate
            timerHandler.postDelayed(this, 50);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotting);
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
    }
}
