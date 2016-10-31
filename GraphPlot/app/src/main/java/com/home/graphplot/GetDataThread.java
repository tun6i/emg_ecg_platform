package com.home.graphplot;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class GetDataThread implements Runnable {
    private Handler timerHandler;
    private byte[] buffer = new byte[2];
    private int[] cache = new int[1];
    private AtomicIntegerArray valuePacket  = new AtomicIntegerArray(cache);
    private Handler uiHandler;

    public GetDataThread() {
        timerHandler = new Handler();
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
                        //Main.btSetup.getBtData().read(buffer);
                        //wrapper = ByteBuffer.wrap(buffer);
                        //short number = wrapper.getShort();
                        switch (actualCH) {
                            case 1:
                                //valuePacket.set(0, number);
                                Message msg = uiHandler.obtainMessage();
                                msg.what = 1337;
                                //msg.obj = valuePacket;
                                uiHandler.sendMessage(msg);
                            case 2:
                                //valuePacket.set(1, number);
                                break;
                            case 3:
                                //valuePacket.set(2, number);
                                break;
                            case 4:
                                //valuePacket.set(3, number);
                                break;
                            case 5:
                                //valuePacket.set(4, number);
                                break;
                            case 6:
                                //valuePacket.set(5, number);
                                break;
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Polling rate
        timerHandler.postDelayed(this, 5);
    }

    public AtomicIntegerArray getValue() {
        return valuePacket;
    }

    public void setViewHandler(Handler handler) {
        uiHandler = handler;
    }
}
