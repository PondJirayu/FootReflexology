package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jirayu.pond.footreflexology.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DiseaseSummaryFragment extends Fragment {

    /************
     * Variables
     ************/

    FloatingActionButton btnFloatingAction;

    TextView tvDiseaseName, tvDetail, tvTreatment, tvShouldEat, tvShouldNotEat, tvRecommend;

    /************
     * Functions
     ************/

    public DiseaseSummaryFragment() {
        super();
    }

    public static DiseaseSummaryFragment newInstance() {
        DiseaseSummaryFragment fragment = new DiseaseSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_disease_summary, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnFloatingAction = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingAction);
        tvDiseaseName = (TextView) rootView.findViewById(R.id.tvDiseaseName);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        tvTreatment = (TextView) rootView.findViewById(R.id.tvTreatment);
        tvShouldEat = (TextView) rootView.findViewById(R.id.tvShouldEat);
        tvShouldNotEat = (TextView) rootView.findViewById(R.id.tvShouldNotEat);
        tvRecommend = (TextView) rootView.findViewById(R.id.tvRecommend);
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
