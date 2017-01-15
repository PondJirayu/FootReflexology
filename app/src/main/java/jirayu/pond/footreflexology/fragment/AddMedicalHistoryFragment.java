package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.StatusDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddMedicalHistoryFragment extends Fragment implements View.OnClickListener {

    /************
     * Variables
     ************/

    Button btnSave;

    /************
     * Functions
     ************/

    public AddMedicalHistoryFragment() {
        super();
    }

    public static AddMedicalHistoryFragment newInstance() {
        AddMedicalHistoryFragment fragment = new AddMedicalHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_medical_history, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnSave = (Button) rootView.findViewById(R.id.btnSave);

        createSpinner();

        btnSave.setOnClickListener(this);
    }

    private void createSpinner() {
        // Create Adapter of Spinner

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

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            // Insert MedicalHistory Here
            Call<StatusDao> call = HttpManager.getInstance().getService().InsertMedicalHistory(
                    DataMemberManager.getInstance().getMemberItemDao().getId(),
                    1,
                    1
            );
            call.enqueue(insertMedicalHistory);
            getFragmentManager().popBackStack();
        }
    }

    Callback<StatusDao> insertMedicalHistory = new Callback<StatusDao>() {
        @Override
        public void onResponse(Call<StatusDao> call, Response<StatusDao> response) {

        }

        @Override
        public void onFailure(Call<StatusDao> call, Throwable t) {

        }
    };

    /**************
     * Inner Class
     **************/

}
