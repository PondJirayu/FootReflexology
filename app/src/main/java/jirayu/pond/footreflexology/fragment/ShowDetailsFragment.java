package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.adapter.DetailsListAdapter;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ShowDetailsFragment extends Fragment {

    ListView listView;
    DetailsListAdapter listAdapter;

    public ShowDetailsFragment() {
        super();
    }

    public static ShowDetailsFragment newInstance() {
        ShowDetailsFragment fragment = new ShowDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView); // Create ListView
        listAdapter = new DetailsListAdapter();     // Create Adapter
        listView.setAdapter(listAdapter);   // สั่งให้ ListView with Adapter ทำงานร่วมกัน
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
}
