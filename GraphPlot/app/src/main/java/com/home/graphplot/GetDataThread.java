package com.home.graphplot;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class GetDataThread implements Runnable {
    private Handler timerHandler = new Handler();
    private byte[] buffer = new byte[2];
    private int[] cache = new int[6];
    private AtomicIntegerArray valuePacket  = new AtomicIntegerArray(cache);




    @Override
    public void run() {
        final int channels = 6;
        if (Main.btSetup.isConnected()) {
            try {
                if (Main.btSetup.getBtData().available() > 0) {
                    for (int actualCH = 1; actualCH <= channels; actualCH++) {
                        Main.btSetup.getBtData().read(buffer);
                        ByteBuffer wrapper = ByteBuffer.wrap(buffer);
                        short number = wrapper.getShort();
                        switch (actualCH) {
                            case 1:
                                valuePacket.set(0, number);
                            case 2:
                                valuePacket.set(1, number);
                                break;
                            case 3:
                                valuePacket.set(2, number);
                                break;
                            case 4:
                                valuePacket.set(3, number);
                                break;
                            case 5:
                                valuePacket.set(4, number);
                                break;
                            case 6:
                                valuePacket.set(5, number);
                                break;
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Polling rate
        timerHandler.postDelayed(this, 10);
        Log.d("Tag", "Data retrieved");
    }

    public AtomicIntegerArray getValue() {
        return valuePacket;
    }
}
