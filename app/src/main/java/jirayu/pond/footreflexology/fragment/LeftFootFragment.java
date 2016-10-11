package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import jirayu.pond.footreflexology.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class LeftFootFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinnerLeftFoot;
    ArrayAdapter<CharSequence> adapter;

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
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerLeftFoot = (Spinner) rootView.findViewById(R.id.spinnerLeftFoot);

        // Create Adapter of Spinner
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.left_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLeftFoot.setAdapter(adapter); // Spinner + Adapter
        spinnerLeftFoot.setOnItemSelectedListener(this); // Handle Click Spinner
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

    // Handle Click Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO: Handle Click Spinner of left foot
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
