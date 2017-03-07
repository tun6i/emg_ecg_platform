package activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.display.DisplayManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Random;

import bluetooth.BluetoothSetup;
import de.fachstudie.fachstudie_template.R;

public class HeatmapFragment extends Fragment {

    private BluetoothSetup btSetup = BluetoothSetup.getInstance();
    private ImageButton imgButton;
    private Button buttonAdd;
    private Button buttonDel;
    private final int CONNECTION_ESTABLISHED = 1;
    CustomImageView imageViewArm1;
    CustomImageView imageViewArm2;

    Handler handler = new Handler();
    Runnable changeColors = new Runnable() {
        int g = 255;
        int r = 5;
        @Override
        public void run() {
            Random rnd = new Random();

            /*
            * 255...... 0......0 1023
            * 255..... 25......0 985
            * 255..... 50......0 960
            * 255..... 75......0 935
            * 255.....100......0 910
            * 255.....125......0 885
            * 255.....150......0 860
            * 255.....175......0 835
            * 255.....200......0 810
            * 255.....225......0 785
            * 255.....255......0 760
            * 225.....255......0 735
            * 200.....255......0 710
            * 175.....255......0 685
            * 250.....255......0 660
            * 125.....255......0 635
            * 100.....255......0 610
            *  75.....255......0 585
            *  50.....255......0 560
            *  25.....255......0 535
            *   0.....255......0 510
             */
            imageViewArm1.setColor(r, g, 0, imageViewArm1.circle1);
            imageViewArm1.setColor(r, g, 255,imageViewArm1.circle2);
            imageViewArm1.invalidate();
            imageViewArm2.invalidate();
            handler.postDelayed(this, 100);
            if (r < 250) {
                r+=25;
            } else if (g > 5) {
                g-=25;
            } else {
                g = 255;
                r = 5;
            }

        }
    };

    public HeatmapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_heatmap, container, false);
        imgButton = (ImageButton) rootView.findViewById(R.id.imgbutton2);
        buttonAdd = (Button) rootView.findViewById(R.id.button_add);
        buttonDel = (Button) rootView.findViewById(R.id.button_del);
        imageViewArm1 = (CustomImageView) rootView.findViewById(R.id.customImageView);
        imageViewArm2 = (CustomImageView) rootView.findViewById(R.id.customImageView2);
        //imageViewArm1.setImageResource(R.drawable.unterarm1);
        //imageViewArm2.setImageResource(R.drawable.unterarm2);

        imageViewArm1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageViewArm1.x = event.getX();
                imageViewArm1.y = event.getY();
                return false;
            }
        });

        imageViewArm2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageViewArm2.x = event.getX();
                imageViewArm2.y = event.getY();
                return false;
            }
        });


        changeColors.run();

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btSetup.isConnected()) {
                    Intent intent = new Intent(getActivity(), ShowPairedDevices.class);
                    startActivityForResult(intent, CONNECTION_ESTABLISHED);
                } else {
                    try {
                        btSetup.getBtData().close();
                        btSetup.getBtSocket().close();
                        btSetup.setConnected(false);
                        imgButton.setImageResource(R.drawable.ic_bluetooth_no_connection);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewArm1.channels < 6) {
                    imageViewArm1.channels += 1;
                }
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageViewArm1.channels > 1) {
                    imageViewArm1.channels -= 1;
                }
            }
        });


        return rootView;
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
