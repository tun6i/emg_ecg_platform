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
import bluetooth.BluetoothSetup;
import csv.CSVSetup;
import de.fachstudie.fachstudie_template.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener  {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    static BluetoothSetup btSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        btSetup = BluetoothSetup.getInstance();

        // display the first navigation drawer view on app launch
        displayView(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Settings Button
        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        // Search Button
        /*if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new PlotFragment();
                title = getString(R.string.title_plot);
                break;
            case 2:
                fragment = new InformationFragment();
                title = getString(R.string.title_information);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void createNewCsvFile(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createNewCSVFile();
    }

    public void createMarkInCsv(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        //csv.createMarkInCSV();
    }

    public void csvMarkStartExperiment(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-1", "-1", "-1", "-1", "-1", "-1", "-1");
    }

    public void csvMarkStopExperiment(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-2", "-2", "-2", "-2", "-2", "-2", "-2");
    }

    public void csvMarkStartRotation(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-3", "-3", "-3", "-3", "-3", "-3", "-3");
    }

    public void csvMarkStopRotation(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-4", "-4", "-4", "-4", "-4", "-4", "-4");
    }

    public void csvMarkStartPush(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-5", "-5", "-5", "-5", "-5", "-5", "-5");
    }

    public void csvMarkStopPush(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-6", "-6", "-6", "-6", "-6", "-6", "-6");
    }

    public void csvMarkStartLiftUp(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-7", "-7", "-7", "-7", "-7", "-7", "-7");
    }

    public void csvMarkStopLiftUp(View view) {
        CSVSetup csv = CSVSetup.getInstance();
        csv.createMarkInCSV("-8", "-8", "-8", "-8", "-8", "-8", "-8");
    }
}
