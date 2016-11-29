package bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/* Comment
 */
public class BluetoothSetup {
    private BluetoothAdapter btAdapter;
    private BluetoothDevice btDevice;
    private BluetoothSocket btSocket;
    private boolean connected;

    private static BluetoothSetup bluetoothSetup = new BluetoothSetup();

    public BluetoothSetup() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        connected = false;
        if (!btAdapter.isEnabled()) {
            btAdapter.enable();
        }
    }

    public Set<BluetoothDevice> getPairedDevices() {
        return btAdapter.getBondedDevices();
    }

    public void establishConnection() throws IOException {
        btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        btSocket.connect();
        connected = true;
    }

    public void setBtDevice(BluetoothDevice btDevice) {
        this.btDevice = btDevice;
    }

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public InputStream getBtData() throws IOException {
        return btSocket.getInputStream();
    }

    public static BluetoothSetup getInstance() {
        return bluetoothSetup;
    }
}
