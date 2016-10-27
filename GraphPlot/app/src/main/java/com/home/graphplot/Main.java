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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main extends AppCompatActivity {

    private static final int CONNECTION_ESTABLISHED = 1;
    static BluetoothSetup btSetup;
    private TextView tv_status;
    private TextView viewData;
    private Button btn_connect;
    private Bundle state;
    private Handler timerHandler = new Handler();

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (btSetup.isConnected()) {
                String line;
                try {
                    InputStream inputStream = btSetup.getBtSocket().getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ASCII"));
                    if (inputStream.available() > 0) {
                        if (( line = bufferedReader.readLine()) != null) {
                            
                            viewData.append("\n" + line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            timerHandler.postDelayed(this, 200);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewData = (TextView) findViewById(R.id.viewData);
        tv_status = (TextView) findViewById(R.id.status);
        btn_connect = (Button) findViewById(R.id.connect);
        btSetup = new BluetoothSetup();
        viewData.setMovementMethod(new ScrollingMovementMethod());
        //state = new Bundle();
        timerRunnable.run();
        }

    /*@Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(state);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRestoreInstanceState(state);
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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








}
