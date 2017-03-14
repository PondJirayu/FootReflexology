package jirayu.pond.footreflexology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
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
    private final int alertViewLength = 12;
    private AlertViewUtils alertViewUtils[] = new AlertViewUtils[alertViewLength];

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

    private void initAlertView() {
        // Create alertView [4. Gray Color]
        alertViewUtils[0] = new AlertViewUtils(getContext(), 4, 45, 45, 280, 200);
        alertViewUtils[1] = new AlertViewUtils(getContext(), 4, 45, 45, 150, 150);
        alertViewUtils[2] = new AlertViewUtils(getContext(), 4, 45, 45, 200, 200);

        // Add & Hide alertView
        for (int i = 0; i < 3; i++) {
            layoutAlert.addView(alertViewUtils[i].getAlertView(), alertViewUtils[i].getParams());
            alertViewUtils[i].hideAlertView();
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
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
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
