package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.adapter.MedicalHistoryAdapter;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MedicalHistoryFragment extends Fragment {

    /************
     * Variables
     ************/

    ListView listView;
    MedicalHistoryAdapter listAdapter;

    /************
     * Functions
     ************/

    public MedicalHistoryFragment() {
        super();
    }

    public static MedicalHistoryFragment newInstance() {
        MedicalHistoryFragment fragment = new MedicalHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medical_history, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView); // create listView
        listAdapter = new MedicalHistoryAdapter();  // create Adapter
        listView.setAdapter(listAdapter);   // listView with Adapter ทำงานร่วมกัน
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



    /**************
     * Inner Class
     **************/
}
