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
import android.widget.ImageButton;
import android.widget.Spinner;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.manager.StringsManager;

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
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerRightFoot = (Spinner) rootView.findViewById(R.id.spinnerRightFoot);
        btnShowDetails = (Button) rootView.findViewById(R.id.btnShowDetails);

        createAdapter();
        spinnerRightFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click Button
        btnShowDetails.setOnClickListener(this);
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
        if (v == btnShowDetails){
            Intent intent = new Intent(getContext(), ShowDetailsActivity.class);
            intent.putExtra("result", stringsManager.getWordNoneNumberAndNoneWhiteSpace());
            startActivity(intent);
        }
    }

    /**************
     * Inner Class
     **************/
}
