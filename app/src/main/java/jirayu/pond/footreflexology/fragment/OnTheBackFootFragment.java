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
import jirayu.pond.footreflexology.util.ButtonAlertPositionUtils;
import jirayu.pond.footreflexology.util.ButtonAlertUtils;
import jirayu.pond.footreflexology.util.InfoDialogUtils;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class OnTheBackFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Spinner spinnerFoot;
    ArrayAdapter<CharSequence> adapter;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    private int lastPosition = -1;
    private final int SIZE = 12;
    private int position[][] = ButtonAlertPositionUtils.getAlertViewOnTheBackFootPosition();
    private ButtonAlertUtils buttonAlertUtils[] = new ButtonAlertUtils[SIZE];

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
        initAlertView();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerFoot = (Spinner) rootView.findViewById(R.id.spinnerFoot);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click
        imgBtnInfo.setOnClickListener(this);
    }

    private void initAlertView() {
        for (int i = 0; i < SIZE; i++) {
            buttonAlertUtils[i] = new ButtonAlertUtils(getContext(), 4, 38, 38, position[i][0], position[i][1]); // Create
            layoutAlert.addView(buttonAlertUtils[i].getBtnAlert(), buttonAlertUtils[i].getParams());   // Add
            buttonAlertUtils[i].hideAlertView();  // Hide
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
        spinnerFoot.setAdapter(adapter);   // Spinner + Adapter
    }

    private void showAlertView(int position) {
        if (lastPosition != -1) buttonAlertUtils[lastPosition].hideAlertView(); // ซ่อน AlertView ตัวเก่า
        for (int i = 0; i < SIZE; i++) {
            if (i == position) {
                buttonAlertUtils[i].showAlertView();
                lastPosition = position;
                break;
            }
        }
    }

    /*****************
     * Listener Zone
     ****************/

    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showAlertView(position);
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
