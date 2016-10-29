package com.home.graphplot;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.home.graphplot.bluetooth.BluetoothSetup;
import com.jjoe64.graphview.GraphView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class Plotting extends AppCompatActivity {
    private Handler timerHandler = new Handler();

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

            //Polling rate
            timerHandler.postDelayed(this, 10 );
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
