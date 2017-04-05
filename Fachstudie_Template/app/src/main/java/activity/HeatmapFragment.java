package activity;

import android.app.Activity;
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

import bluetooth.BluetoothSetup;
import de.fachstudie.fachstudie_template.R;

public class HeatmapFragment extends Fragment {

    private final int CONNECTION_ESTABLISHED = 1;
    CustomImageView imageViewArm1;
    int imageIndex = 0;
    int[] imageIds = {R.drawable.arm_left_posterior, R.drawable.armleft, R.drawable.armright, R.drawable.bein, R.drawable.unterarm1, R.drawable.unterarm2};

    Handler heatmapHandler = new Handler();
    private BluetoothSetup btSetup = BluetoothSetup.getInstance();
    //private BluetoothSetup btSetup;
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

                        if (tmpCh1 <= 255) {
                            CustomImageView.setColor(255, tmpCh1, 0, CustomImageView.circle1);
                        } else if (tmpCh1 >= 256 && tmpCh1 <= 511) {
                            CustomImageView.setColor(511 - tmpCh1, 255, 0, CustomImageView.circle1);
                        } else if (tmpCh1 >= 512 && tmpCh1 <=  767) {
                            CustomImageView.setColor(tmpCh1 - 512, 255, 0, CustomImageView.circle1);
                        } else {
                            CustomImageView.setColor(255, 1023 - tmpCh1, 0, CustomImageView.circle1);
                        }

                        if (tmpCh2 <= 255) {
                            CustomImageView.setColor(255, tmpCh2, 0, CustomImageView.circle2);
                        } else if (tmpCh2 >= 256 && tmpCh2 <= 511) {
                            CustomImageView.setColor(511 - tmpCh2, 255, 0, CustomImageView.circle2);
                        } else if (tmpCh2 >= 512 && tmpCh2 <=  767) {
                            CustomImageView.setColor(tmpCh2 - 512, 255, 0, CustomImageView.circle2);
                        } else {
                            CustomImageView.setColor(255, 1023 - tmpCh2, 0, CustomImageView.circle2);
                        }

                        if (tmpCh3 <= 255) {
                            CustomImageView.setColor(255, tmpCh3, 0, CustomImageView.circle3);
                        } else if (tmpCh3 >= 256 && tmpCh3 <= 511) {
                            CustomImageView.setColor(511 - tmpCh3, 255, 0, CustomImageView.circle3);
                        } else if (tmpCh3 >= 512 && tmpCh3 <=  767) {
                            CustomImageView.setColor(tmpCh3 - 512, 255, 0, CustomImageView.circle3);
                        } else {
                            CustomImageView.setColor(255, 1023 - tmpCh3, 0, CustomImageView.circle3);
                        }

                        if (tmpCh4 <= 255) {
                            CustomImageView.setColor(255, tmpCh4, 0, CustomImageView.circle4);
                        } else if (tmpCh4 >= 256 && tmpCh4 <= 511) {
                            CustomImageView.setColor(511 - tmpCh4, 255, 0, CustomImageView.circle4);
                        } else if (tmpCh4 >= 512 && tmpCh4 <=  767) {
                            CustomImageView.setColor(tmpCh4 - 512, 255, 0, CustomImageView.circle4);
                        } else {
                            CustomImageView.setColor(255, 1023 - tmpCh4, 0, CustomImageView.circle4);
                        }

                        if (tmpCh5 <= 255) {
                            CustomImageView.setColor(255, tmpCh5, 0, CustomImageView.circle5);
                        } else if (tmpCh5 >= 256 && tmpCh5 <= 511) {
                            CustomImageView.setColor(511 - tmpCh5, 255, 0, CustomImageView.circle5);
                        } else if (tmpCh5 >= 512 && tmpCh5 <=  767) {
                            CustomImageView.setColor(tmpCh5 - 512, 255, 0, CustomImageView.circle5);
                        } else {
                            CustomImageView.setColor(255, 1023 - tmpCh5, 0, CustomImageView.circle5);
                        }

                        if (tmpCh6 <= 255) {
                            CustomImageView.setColor(255, tmpCh6, 0, CustomImageView.circle6);
                        } else if (tmpCh6 >= 256 && tmpCh6 <= 511) {
                            CustomImageView.setColor(511 - tmpCh5, 255, 0, CustomImageView.circle6);
                        } else if (tmpCh6 >= 512 && tmpCh6 <=  767) {
                            CustomImageView.setColor(tmpCh6 - 512, 255, 0, CustomImageView.circle6);
                        } else {
                            CustomImageView.setColor(255, 1023 - tmpCh6, 0, CustomImageView.circle6);
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

        heatmapRunnable.run();
        heatmapRunnable.run();


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageIndex < 5) {
                    imageViewArm1.initialize();
                    imageIndex++;
                    imageSwitcher.setImageResource(imageIds[imageIndex]);
                }
                imageSwitcher.invalidate();

            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageIndex > 0) {
                    imageViewArm1.initialize();
                    imageIndex--;
                    imageSwitcher.setImageResource(imageIds[imageIndex]);
                }
                imageSwitcher.invalidate();
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
                if (btSetup.isConnected()) {
                    item.setTitle("Disconnect");
                    item.setIcon(R.drawable.ic_bluetooth_connection);
                }
            } else {
                try {
                    btSetup.getBtData().close();
                    btSetup.getBtSocket().close();
                    btSetup.setConnected(false);
                    if (!btSetup.isConnected()) {
                        item.setTitle("Connect");
                        item.setIcon(R.drawable.ic_bluetooth_no_connection);
                    }
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
