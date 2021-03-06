package activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bluetooth.BluetoothSetup;
import csv.CSVSetup;
import de.fachstudie.fachstudie_template.R;

/**
 * Class for the Main Activity of the app.
 */
public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener  {

    // Global Bluetooth-Setup.
    static BluetoothSetup btSetup;

    /*
     * EditText for the experiment-view:
     * Name of the current Participant.
     */
    EditText currentParticipantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main-Layout
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Fragment-Drawer for the fragment-views of the app.
        FragmentDrawer drawerFragment = (FragmentDrawer)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // Sets the global bluetooth.
        btSetup = BluetoothSetup.getInstance();

        // Display the first navigation drawer view on app launch
        if (savedInstanceState == null) {
            displayView(0);
        } else {
            displayView(Settings.getInstance().getActiveFragment());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Displays the individual views and sets the respective active fragment.
     * @param position
     */
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                Settings.getInstance().setActiveFragment(0);
                break;
            case 1:
                fragment = new InformationFragment();
                title = getString(R.string.title_information);
                Settings.getInstance().setActiveFragment(1);
                break;
            case 2:
                fragment = new PlotFragment();
                title = getString(R.string.title_plot);
                Settings.getInstance().setActiveFragment(2);
                break;
            case 3:
                fragment = new HeatmapFragment();
                title = getString(R.string.title_heatmap);
                Settings.getInstance().setActiveFragment(3);
                break;
            case 4:
                fragment = new ExperimentFragment();
                title = getString(R.string.title_experiment);
                Settings.getInstance().setActiveFragment(4);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // Set the toolbar title
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        }
    }

    /**
     * Creates a new CSV file for the current participant from EditText.
     * @param view
     */
    public void createNewCsvFile(View view) {
        currentParticipantName = (EditText)findViewById(R.id.participantName);
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createNewCSVFile(currentParticipantName.getText().toString());
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "Created New CSV File", Toast.LENGTH_LONG).show();
    }

    public void createMarkInCsv(View view) {
        CSVSetup csv = CSVSetup.getInstance();
    }

    /**
     * Sets a marker in the CSV file to start the experiment.
     * @param view
     */
    public void csvMarkStartExperiment(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "START EXPERIMENT", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-1", "-1", "-1", "-1", "-1", "-1", "-1");
    }

    /**
     * Sets a marker in the CSV file to stop the experiment.
     * @param view
     */
    public void csvMarkStopExperiment(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "STOP EXPERIMENT", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-2", "-2", "-2", "-2", "-2", "-2", "-2");
    }

    /**
     * Sets a marker in the CSV file to start the rotation exercise.
     * @param view
     */
    public void csvMarkStartRotation(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "START ROTATION", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-3", "-3", "-3", "-3", "-3", "-3", "-3");
    }

    /**
     * Sets a marker in the CSV file to stop the rotation exercise.
     * @param view
     */
    public void csvMarkStopRotation(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "STOP ROTATION", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-4", "-4", "-4", "-4", "-4", "-4", "-4");
    }

    /**
     * Sets a marker in the CSV file to start the push exercise.
     * @param view
     */
    public void csvMarkStartPush(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "START PUSH", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-5", "-5", "-5", "-5", "-5", "-5", "-5");
    }

    /**
     * Sets a marker in the CSV file to stop the push exercise.
     * @param view
     */
    public void csvMarkStopPush(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "STOP PUSH", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-6", "-6", "-6", "-6", "-6", "-6", "-6");
    }

    /**
     * Sets a marker in the CSV file to start the lift up exercise.
     * @param view
     */
    public void csvMarkStartLiftUp(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "START LIFT UP", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-7", "-7", "-7", "-7", "-7", "-7", "-7");
    }

    /**
     * Sets a marker in the CSV file to stop the lift up exercise.
     * @param view
     */
    public void csvMarkStopLiftUp(View view) {
        // Message on Screen.
        Toast.makeText(getApplicationContext(), "STOP LIFT UP", Toast.LENGTH_LONG).show();
        // CSV Instance.
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-8", "-8", "-8", "-8", "-8", "-8", "-8");
    }
}
