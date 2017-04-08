package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import bluetooth.BluetoothSetup;
import csv.CSVSetup;
import de.fachstudie.fachstudie_template.R;

public class PlotFragment extends Fragment {

    private BluetoothSetup btSetup = BluetoothSetup.getInstance();
    private CSVSetup csvFile = CSVSetup.getInstance();
    private static SeekBar seekBar;

    private ImageButton imgButton;
    private GraphView graphView;
    private LineGraphSeries<DataPoint> seriesCH1 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH2 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH3 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH4 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH5 = new LineGraphSeries<>();
    private LineGraphSeries<DataPoint> seriesCH6 = new LineGraphSeries<>();

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
                    if (inputStream.available() > 120) {
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

                        seriesCH1.appendData(new DataPoint(tmpX_Axis, tmpCh1), true, 500);
                        seriesCH2.appendData(new DataPoint(tmpX_Axis, tmpCh2), true, 500);
                        seriesCH3.appendData(new DataPoint(tmpX_Axis, tmpCh3), true, 500);
                        seriesCH4.appendData(new DataPoint(tmpX_Axis, tmpCh4), true, 500);
                        seriesCH5.appendData(new DataPoint(tmpX_Axis, tmpCh5), true, 500);
                        seriesCH6.appendData(new DataPoint(tmpX_Axis, tmpCh6), true, 500);

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

    public PlotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_plot, container, false);

        // Inflate the layout for this fragment
        imgButton = (ImageButton) rootView.findViewById(R.id.imgbutton);
        graphView = (GraphView) rootView.findViewById(R.id.graph1);

        setupGraphView();

        graphView.addSeries(seriesCH1);
        graphView.addSeries(seriesCH2);
        graphView.addSeries(seriesCH3);
        graphView.addSeries(seriesCH4);
        graphView.addSeries(seriesCH5);
        graphView.addSeries(seriesCH6);



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
                        plotRunnableHandler.removeCallbacks(plotRunnable);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONNECTION_ESTABLISHED && resultCode == Activity.RESULT_OK) {
            imgButton.setImageResource(R.drawable.ic_bluetooth_connection);
        }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (btSetup.isConnected()) {
            plotRunnable.run();
            plotRunnable.run();
            //plotRunnable.run();
            //plotRunnable.run();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        plotRunnableHandler.removeCallbacks(plotRunnable);
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

    private void setupGraphView() {
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

        //graphView.removeAllSeries();

        /*int numChannels = Settings.getInstance().getNumChannels();

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
        }*/



        graphView.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options_plot, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.menuChannel1:
                if (item.isChecked()) {
                    item.setChecked(false);
                    graphView.removeSeries(seriesCH1);
                } else {
                    item.setChecked(true);
                    graphView.addSeries(seriesCH1);
                }
                break;
            case R.id.menuChannel2:
                if (item.isChecked()) {
                    item.setChecked(false);
                    graphView.removeSeries(seriesCH2);

                } else {
                    item.setChecked(true);
                    graphView.addSeries(seriesCH2);

                }
                break;
            case R.id.menuChannel3:
                if (item.isChecked()) {
                    item.setChecked(false);
                    graphView.removeSeries(seriesCH3);

                } else {
                    item.setChecked(true);
                    graphView.addSeries(seriesCH3);

                }
                break;
            case R.id.menuChannel4:
                if (item.isChecked()) {
                    item.setChecked(false);
                    graphView.removeSeries(seriesCH4);

                } else {
                    item.setChecked(true);
                    graphView.addSeries(seriesCH4);

                }
                break;
            case R.id.menuChannel5:
                if (item.isChecked()) {
                    item.setChecked(false);
                    graphView.removeSeries(seriesCH5);

                } else {
                    item.setChecked(true);
                    graphView.addSeries(seriesCH5);

                }
                break;
            case R.id.menuChannel6:
                if (item.isChecked()) {
                    item.setChecked(false);
                    graphView.removeSeries(seriesCH6);

                } else {
                    item.setChecked(true);
                    graphView.addSeries(seriesCH6);
                }
                break;
            default:
        }
        graphView.invalidate();
        return super.onOptionsItemSelected(item);
    }

}
