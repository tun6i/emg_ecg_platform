package com.home.graphplot;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListViewDevices extends AppCompatActivity {
    private List<BluetoothDevice> deviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_devices);
        setupListView();
    }

    private void setupListView() {
        ListView listView = (ListView) findViewById(R.id.listView);
        deviceList = new ArrayList<>();
        List<String> adress_nameList = new ArrayList<>();

        for (BluetoothDevice bt: Main.btSetup.getPairedDevices()) {
            deviceList.add(bt);
            adress_nameList.add(bt.getAddress() + "   " + bt.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, adress_nameList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Main.btSetup.setBtDevice(deviceList.get(position));
                try {
                    Main.btSetup.establishConnection();
                    setResult(RESULT_OK);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
