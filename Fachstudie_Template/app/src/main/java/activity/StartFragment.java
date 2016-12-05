package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import bluetooth.BluetoothSetup;
import de.fachstudie.fachstudie_template.R;

public class StartFragment extends Fragment {

    private ImageButton imgButton;

    private final int CONNECTION_ESTABLISHED = 1;
    private BluetoothSetup btSetup = BluetoothSetup.getInstance();


    private GraphView graphView;
    private LineGraphSeries<DataPoint> seriesCH1 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH2 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH3 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH4 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH5 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH6 = new LineGraphSeries<>();

    // Buffer for building EMG value
    // contains highByte & lowByte of an Integer
    private byte[] buffer = new byte[12];

    //Debug
    long starttime = System.currentTimeMillis();
    long stoptime = System.currentTimeMillis();

    private Handler plotRunnableHandler = new Handler();
    private Runnable plotRunnable = new Runnable() {
        ByteBuffer wrapper;
        InputStream inputStream;
        float x_Axis = 0;
        int amountBytes = 0;
        @Override
        public void run() {
            if (btSetup.isConnected()) {
                //Log.w("Run", "Graph view running");
                try {
                    inputStream = btSetup.getBtData();
                    stoptime = System.currentTimeMillis();
                    Log.w("Time", stoptime - starttime + "");
                    starttime = System.currentTimeMillis();
                    if (inputStream.available() > 0) {
                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        amountBytes = inputStream.read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);
                        seriesCH1.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH2.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH3.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH4.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH5.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                        seriesCH6.appendData(new DataPoint(x_Axis, wrapper.getShort()), true, 100);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            x_Axis = x_Axis + 0.01f;
            plotRunnableHandler.post(this);

        }
    };

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_start, container, false);

        // Inflate the layout for this fragment
        imgButton = (ImageButton) rootView.findViewById(R.id.imgbutton);
        graphView = (GraphView) rootView.findViewById(R.id.graph1);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);

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

        seriesCH1.setThickness(3);
        seriesCH2.setThickness(3);
        seriesCH3.setThickness(3);
        seriesCH4.setThickness(3);
        seriesCH5.setThickness(3);
        seriesCH6.setThickness(3);

        seriesCH1.setColor(Color.BLUE);
        seriesCH2.setColor(Color.RED);
        seriesCH3.setColor(Color.GREEN);
        seriesCH4.setColor(Color.GRAY);
        seriesCH5.setColor(Color.BLACK);
        seriesCH6.setColor(Color.MAGENTA);

        graphView.addSeries(seriesCH1);
        graphView.addSeries(seriesCH2);
        graphView.addSeries(seriesCH3);
        graphView.addSeries(seriesCH4);
        graphView.addSeries(seriesCH5);
        graphView.addSeries(seriesCH6);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONNECTION_ESTABLISHED && resultCode == Activity.RESULT_OK) {
            imgButton.setImageResource(R.drawable.ic_bluetooth_connection);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        plotRunnableHandler.removeCallbacks(plotRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        plotRunnableHandler.removeCallbacks(plotRunnable);

    }

    @Override
    public void onResume() {
        super.onResume();
        plotRunnable.run();
        plotRunnable.run();
        plotRunnable.run();
        plotRunnable.run();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (!btSetup.isConnected()) {
            imgButton.setImageResource(R.drawable.ic_bluetooth_no_connection);
        } else {
            imgButton.setImageResource(R.drawable.ic_bluetooth_connection);
        }

    }
}
