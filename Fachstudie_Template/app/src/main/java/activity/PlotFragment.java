package activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.scichart.charting.model.AxisCollection;
import com.scichart.charting.model.RenderableSeriesCollection;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.DateAxis;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.core.annotations.Visibility;
import com.scichart.core.model.DateValues;
import com.scichart.core.model.DoubleValues;
import com.scichart.core.model.IntegerValues;
import com.scichart.core.utility.DateIntervalUtil;
import com.scichart.drawing.common.PenStyle;
import com.scichart.drawing.utility.ColorUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import bluetooth.BluetoothSetup;
import de.fachstudie.fachstudie_template.R;

public class PlotFragment extends Fragment {

    private Handler graphHandler = new Handler();
    private SciChartSurface chartSurface;
    private FastLineRenderableSeries lineSeries;
    private IXyDataSeries<Date, Integer> dataSeries;
    private boolean isFragmentRunning = false;

    // Buffer for building EMG value
    // contains highByte & lowByte of an Integer
    private byte[] buffer = new byte[12];

    //Debug
    long starttime = System.currentTimeMillis();
    long stoptime = System.currentTimeMillis();

    private Runnable timerRunnable = new Runnable() {
        ByteBuffer wrapper;
        InputStream inputStream;
        float x_Axis = 0;
        int amountBytes = 0;
        BluetoothSetup btSetup = MainActivity.btSetup;

        @Override
        public void run() {
            if (btSetup.isConnected()) {
                Log.w("Tag", "Graph view running");
                try {
                    inputStream = btSetup.getBtData();
                    stoptime = System.currentTimeMillis();
                    Log.d("tag", stoptime - starttime + "");
                    starttime = System.currentTimeMillis();
                    if (inputStream.available() > 0) {
                        do {
                            amountBytes = inputStream.read(buffer, 0, 2);
                            wrapper = ByteBuffer.wrap(buffer);
                        } while (wrapper.getShort() != 1337);

                        amountBytes = inputStream.read(buffer);
                        wrapper = ByteBuffer.wrap(buffer);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            x_Axis = x_Axis + 0.01f;
            /*if (isFragmentRunning) {
            } else {
                graphHandler.postDelayed(this, 20);
            }*/
            graphHandler.post(this);


        }
    };
    Thread thread = new Thread(timerRunnable);


    public PlotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plot, container, false);
        isFragmentRunning = true;

        chartSurface = (SciChartSurface) rootView.findViewById(R.id.chartView);

        try {
            chartSurface.setRuntimeLicenseKeyFromResource(this.getActivity(), "app\\src\\main\\res\\raw\\license.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateAxis xAxis = new DateAxis(this.getActivity());
        NumericAxis yAxis = new NumericAxis(this.getActivity());

        AxisCollection xAxes = chartSurface.getXAxes();
        xAxes.add(xAxis);
        AxisCollection yAxes = chartSurface.getXAxes();
        yAxes.add(yAxis);
        yAxis.setAxisAlignment(AxisAlignment.Left);
        xAxis.setAxisAlignment(AxisAlignment.Bottom);
        xAxis.setAxisTitle("Hallo");
        yAxis.setAxisTitle("Welt");

        RenderableSeriesCollection renderableSeries = chartSurface.getRenderableSeries();
        lineSeries = new FastLineRenderableSeries();
        PenStyle strokePen = new PenStyle(ColorUtil.argb(0xFF, 0x27, 0x9b, 0x27), true, 2f);
        lineSeries.setStrokeStyle(strokePen);

        renderableSeries.add(lineSeries);

        dataSeries = new XyDataSeries<>(Date.class, Integer.class);
        DateValues xValues = new DateValues(100);
        IntegerValues yValues = new IntegerValues(100);

        Random random = new Random();
        for (double i = 0; i < 100; i++) {
            long xValue = new Date().getTime() + DateIntervalUtil.fromDays(i);
            xValues.add(new Date(xValue));
            yValues.add(random.nextInt());
        }

        dataSeries.append(xValues, yValues);

        lineSeries.setDataSeries(dataSeries);

        chartSurface.zoomExtents();

        //Collections.addAll(chartSurface.getRenderableSeries(), lineSeries);

        //thread.start();

        // Inflate the layout for this fragment
        return rootView;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentRunning = false;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        isFragmentRunning = true;
    }
}
