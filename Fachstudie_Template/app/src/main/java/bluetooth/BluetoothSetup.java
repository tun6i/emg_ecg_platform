package bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/**
 *  Class for the bluetooth-setup of the app.
 */
public class BluetoothSetup {
    private BluetoothAdapter btAdapter;
    private BluetoothDevice btDevice;
    private BluetoothSocket btSocket;
    private boolean connected;
    // Global bluetooth-Setup.
    private static BluetoothSetup bluetoothSetup = new BluetoothSetup();
    //private static BluetoothSetup bluetoothSetup = null;

    /**
     *  Constructor for the bluetooth-setup.
     */
    private BluetoothSetup() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        connected = false;
        if (!btAdapter.isEnabled()) {
            btAdapter.enable();
        }
    }

    /**
     * Returns the bluetooth-setup instance.
     * @return
     */
    public static BluetoothSetup getInstance() {
        return bluetoothSetup;
    }

    /**
     * Returns the paired devices of the bluetooth.
     * @return
     */
    public Set<BluetoothDevice> getPairedDevices() {
        return btAdapter.getBondedDevices();
    }

    /**
     * creates the connection.
     * @throws IOException
     */
    public void establishConnection() throws IOException {
        // Socket to Service Record.
        btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        // Connection.
        btSocket.connect();
        // Set flag to connected.
        connected = true;
    }

    /**
     * Sets the flag to connected.
     * @param connected
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * Is connected.
     * @return
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Returns the bluetooth-socket.
     * @return
     */
    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    /**
     * Returns the bluetooth-Stream.
     * @return
     * @throws IOException
     */
    public InputStream getBtData() throws IOException {
        return btSocket.getInputStream();
    }

    /**
     * Sets the bluetooth-device.
     * @param btDevice
     */
    public void setBtDevice(BluetoothDevice btDevice) {
        this.btDevice = btDevice;
    }


}
