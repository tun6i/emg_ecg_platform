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
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Random;

import bluetooth.BluetoothSetup;
import de.fachstudie.fachstudie_template.R;

public class HeatmapFragment extends Fragment {

    private BluetoothSetup btSetup = BluetoothSetup.getInstance();
    private ImageButton imgButton;
    private final int CONNECTION_ESTABLISHED = 1;
    CustomImageView imageViewArm1;
    CustomImageView imageViewArm2;

    Handler handler = new Handler();
    Runnable changeColors = new Runnable() {
        @Override
        public void run() {
            Random rnd = new Random();
            imageViewArm1.paint.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            imageViewArm1.invalidate();
            handler.postDelayed(this, 700);
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
