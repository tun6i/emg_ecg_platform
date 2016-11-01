package com.home.graphplot;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class GetDataThread extends Thread {
    private Handler timerHandler = new Handler();
    private byte[] buffer = new byte[2];
    private int[] cache = new int[6];
    Handler uiHandler;

    public GetDataThread(Handler handler) {
        uiHandler = handler;
    }

    @Override
    public void run() {
        final int channels = 6;
        ByteBuffer wrapper;
        if (Main.btSetup.isConnected()) {
            try {
                if (Main.btSetup.getBtData().available() > 0) {
                    do {
                        Main.btSetup.getBtData().read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);
                    } while(wrapper.getShort() != 1337);

                    for (int actualCH = 1; actualCH <= channels; actualCH++) {
                       Main.btSetup.getBtData().read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);
                        short number = wrapper.getShort();
                        Message msg = uiHandler.obtainMessage();
                        msg.what = 1337;
                        msg.arg1 = actualCH;
                        msg.arg2 = number;
                        uiHandler.sendMessage(msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Polling rate
        uiHandler.post(this);
    }

    public void setViewHandler(Handler handler) {
        uiHandler = handler;
    }
}
