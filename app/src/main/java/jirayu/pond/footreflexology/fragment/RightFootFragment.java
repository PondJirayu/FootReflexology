package jirayu.pond.footreflexology.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.manager.StringsManager;
import jirayu.pond.footreflexology.util.InfoDialogUtils;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RightFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
    * Variables
     ***********/

    Spinner spinnerRightFoot;
    ArrayAdapter<CharSequence> adapter;
    Button btnShowDetails;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    StringsManager stringsManager;

    /************
     * Functions
     ************/

    public RightFootFragment() {
        super();
    }

    public static RightFootFragment newInstance() {
        RightFootFragment fragment = new RightFootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rightfoot, container, false);
        initInstances(rootView);
        initViewAlert();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerRightFoot = (Spinner) rootView.findViewById(R.id.spinnerRightFoot);
        btnShowDetails = (Button) rootView.findViewById(R.id.btnShowDetails);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerRightFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click Button
        btnShowDetails.setOnClickListener(this);
        imgBtnInfo.setOnClickListener(this);
    }

    private void initViewAlert() {
        // Create View
        View view = new View(getContext());
        view.setBackgroundResource(R.drawable.shape_view_alert_yellow_color);
        View view1 = new View(getContext());
        view1.setBackgroundResource(R.drawable.shape_view_alert_green_color);
        View view2 = new View(getContext());
        view2.setBackgroundResource(R.drawable.shape_view_alert_red_color);

        // Creating Animation let View
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(600);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
        view1.startAnimation(anim);
        view2.startAnimation(anim);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(45, 45);
        params.leftMargin = 300;
        params.topMargin = 220;
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(45, 45);
        params1.leftMargin = 270;
        params1.topMargin = 50;
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(45, 45);
        params2.leftMargin = 400;
        params2.topMargin = 500;

        layoutAlert.addView(view, params);
        layoutAlert.addView(view1, params1);
        layoutAlert.addView(view2, params2);
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
                R.array.right_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRightFoot.setAdapter(adapter); // Spinner + Adapter
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
