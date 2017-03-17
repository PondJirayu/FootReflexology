package jirayu.pond.footreflexology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.manager.StringsManager;
import jirayu.pond.footreflexology.util.AlertViewPositionUtils;
import jirayu.pond.footreflexology.util.AlertViewUtils;
import jirayu.pond.footreflexology.util.InfoDialogUtils;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class LeftFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Spinner spinnerLeftFoot;
    ArrayAdapter<CharSequence> adapter;
    Button btnShowDetails;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    StringsManager stringsManager;

    private int lastPosition = -1;
    private final int SIZE = 38 + 14;
    private int position[][] = AlertViewPositionUtils.getAlertViewLeftFootPosition();
    private AlertViewUtils alertViewUtils[] = new AlertViewUtils[SIZE];

    /************
     * Functions
     ************/

    public LeftFootFragment() {
        super();
    }

    public static LeftFootFragment newInstance() {
        LeftFootFragment fragment = new LeftFootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leftfoot, container, false);
        initInstances(rootView);
        initAlertView();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerLeftFoot = (Spinner) rootView.findViewById(R.id.spinnerLeftFoot);
        btnShowDetails = (Button) rootView.findViewById(R.id.btnShowDetails);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerLeftFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click
        btnShowDetails.setOnClickListener(this);
        imgBtnInfo.setOnClickListener(this);
    }

    private void initAlertView() {
        for (int i = 0; i < SIZE; i++) {
            alertViewUtils[i] = new AlertViewUtils(getContext(), 4, 45, 45, position[i][0], position[i][1]); // Create
            layoutAlert.addView(alertViewUtils[i].getAlertView(), alertViewUtils[i].getParams()); // Add
            alertViewUtils[i].hideAlertView(); // Hide
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    /*
     * Create Adapter of Spinner
     */
    private void createAdapter() {
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.left_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeftFoot.setAdapter(adapter); // Spinner + Adapter
    }

    private void showAlertViewNumberOneExtend() {
        for (int i = 1; i <= 8; i++) {
            alertViewUtils[37 + i].showAlertView();
        }
    }

    /****************
     * Listener Zone
     ****************/

    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stringsManager = new StringsManager();
        stringsManager.setWord(parent.getItemAtPosition(position).toString());

        if (lastPosition != -1) alertViewUtils[lastPosition].hideAlertView(); // ซ่อน AlertView
        for (int i = 0; i < SIZE; i++) {
            if (i == position) {
                alertViewUtils[i].showAlertView();
                lastPosition = position;
                switch (position) {
                    case 0:
                        showAlertViewNumberOneExtend();
                        break;
                    case 3:
                        alertViewUtils[37+9].showAlertView();
                        break;
                    case 4:
                        alertViewUtils[37+10].showAlertView();
                        break;
                    case 6:
                        alertViewUtils[37+11].showAlertView();
                        alertViewUtils[37+12].showAlertView();
                        alertViewUtils[37+13].showAlertView();
                        alertViewUtils[37+14].showAlertView();
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowDetails:
                Intent intent = new Intent(getContext(), ShowDetailsActivity.class);
                intent.putExtra("result", stringsManager.getWordNoneNumberAndNoneWhiteSpace());
                startActivity(intent);
                break;
            case R.id.imgBtnInfo:
                InfoDialogUtils infoDialog = new InfoDialogUtils(getContext());
                infoDialog.showDialog();
                break;
        }
    }

    /**************
     * Inner Class
     **************/
}
