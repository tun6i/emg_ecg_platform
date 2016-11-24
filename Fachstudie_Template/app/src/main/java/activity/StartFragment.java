package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

import de.fachstudie.fachstudie_template.R;

public class StartFragment extends Fragment {

    private ImageButton imgButton;
    private TextView textView;
    private int CONNECTION_ESTABLISHED = 1;

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_start, container, false);

        // Inflate the layout for this fragment
        imgButton = (ImageButton) rootView.findViewById(R.id.imgbutton);

        textView = (TextView) rootView.findViewById(R.id.connectionStatus);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.btSetup.isConnected()) {
                    Intent intent = new Intent(getActivity(), ShowPairedDevices.class);
                    startActivityForResult(intent, CONNECTION_ESTABLISHED);
                } else {
                    try {
                        MainActivity.btSetup.getBtData().close();
                        MainActivity.btSetup.getBtSocket().close();
                        MainActivity.btSetup.setConnected(false);
                        textView.setText(R.string.not_connected);
                        imgButton.setImageResource(R.drawable.ic_stat_no_connection);
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
            textView.setText(R.string.connected);
            imgButton.setImageResource(R.drawable.ic_stat_connected);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
