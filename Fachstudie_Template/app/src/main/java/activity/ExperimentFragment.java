package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import bluetooth.BluetoothSetup;
import csv.CSVSetup;
import de.fachstudie.fachstudie_template.R;

/**
 * Class for "Experiment" view
 */
public class ExperimentFragment extends Fragment {

    // Get Bluetooth adapter
    private BluetoothSetup btSetup = BluetoothSetup.getInstance();

    // Get CSV adapter
    private CSVSetup csvFile = CSVSetup.getInstance();

    private Button connect;

    // Bytes available connection feedback
    TextView textView;

    // Result code
    private final int CONNECTION_ESTABLISHED = 1;

    /* Buffer for building EMG value
     * Contains highByte & lowByte of an Integer
     * Resulting in 6 Integers
     */
    private byte[] buffer = new byte[12];

    // Time
    double tmpX_Axis;

    // Thread runnable and handler
    private Handler plotRunnableHandler = new Handler();
    private Runnable plotRunnable = new Runnable() {
        ByteBuffer wrapper;
        InputStream inputStream;
        double x_Axis = 0;
        int amountBytes = 0;
        @Override
        public void run() {
            if (btSetup.isConnected()) {
                try {

                    // Get input stream from Bluetooth
                    inputStream = btSetup.getBtData();

                    // If data (bytes) is available
                    if (inputStream.available() > 0) {
                        textView.setText(inputStream.available()+"");

                        /*
                        * Synchronize input stream
                        * A Checksum 1337 marks beginning of a valid byte package
                        */
                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        // Read valid package into the buffer
                        amountBytes = inputStream.read(buffer);

                        // Wrapper for conversion into values
                        wrapper = ByteBuffer.wrap(buffer);

                        // Get values
                        tmpX_Axis = x_Axis;
                        short tmpCh1 = wrapper.getShort();
                        short tmpCh2 = wrapper.getShort();
                        short tmpCh3 = wrapper.getShort();
                        short tmpCh4 = wrapper.getShort();
                        short tmpCh5 = wrapper.getShort();
                        short tmpCh6 = wrapper.getShort();


                        // Write to CSV file
                        csvFile.appendRowToCSV(tmpX_Axis + ";" + tmpCh1 + ";" + tmpCh2 + ";"
                                + tmpCh3 + ";" + tmpCh4 + ";" + tmpCh5 + ";" + tmpCh6 + ";");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            x_Axis = x_Axis + 0.01;
            plotRunnableHandler.post(this);
        }
    };

    /**
     * Default constructor
     */
    public ExperimentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * Setup view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_experiment, container, false);
        // Inflate the layout for this fragment

        // Get UI elements
        connect = (Button) rootView.findViewById(R.id.connectExperiment);
        textView = (TextView) rootView.findViewById(R.id.textView2);

        // Set click listener
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btSetup.isConnected()) {
                    // Open Bluetooth device list and connect
                    Intent intent = new Intent(getActivity(), ShowPairedDevices.class);
                    startActivityForResult(intent, CONNECTION_ESTABLISHED);
                } else {
                    try {

                        // Disconnect
                        btSetup.getBtData().close();
                        btSetup.getBtSocket().close();
                        btSetup.setConnected(false);
                        plotRunnableHandler.removeCallbacks(plotRunnable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    public void onResume() {
        super.onResume();
        // Start runnable
        if (btSetup.isConnected()) {
            plotRunnable.run();
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
}