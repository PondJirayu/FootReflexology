package jirayu.pond.footreflexology.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import jirayu.pond.footreflexology.util.AlertViewUtils;
import jirayu.pond.footreflexology.util.InfoDialogUtils;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class OutSideFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Spinner spinnerOutSideFoot;
    ArrayAdapter<CharSequence> adapter;
    Button btnShowDetails;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    StringsManager stringsManager;

    private int lastPosition = -1;
    private final int SIZE = 16, ROW = 16, COL = 2;
    private int position[][] = new int[ROW][COL];
    private AlertViewUtils alertViewUtils[] = new AlertViewUtils[SIZE];

     /***********
     * Functions
     ************/

    public OutSideFootFragment() {
        super();
    }

    public static OutSideFootFragment newInstance() {
        OutSideFootFragment fragment = new OutSideFootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_outsidefoot, container, false);
        initInstances(rootView);
        initAlertViewPosition();
        initAlertView();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerOutSideFoot = (Spinner) rootView.findViewById(R.id.spinnerOutSideFoot);
        btnShowDetails = (Button) rootView.findViewById(R.id.btnShowDetails);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerOutSideFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click
        btnShowDetails.setOnClickListener(this);
        imgBtnInfo.setOnClickListener(this);
    }

    private void initAlertViewPosition() {
        /* COL[0 = topMargin]
           COL[1 = leftMargin] */
        // 1
        position[0][0] = 94;
        position[0][1] = 121;
        // 2
        position[1][0] = 107;
        position[1][1] = 90;
        // 3
        position[2][0] = 58;
        position[2][1] = 287;
        // 4
        position[3][0] = 288;
        position[3][1] = 131;
        // 5
        position[4][0] = 290;
        position[4][1] = 32;
        // 6
        position[5][0] = 426;
        position[5][1] = 106;
        // 7
        position[6][0] = 414;
        position[6][1] = 153;
        // 8
        position[7][0] = 324;
        position[7][1] = 223;
        // 9
        position[8][0] = 399;
        position[8][1] = 316;
        // 10
        position[9][0] = 425;
        position[9][1] = 415;
        // 11
        position[10][0] = 100;
        position[10][1] = 100;
        // 12
        position[11][0] = 100;
        position[11][1] = 100;
        // 13
        position[12][0] = 100;
        position[12][1] = 100;
        // 14
        position[13][0] = 100;
        position[13][1] = 100;
        // 15
        position[14][0] = 100;
        position[14][1] = 100;
        // 16
        position[15][0] = 100;
        position[15][1] = 100;
    }

    private void initAlertView() {
        for (int i = 0; i < SIZE; i++) {
            alertViewUtils[i] = new AlertViewUtils(getContext(), 4, 45, 45, position[i][0], position[i][1]); // Create
            layoutAlert.addView(alertViewUtils[i].getAlertView(), alertViewUtils[i].getParams());    // Add
            alertViewUtils[i].hideAlertView();    // Hide
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
                R.array.out_side_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOutSideFoot.setAdapter(adapter); // Spinner + Adapter
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

        if (lastPosition != -1) alertViewUtils[lastPosition].hideAlertView();
        for (int i = 0; i < SIZE; i++) {
            if (i == position) {
                alertViewUtils[i].showAlertView();
                lastPosition = position;
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
