package com.home.graphplot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.home.graphplot.bluetooth.BluetoothSetup;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class Main extends AppCompatActivity {

    private static final int CONNECTION_ESTABLISHED = 1;
    static BluetoothSetup btSetup;
    private TextView tv_status;
    private TextView viewDataCH1;
    private TextView viewDataCH2;
    private TextView viewDataCH3;
    private TextView viewDataCH4;
    private TextView viewDataCH5;
    private TextView viewDataCH6;
    private Button btn_connect;
    private Handler timerHandler = new Handler();
    //private Bundle state;



    // Buffer for building EMG value
    // contains highByte & lowByte of an Integer

    private Runnable timerRunnable = new Runnable() {
        byte[] buffer = new byte[2];
        @Override
        public void run() {
            final int channels = 6;
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
                                    viewDataCH1.append("\n" + number + " ");
                                    break;
                                case 2:
                                    viewDataCH2.append("\n" + number + " ");
                                    break;
                                case 3:
                                    viewDataCH3.append("\n" + number + " ");
                                    break;
                                case 4:
                                    viewDataCH4.append("\n" + number + " ");
                                    break;
                                case 5:
                                    viewDataCH5.append("\n" + number + " ");
                                    break;
                                case 6:
                                    viewDataCH6.append("\n" + number + " ");
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
        setContentView(R.layout.activity_main);
        viewDataCH1 = (TextView) findViewById(R.id.viewDataCH1);
        viewDataCH2 = (TextView) findViewById(R.id.viewDataCH2);
        viewDataCH3 = (TextView) findViewById(R.id.viewDataCH3);
        viewDataCH4 = (TextView) findViewById(R.id.viewDataCH4);
        viewDataCH5 = (TextView) findViewById(R.id.viewDataCH5);
        viewDataCH6 = (TextView) findViewById(R.id.viewDataCH6);

        tv_status = (TextView) findViewById(R.id.status);
        btn_connect = (Button) findViewById(R.id.connect);
        btSetup = new BluetoothSetup();
        viewDataCH1.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH2.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH3.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH4.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH5.setMovementMethod(new ScrollingMovementMethod());
        viewDataCH6.setMovementMethod(new ScrollingMovementMethod());

        //state = new Bundle();
        timerRunnable.run();
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
    protected void onDestroy() {
        super.onDestroy();
        if (btSetup.isConnected()) {
            try {
                btSetup.getBtSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONNECTION_ESTABLISHED) {
            if (resultCode == RESULT_OK) {
                if(btSetup.isConnected()) {
                    tv_status.setText(R.string.connected_true);
                    tv_status.setBackgroundColor(Color.GREEN);
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

    public void showDeviceList(View view) throws IOException {
        if (!btSetup.isConnected()) {
            Intent showDevices = new Intent(this, ListViewDevices.class);
            startActivityForResult(showDevices, CONNECTION_ESTABLISHED);
        } else {
            btSetup.getBtSocket().close();
            btSetup.setConnected(false);
            tv_status.setText(R.string.connected_false);
            tv_status.setBackgroundColor(Color.RED);
            btn_connect.setText(R.string.btn_connect);

        }
    }

    public void showPlot(View view) {
        Intent viewPlot = new Intent(this, Plotting.class);
        startActivity(viewPlot);
    }

}
