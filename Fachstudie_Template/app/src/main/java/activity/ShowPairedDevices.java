package activity;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.fachstudie.fachstudie_template.R;

public class ShowPairedDevices extends AppCompatActivity {
    private List<BluetoothDevice> deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_paired_devices);
        setupListView();
    }

    private void setupListView() {
        ListView listView = (ListView) findViewById(R.id.listView);
        deviceList = new ArrayList<>();
        List<String> address_nameList = new ArrayList<>();

        for (BluetoothDevice bt: MainActivity.btSetup.getPairedDevices()) {
            deviceList.add(bt);
            address_nameList.add(bt.getAddress() + "   " + bt.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, address_nameList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.btSetup.setBtDevice(deviceList.get(position));
                try {
                    MainActivity.btSetup.establishConnection();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
