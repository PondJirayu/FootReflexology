package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DatesChartSummaryFragment extends Fragment {

    /************
     * Variables
     ************/



    /************
     * Functions
     ************/

    public DatesChartSummaryFragment() {
        super();
    }

    public static DatesChartSummaryFragment newInstance() {
        DatesChartSummaryFragment fragment = new DatesChartSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dates_chart_summary, container, false);
        initInstances(rootView);
        initGraph();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here

    }

    private void initGraph() {

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

    private void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    /**************
     * Inner Class
     **************/

}
