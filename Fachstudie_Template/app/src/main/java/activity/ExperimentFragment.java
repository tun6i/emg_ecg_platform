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

public class ExperimentFragment extends Fragment {
    private BluetoothSetup btSetup = BluetoothSetup.getInstance();
    private CSVSetup csvFile = CSVSetup.getInstance();

    protected Button connect;
    TextView textView;
    private final int CONNECTION_ESTABLISHED = 1;

    // Buffer for building EMG value
    // contains highByte & lowByte of an Integer
    private byte[] buffer = new byte[12];

    double tmpX_Axis;

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
                    inputStream = btSetup.getBtData();
                    if (inputStream.available() > 0) {
                        textView.setText(inputStream.available()+ R.string.placeholder);
                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        amountBytes = inputStream.read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);


                        tmpX_Axis = x_Axis;
                        short tmpCh1 = wrapper.getShort();
                        short tmpCh2 = wrapper.getShort();
                        short tmpCh3 = wrapper.getShort();
                        short tmpCh4 = wrapper.getShort();
                        short tmpCh5 = wrapper.getShort();
                        short tmpCh6 = wrapper.getShort();

                        //Schreibe alle Daten in die CSV
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

    public ExperimentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_experiment, container, false);
        // Inflate the layout for this fragment
        connect = (Button) rootView.findViewById(R.id.connectExperiment);
        textView = (TextView) rootView.findViewById(R.id.textView2);

        connect.setOnClickListener(new View.OnClickListener() {
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