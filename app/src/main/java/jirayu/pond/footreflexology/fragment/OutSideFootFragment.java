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
public class OutSideFootFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinnerOutSideFoot;
    ArrayAdapter<CharSequence> adapter;

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
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerOutSideFoot = (Spinner) rootView.findViewById(R.id.spinnerOutSideFoot);

        // Create Adapter of Spinner
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.out_side_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Spinner + Adapter
        spinnerOutSideFoot.setAdapter(adapter);
        // Handle Click
        spinnerOutSideFoot.setOnItemSelectedListener(this);
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
