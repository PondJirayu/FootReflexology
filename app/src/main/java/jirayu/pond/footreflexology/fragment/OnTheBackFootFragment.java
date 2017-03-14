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
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.manager.StringsManager;
import jirayu.pond.footreflexology.util.AlertViewUtils;
import jirayu.pond.footreflexology.util.InfoDialogUtils;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class OnTheBackFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Spinner spinnerOnTheBackFoot;
    ArrayAdapter<CharSequence> adapter;
    Button btnShowDetails;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    StringsManager stringsManager;

    private int lastPosition = -1;
    private final int SIZE = 12, ROW = 12, COL = 2;
    private int position[][] = new int[ROW][COL];
    private AlertViewUtils alertViewUtils[] = new AlertViewUtils[SIZE];

    /************
     * Functions
     ************/

    public OnTheBackFootFragment() {
        super();
    }

    public static OnTheBackFootFragment newInstance() {
        OnTheBackFootFragment fragment = new OnTheBackFootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_onthebackfoot, container, false);
        initInstances(rootView);
        initAlertViewPosition();
        initAlertView();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerOnTheBackFoot = (Spinner) rootView.findViewById(R.id.spinnerOnTheBackFoot);
        btnShowDetails = (Button) rootView.findViewById(R.id.btnShowDetails);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerOnTheBackFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click
        btnShowDetails.setOnClickListener(this);
        imgBtnInfo.setOnClickListener(this);
    }

    private void initAlertViewPosition() {
        /* COL[0 = topMargin]
           COL[1 = leftMargin] */
        // 1
        position[0][0] = 308;
        position[0][1] = 144;
        // 2
        position[1][0] = 307;
        position[1][1] = 277;
        // 3
        position[2][0] = 215;
        position[2][1] = 305;
        // 4
        position[3][0] = 90;
        position[3][1] = 172;
        // 5
        position[4][0] = 120;
        position[4][1] = 455;
        // 6
        position[5][0] = 329;
        position[5][1] = 508;
        // 7
        position[6][0] = 380;
        position[6][1] = 500;
        // 8
        position[7][0] = 430;
        position[7][1] = 486;
        // 9
        position[8][0] = 466;
        position[8][1] = 302;
        // 10
        position[9][0] = 274;
        position[9][1] = 348;
        // 11
        position[10][0] = 349;
        position[10][1] = 349;
        // 12
        position[11][0] = 295;
        position[11][1] = 487;
    }

    private void initAlertView() {
        for (int i = 0; i < SIZE; i++) {
            alertViewUtils[i] = new AlertViewUtils(getContext(), 4, 45, 45, position[i][0], position[i][1]); // Create
            layoutAlert.addView(alertViewUtils[i].getAlertView(), alertViewUtils[i].getParams());   // Add
            alertViewUtils[i].hideAlertView();  // Hide
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
                R.array.on_the_back_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOnTheBackFoot.setAdapter(adapter);   // Spinner + Adapter
    }

    public void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_LONG)
                .show();
    }

    /*****************
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
        switch (position) {
            case 0:
                alertViewUtils[0].showAlertView();
                lastPosition = position;
                break;
            case 1:
                alertViewUtils[1].showAlertView();
                lastPosition = position;
                break;
            case 2:
                alertViewUtils[2].showAlertView();
                lastPosition = position;
                break;
            case 3:
                alertViewUtils[3].showAlertView();
                lastPosition = position;
                break;
            case 4:
                alertViewUtils[4].showAlertView();
                lastPosition = position;
                break;
            case 5:
                alertViewUtils[5].showAlertView();
                lastPosition = position;
                break;
            case 6:
                alertViewUtils[6].showAlertView();
                lastPosition = position;
                break;
            case 7:
                alertViewUtils[7].showAlertView();
                lastPosition = position;
                break;
            case 8:
                alertViewUtils[8].showAlertView();
                lastPosition = position;
                break;
            case 9:
                alertViewUtils[9].showAlertView();
                lastPosition = position;
                break;
            case 10:
                alertViewUtils[10].showAlertView();
                lastPosition = position;
                break;
            case 11:
                alertViewUtils[11].showAlertView();
                lastPosition = position;
                break;
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
