package activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import csv.CSVSetup;
import de.fachstudie.fachstudie_template.R;

/**
 * Class for "Home" view
 */
public class HomeFragment extends Fragment {

    // Get CSV adapter
    private CSVSetup csvFile = CSVSetup.getInstance();

    // ID for write permission
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Layout for the home view
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        /*
         * Query to access the files of the mobile phone.
         * (Must be queried from API 23).
          */
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // File is created with the header.
        if (canWrite && !csvFile.getCSVBoolean()) {
            this.writeFile();
        }

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

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(getActivity(), permissionName);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    /**
     *  Creates a new file in external storage with a specific header.
     */
    private void writeFile() {

        // External Storage
        File extStore = Environment.getExternalStorageDirectory();

        // File-Path
        String path = extStore.getAbsolutePath() + "/" + csvFile.getFileName();

        try {
            // File with specific path
            File myFile = new File(path);

            // Creates new File
            myFile.createNewFile();

            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            //Adds the CSV header
            myOutWriter.append(csvFile.getCSVHeader());
            myOutWriter.append("\n\r");
            myOutWriter.close();
            fOut.close();

            // Says that there is a csv file.
            csvFile.setCSVBoolean(true);

            // Message that a CSV file was created.
            //Toast.makeText(getActivity(), "CSV-Datei " + csvFile.getFileName() + " wurde erstellt", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
