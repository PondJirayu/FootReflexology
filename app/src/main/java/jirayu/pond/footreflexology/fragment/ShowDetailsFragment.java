package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    /************
     * Variables
     ************/

    ListView listView;
    DetailsListAdapter listAdapter;
    String result;

    /************
     * Functions
     ************/

    public ShowDetailsFragment() {
        super();
    }

    public static ShowDetailsFragment newInstance(String result) {
        ShowDetailsFragment fragment = new ShowDetailsFragment();
        Bundle args = new Bundle();
        args.putString("result", "");   // เอาตัวแปร result เก็บใน Arguments
        fragment.setArguments(args);    // สั่งให้ Arguments ผุกกับ Fragment
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments / อ่านค่าจาก Arguments ใส่ Member Variable
        result = getArguments().getString("result");
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
//        result = ((ShowDetailsActivity)getActivity()).getResult(); // ดึงค่า Result จาก Activity
        reloadData(result);
    }

    private void reloadData(String result) {

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
