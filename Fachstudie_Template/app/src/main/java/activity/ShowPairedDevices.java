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

import bluetooth.BluetoothSetup;
import de.fachstudie.fachstudie_template.R;

public class ShowPairedDevices extends AppCompatActivity {

    // List of paired devices
    private List<BluetoothDevice> deviceList;

    // Get Bluetooth adapter
    private BluetoothSetup btSetup = BluetoothSetup.getInstance();


    /**
     * Create and setup new Acticity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout and content
        setContentView(R.layout.activity_show_paired_devices);
        setupListView();
    }

    /**
     * Prepare list for the view
     */
    private void setupListView() {

        // Get UI element
        ListView listView = (ListView) findViewById(R.id.listView);

        // Array with devices
        deviceList = new ArrayList<>();

        // List with divice names
        List<String> address_nameList = new ArrayList<>();

        // Add paired devices to the array and list
        for (BluetoothDevice bt: btSetup.getPairedDevices()) {
            deviceList.add(bt);
            address_nameList.add(bt.getAddress() + "   " + bt.getName());
        }

        // List layout
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, address_nameList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Connect to selected device
                btSetup.setBtDevice(deviceList.get(position));
                try {
                    btSetup.establishConnection();

                    // Return to previous view/activity
                    setResult(RESULT_OK);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
