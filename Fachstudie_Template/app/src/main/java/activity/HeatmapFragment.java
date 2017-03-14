package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Random;

import bluetooth.BluetoothSetup;
import de.fachstudie.fachstudie_template.R;

public class HeatmapFragment extends Fragment {

    private final int CONNECTION_ESTABLISHED = 1;
    CustomImageView imageViewArm1;
    int imageIndex = 0;
    int[] imageIds = {R.drawable.arm_left_posterior, R.drawable.armleft, R.drawable.armright, R.drawable.bein, R.drawable.unterarm1, R.drawable.unterarm2};
    Handler handler = new Handler();
    Runnable changeColors = new Runnable() {
        int g = 255;
        int r = 5;

        @Override
        public void run() {
            Random rnd = new Random();

            /*
            * 255...... 0......0 1023 0
            * 255..... 25......0 985 25
            * 255..... 50......0 960 50
            * 255..... 75......0 935 75
            * 255.....100......0 910 100
            * 255.....125......0 885 125
            * 255.....150......0 860 150
            * 255.....175......0 835 175
            * 255.....200......0 810 200
            * 255.....225......0 785 225
            * 255.....255......0 760 250
            * 225.....255......0 735 275
            * 200.....255......0 710 300
            * 175.....255......0 685 325
            * 150.....255......0 660 350
            * 125.....255......0 635 375
            * 100.....255......0 610 400
            *  75.....255......0 585 425
            *  50.....255......0 560 450
            *  25.....255......0 535 475
            *   0.....255......0 510 500
             */
            CustomImageView.setColor(r, g, 0, CustomImageView.circle1);
            CustomImageView.setColor(r, g, 255, CustomImageView.circle2);
            imageViewArm1.invalidate();
            handler.postDelayed(this, 100);
            if (r < 250) {
                r += 25;
            } else if (g > 5) {
                g -= 25;
            } else {
                g = 255;
                r = 5;
            }

        }
    };
    Handler heatmapHandler = new Handler();
    private BluetoothSetup btSetup = BluetoothSetup.getInstance();
    private ImageSwitcher imageSwitcher;
    private Button buttonNext;
    private Button buttonPrev;
    // Buffer for building EMG value
    // contains highByte & lowByte of an Integer
    private byte[] buffer = new byte[12];
    private Runnable heatmapRunnable = new Runnable() {
        ByteBuffer wrapper;
        InputStream inputStream;
        int amountBytes = 0;

        @Override
        public void run() {
            if (btSetup.isConnected()) {
                try {
                    inputStream = btSetup.getBtData();
                    if (inputStream.available() > 14) {
                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        amountBytes = inputStream.read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);

                        short tmpCh1 = wrapper.getShort();
                        short tmpCh2 = wrapper.getShort();
                        short tmpCh3 = wrapper.getShort();
                        short tmpCh4 = wrapper.getShort();
                        short tmpCh5 = wrapper.getShort();
                        short tmpCh6 = wrapper.getShort();


                        if (tmpCh1 < 25) {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 50) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 75) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 100) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 125) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 155) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 175) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 200) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 225) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 250) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 275) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 300) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 325) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 350) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 375) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 400) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 425) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 450) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 475) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 509) {
                            CustomImageView.setColor(0, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 535) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 560) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 585) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 610) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 635) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 660) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 685) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 710) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 735) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 760) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 785) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 810) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 835) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 860) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 885) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 910) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 935) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 985) {
                            CustomImageView.setColor(255, 50, 0, CustomImageView.circle1);
                        } else if (tmpCh1 < 1010) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle1);
                        } else {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle1);
                        }

                        if (tmpCh2 < 25) {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 50) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 75) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 100) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 125) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 155) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 175) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 200) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 225) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 250) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 275) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 300) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 325) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 350) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 375) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 400) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 425) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 450) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 475) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 509) {
                            CustomImageView.setColor(0, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 535) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 560) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 585) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 610) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 635) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 660) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 685) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 710) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 735) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 760) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 785) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 810) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 835) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 860) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 885) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 910) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 935) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 985) {
                            CustomImageView.setColor(255, 50, 0, CustomImageView.circle2);
                        } else if (tmpCh2 < 1010) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle2);
                        } else {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle2);
                        }

                        if (tmpCh3 < 25) {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 50) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 75) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 100) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 125) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 155) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 175) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 200) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 225) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 250) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 275) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 300) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 325) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 350) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 375) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 400) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 425) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 450) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 475) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 509) {
                            CustomImageView.setColor(0, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 535) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 560) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 585) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 610) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 635) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 660) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 685) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 710) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 735) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 760) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 785) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 810) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 835) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 860) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 885) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 910) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 935) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 985) {
                            CustomImageView.setColor(255, 50, 0, CustomImageView.circle3);
                        } else if (tmpCh3 < 1010) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle3);
                        } else {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle3);
                        }

                        if (tmpCh4 < 25) {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 50) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 75) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 100) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 125) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 155) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 175) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 200) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 225) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 250) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 275) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 300) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 325) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 350) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 375) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 400) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 425) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 450) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 475) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 509) {
                            CustomImageView.setColor(0, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 535) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 560) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 585) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 610) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 635) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 660) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 685) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 710) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 735) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 760) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 785) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 810) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 835) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 860) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 885) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 910) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 935) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 985) {
                            CustomImageView.setColor(255, 50, 0, CustomImageView.circle4);
                        } else if (tmpCh4 < 1010) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle4);
                        } else {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle4);
                        }

                        if (tmpCh5 < 25) {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 50) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 75) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 100) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 125) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 155) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 175) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 200) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 225) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 250) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 275) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 300) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 325) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 350) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 375) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 400) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 425) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 450) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 475) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 509) {
                            CustomImageView.setColor(0, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 535) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 560) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 585) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 610) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 635) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 660) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 685) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 710) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh5 < 735) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 760) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 785) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 810) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 835) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 860) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 885) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 910) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 935) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 985) {
                            CustomImageView.setColor(255, 50, 0, CustomImageView.circle5);
                        } else if (tmpCh5 < 1010) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle5);
                        } else {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle5);
                        }

                        if (tmpCh6 < 25) {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 50) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 75) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 100) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 125) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 155) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 175) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 200) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 225) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 250) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 275) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 300) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 325) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 350) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 375) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 400) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 425) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 450) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 475) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 509) {
                            CustomImageView.setColor(0, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 535) {
                            CustomImageView.setColor(25, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 560) {
                            CustomImageView.setColor(50, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 585) {
                            CustomImageView.setColor(75, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 610) {
                            CustomImageView.setColor(100, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 635) {
                            CustomImageView.setColor(125, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 660) {
                            CustomImageView.setColor(150, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 685) {
                            CustomImageView.setColor(175, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 710) {
                            CustomImageView.setColor(200, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 735) {
                            CustomImageView.setColor(225, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 760) {
                            CustomImageView.setColor(255, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 785) {
                            CustomImageView.setColor(255, 225, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 810) {
                            CustomImageView.setColor(255, 200, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 835) {
                            CustomImageView.setColor(255, 175, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 860) {
                            CustomImageView.setColor(255, 150, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 885) {
                            CustomImageView.setColor(255, 125, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 910) {
                            CustomImageView.setColor(255, 100, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 935) {
                            CustomImageView.setColor(255, 75, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 985) {
                            CustomImageView.setColor(255, 50, 0, CustomImageView.circle6);
                        } else if (tmpCh6 < 1010) {
                            CustomImageView.setColor(255, 25, 0, CustomImageView.circle6);
                        } else {
                            CustomImageView.setColor(255, 0, 0, CustomImageView.circle6);
                        }


                        imageViewArm1.invalidate();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            heatmapHandler.post(this);
        }
    };

    public HeatmapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_heatmap, container, false);
        imageSwitcher = (ImageSwitcher) rootView.findViewById(R.id.image_switcher);
        imageSwitcher.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        buttonNext = (Button) rootView.findViewById(R.id.button_next);
        buttonPrev = (Button) rootView.findViewById(R.id.button_prev);
        imageIndex = 0;

        //imageViewArm1.setImageResource(R.drawable.unterarm1);
        //imageViewArm2.setImageResource(R.drawable.unterarm2);


        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                imageViewArm1 = new CustomImageView(getContext());
                imageViewArm1.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewArm1.setLayoutParams(new
                        ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));

                imageViewArm1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        CustomImageView.x = event.getX();
                        CustomImageView.y = event.getY();
                        return false;
                    }
                });
                return imageViewArm1;
            }
        });
        imageSwitcher.setImageResource(imageIds[imageIndex]);


        /*final Animation inLeft = AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.slide_in_left);
        final Animation outRight = AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.slide_out_right);

        final Animation inRight = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.slide_in_right);
        final Animation outLeft = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.slide_out_left);*/


        heatmapRunnable.run();
        heatmapRunnable.run();
        //heatmapRunnable.run();
        //heatmapRunnable.run();



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageIndex < 5) {
                    //imageSwitcher.setInAnimation(inLeft);
                    //imageSwitcher.setOutAnimation(outRight);
                    imageIndex++;
                    imageSwitcher.setImageResource(imageIds[imageIndex]);
                }
                imageSwitcher.invalidate();
                //mageViewArm1.channels = 0;

            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageSwitcher.setInAnimation(inRight);
                //imageSwitcher.setOutAnimation(outLeft);
                if (imageIndex > 0) {
                    imageIndex--;
                    imageSwitcher.setImageResource(imageIds[imageIndex]);
                }
                imageSwitcher.invalidate();
                //imageViewArm1.channels = 0;
            }
        });


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_electrode) {
            if (CustomImageView.channels < 6) {
                CustomImageView.channels += 1;
            }
        } else if (id == R.id.del_electrode) {
            if (CustomImageView.channels > 1) {
                CustomImageView.channels -= 1;
            }
        } else if (id == R.id.connect) {
            if (!btSetup.isConnected()) {
                Intent intent = new Intent(getActivity(), ShowPairedDevices.class);
                startActivityForResult(intent, CONNECTION_ESTABLISHED);
                item.setTitle("Disconnect");
                item.setIcon(R.drawable.ic_bluetooth_connection);
            } else {
                try {
                    btSetup.getBtData().close();
                    btSetup.getBtSocket().close();
                    btSetup.setConnected(false);
                    item.setTitle("Connect");
                    item.setIcon(R.drawable.ic_bluetooth_no_connection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
