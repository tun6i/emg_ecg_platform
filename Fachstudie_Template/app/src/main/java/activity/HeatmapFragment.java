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
    private short[] filterBuffer = new short[5];
    private int filterBufferIndex = 0;
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




                        if (tmpCh1 <= 255) {
                            CustomImageView.setColor(255, tmpCh1, 0, CustomImageView.circle1);
                        } else if (tmpCh1 >= 256 && tmpCh1 <= 511) {
                            CustomImageView.setColor(511 - tmpCh1, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 >= 512 && tmpCh1 <=  767) {
                            CustomImageView.setColor(tmpCh1 - 512, 255, 0, CustomImageView.circle1);
                        } else {
                            CustomImageView.setColor(255, 1023 - tmpCh1, 0, CustomImageView.circle1);
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
