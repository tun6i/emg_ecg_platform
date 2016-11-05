package com.home.graphplot;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.home.graphplot.bluetooth.BluetoothSetup;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class Main extends AppCompatActivity {

    static BluetoothSetup btSetup;
    static Handler textHandler = new Handler();
    private static final int CONNECTION_ESTABLISHED = 1;

    private TextView tv_status_connection;
    private TextView viewDataCH1;
    private TextView viewDataCH2;
    private TextView viewDataCH3;
    private TextView viewDataCH4;
    private TextView viewDataCH5;
    private TextView viewDataCH6;
    private Button btn_connect;

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
        int amountBytes = 0;
        @Override
        public void run() {
            if (btSetup.isConnected() && isActivityRunning) {
                stoptime = System.currentTimeMillis();
                Log.d("tag", stoptime - starttime + "");
                starttime = System.currentTimeMillis();
                try {
                    inputStream = btSetup.getBtData();

                    if (inputStream.available() > 0) {

                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);
                        amountBytes =inputStream.read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);

                        viewDataCH1.setText("\n" + wrapper.getShort() + " ");
                        viewDataCH2.setText("\n" + wrapper.getShort() + " ");
                        viewDataCH3.setText("\n" + wrapper.getShort() + " ");
                        viewDataCH4.setText("\n" + wrapper.getShort() + " ");
                        viewDataCH5.setText("\n" + wrapper.getShort() + " ");
                        viewDataCH6.setText("\n" + wrapper.getShort() + " ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.w("Tag", "Text view running");
            }
            //Polling rate
            textHandler.post(this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_connect = (Button) findViewById(R.id.connect);
        btSetup = new BluetoothSetup();

        tv_status_connection = (TextView) findViewById(R.id.status);
        viewDataCH1 = (TextView) findViewById(R.id.viewDataCH1);
        viewDataCH2 = (TextView) findViewById(R.id.viewDataCH2);
        viewDataCH3 = (TextView) findViewById(R.id.viewDataCH3);
        viewDataCH4 = (TextView) findViewById(R.id.viewDataCH4);
        viewDataCH5 = (TextView) findViewById(R.id.viewDataCH5);
        viewDataCH6 = (TextView) findViewById(R.id.viewDataCH6);

        viewDataCH1.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH2.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH3.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH4.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH5.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH6.setMovementMethod(new ScrollingMovementMethod());

        isActivityRunning = true;

        setupTextViews4Click();

        //Fetch Data in own thread
        //Thread thread = new Thread(timerRunnable);
        //thread.start();

        timerRunnable.run();
        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btSetup.isConnected()) {
            try {
                btSetup.getBtSocket().close();
                isActivityRunning = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONNECTION_ESTABLISHED) {
            if (resultCode == RESULT_OK) {
                if(btSetup.isConnected()) {
                    tv_status_connection.setText(R.string.connected_true);
                    tv_status_connection.setBackgroundColor(Color.GREEN);
                    btn_connect.setText(R.string.btn_disconnect);
                    viewDataCH1.setText("");
                    viewDataCH2.setText("");
                    viewDataCH3.setText("");
                    viewDataCH4.setText("");
                    viewDataCH5.setText("");
                    viewDataCH6.setText("");
                }
            }
        }
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


    //OnClick
    public void showDeviceList(View view) throws IOException {
        if (!btSetup.isConnected()) {
            Intent showDevices = new Intent(this, ListViewDevices.class);
            startActivityForResult(showDevices, CONNECTION_ESTABLISHED);
        } else {
            btSetup.getBtData().close();
            btSetup.getBtSocket().close();
            btSetup.setConnected(false);
            tv_status_connection.setText(R.string.connected_false);
            tv_status_connection.setBackgroundColor(Color.RED);
            btn_connect.setText(R.string.btn_connect);
        }
    }

    public void showPlot(View view) {
        isActivityRunning = false;
        Intent viewPlot = new Intent(this, Plotting.class);
        startActivity(viewPlot);
    }

    private void setupTextViews4Click() {
        viewDataCH1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPlot(v);
                return true;
            }
        });
        viewDataCH2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPlot(v);
                return true;
            }
        });
        viewDataCH3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPlot(v);
                return true;
            }
        });
        viewDataCH4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPlot(v);
                return true;
            }
        });
        viewDataCH5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPlot(v);
                return true;
            }
        });
        viewDataCH6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPlot(v);
                return true;
            }
        });
    }

}
