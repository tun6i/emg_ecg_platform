package activity;

import android.app.Activity;;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import bluetooth.BluetoothSetup;
import csv.CSVSetup;
import de.fachstudie.fachstudie_template.R;

public class ExperimentFragment extends Fragment {
    private BluetoothSetup btSetup = BluetoothSetup.getInstance();
    private CSVSetup csvFile = CSVSetup.getInstance();

    private Button connect;
    /*private Button btnCreateNewCSV;
    private Button btnStartExperiment;
    private Button btnStopExperiment;
    private Button btnStartRotation;
    private Button btnStopRotation;
    private Button btnStartPush;
    private Button btnStopPush;
    private Button btnStartLiftUp;
    private Button btnStopLiftUp;*/
    TextView textView;

    /*private GraphView graphView;
    private LineGraphSeries<DataPoint> seriesCH1 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH2 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH3 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH4 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH5 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH6 = new LineGraphSeries<>();*/

    private final int CONNECTION_ESTABLISHED = 1;

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
        double x_Axis = 0;
        int amountBytes = 0;
        @Override
        public void run() {
            if (btSetup.isConnected()) {
                Log.w("Run", "Graph view running" + x_Axis);
                try {
                    inputStream = btSetup.getBtData();
                    stoptime = System.currentTimeMillis();
                    Log.w("Time", stoptime - starttime + "");
                    starttime = System.currentTimeMillis();
                    if (inputStream.available() > 0) {
                        textView.setText(inputStream.available()+"");
                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        amountBytes = inputStream.read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);


                        double tmpX_Axis = x_Axis;
                        short tmpCh1 = wrapper.getShort();
                        short tmpCh2 = wrapper.getShort();
                        short tmpCh3 = wrapper.getShort();
                        short tmpCh4 = wrapper.getShort();
                        short tmpCh5 = wrapper.getShort();
                        short tmpCh6 = wrapper.getShort();

                        /*seriesCH1.appendData(new DataPoint(tmpX_Axis, tmpCh1), true, 500);
                        seriesCH2.appendData(new DataPoint(tmpX_Axis, tmpCh2), true, 500);
                        seriesCH3.appendData(new DataPoint(tmpX_Axis, tmpCh3), true, 500);
                        seriesCH4.appendData(new DataPoint(tmpX_Axis, tmpCh4), true, 500);
                        seriesCH5.appendData(new DataPoint(tmpX_Axis, tmpCh5), true, 500);
                        seriesCH6.appendData(new DataPoint(tmpX_Axis, tmpCh6), true, 500);*/

                        //Schreibe alle Daten in die CSV
                        csvFile.appendRowToCSV(tmpX_Axis + ";" + tmpCh1 + ";" + tmpCh2 + ";"
                                + tmpCh3 + ";" + tmpCh4 + ";" + tmpCh5 + ";" + tmpCh6 + ";");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            x_Axis = x_Axis + 0.01;
            plotRunnableHandler.postDelayed(this, 15);
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
        //graphView = (GraphView) rootView.findViewById(R.id.graphExperiment);
        connect = (Button) rootView.findViewById(R.id.connectExperiment);
        textView = (TextView) rootView.findViewById(R.id.textView2);
        /*btnCreateNewCSV = (Button) rootView.findViewById(R.id.btnCreateNewCSV);
        btnStartExperiment = (Button) rootView.findViewById(R.id.btnStartExperiment);
        btnStopExperiment = (Button) rootView.findViewById(R.id.btnStopExperiment);
        btnStartRotation = (Button) rootView.findViewById(R.id.btnStartRotation);
        btnStopRotation = (Button) rootView.findViewById(R.id.btnStopRotation);
        btnStartPush = (Button) rootView.findViewById(R.id.btnStartPush);
        btnStopPush = (Button) rootView.findViewById(R.id.btnStopPush);
        btnStartLiftUp = (Button) rootView.findViewById(R.id.btnStartLiftUp);
        btnStopLiftUp = (Button) rootView.findViewById(R.id.btnStopLiftUp);*/

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
        //setupGraphView();
        // Inflate the layout for this fragment
        return rootView;
    }

    public void onResume() {
        super.onResume();
        if (btSetup.isConnected()) {
            plotRunnable.run();
            //plotRunnable.run();
            //plotRunnable.run();
            //plotRunnable.run();
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

    /*private void setupGraphView() {
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1023);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setScalable(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Amplitude");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Time");

        seriesCH1.setThickness(2);
        seriesCH2.setThickness(2);
        seriesCH3.setThickness(2);
        seriesCH4.setThickness(2);
        seriesCH5.setThickness(2);
        seriesCH6.setThickness(2);

        seriesCH1.setColor(Color.BLUE);
        seriesCH2.setColor(Color.RED);
        seriesCH3.setColor(Color.GREEN);
        seriesCH4.setColor(Color.GRAY);
        seriesCH5.setColor(Color.BLACK);
        seriesCH6.setColor(Color.MAGENTA);

        graphView.removeAllSeries();

        int numChannels = Settings.getInstance().getNumChannels();

        switch(numChannels) {
            case 1:
                graphView.addSeries(seriesCH1);
                break;
            case 2:
                graphView.addSeries(seriesCH1);
                graphView.addSeries(seriesCH2);
                break;
            case 3:
                graphView.addSeries(seriesCH1);
                graphView.addSeries(seriesCH2);
                graphView.addSeries(seriesCH3);
                break;
            case 4:
                graphView.addSeries(seriesCH1);
                graphView.addSeries(seriesCH2);
                graphView.addSeries(seriesCH3);
                graphView.addSeries(seriesCH4);
                break;
            case 5:
                graphView.addSeries(seriesCH1);
                graphView.addSeries(seriesCH2);
                graphView.addSeries(seriesCH3);
                graphView.addSeries(seriesCH4);
                graphView.addSeries(seriesCH5);
                break;
            case 6:
                graphView.addSeries(seriesCH1);
                graphView.addSeries(seriesCH2);
                graphView.addSeries(seriesCH3);
                graphView.addSeries(seriesCH4);
                graphView.addSeries(seriesCH5);
                graphView.addSeries(seriesCH6);
                break;
            default:
                graphView.addSeries(seriesCH1);
                graphView.addSeries(seriesCH2);
                graphView.addSeries(seriesCH3);
                graphView.addSeries(seriesCH4);
                graphView.addSeries(seriesCH5);
                graphView.addSeries(seriesCH6);
                break;
        }

        graphView.invalidate();
    }*/


}
