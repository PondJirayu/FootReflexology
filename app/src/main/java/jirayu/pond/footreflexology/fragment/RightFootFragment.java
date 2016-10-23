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
import android.widget.Spinner;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.manager.WordsManager;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RightFootFragment extends Fragment  {

    /***********
    * Variables
     ***********/

    Spinner spinnerRightFoot;
    ArrayAdapter<CharSequence> adapter;
    Button btnShowDetails;
    WordsManager wordsmanager;

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

        btnShowDetails.setOnClickListener(buttonClickListener); // Handle Click Button

        // Create Adapter of Spinner
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.right_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRightFoot.setAdapter(adapter); // Spinner + Adapter
        spinnerRightFoot.setOnItemSelectedListener(spinnerClickListener); // Handle Click Spinner
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

    /****************
     * Listener Zone
     ****************/

    // Handle Click Spinner
    AdapterView.OnItemSelectedListener spinnerClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            wordsmanager = new WordsManager();
            wordsmanager.setWord(parent.getItemAtPosition(position).toString());

            Toast.makeText(parent.getContext(),
                    wordsmanager.getWord(),
                    Toast.LENGTH_LONG)
                    .show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // Handle Click Button
    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnShowDetails){
                Intent intent = new Intent(getContext(), ShowDetailsActivity.class);
                startActivity(intent);
            }
        }
    };


    /**************
     * Inner Class
     **************/
}
