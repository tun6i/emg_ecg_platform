package com.home.graphplot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.home.graphplot.bluetooth.BluetoothSetup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;


public class Main extends AppCompatActivity {

    private static final int CONNECTION_ESTABLISHED = 1;
    static BluetoothSetup btSetup;
    private TextView tv_status;
    private TextView viewData;
    private Button btn_connect;
    private Handler timerHandler = new Handler();
    private Bundle state;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (btSetup.isConnected()) {
                try {
                    InputStream inputStream = btSetup.getBtSocket().getInputStream();
                    //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ASCII"));
                    String line;
                    if (inputStream.available() > 0) {
                        /*if (( line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                            Integer dataValue = Integer.parseInt(line);
                            long millis = System.currentTimeMillis();
                                viewData.append("\n" + dataValue + " " + millis);
                        }*/
                        byte[] buffer = new byte[2];
                        inputStream.read(buffer);
                        ByteBuffer wrapper = ByteBuffer.wrap(buffer);
                        short number = wrapper.getShort();
                        //viewData.append("\n" + inputStream.read() + "");
                        viewData.append("\n" + number + " ");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Polling rate
            timerHandler.postDelayed(this, 10);
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
        state = new Bundle();
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
                    viewData.setText("");
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
