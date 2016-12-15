package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.manager.DataMemberManager;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ProfileFragment extends Fragment {

    /************
     * Variables
     ************/

    TextView tvFirstName;
    TextView tvLastName;
    TextView tvGender;
    TextView tvBirthDate;
    TextView tvAge;
    TextView tvIdentificationNumber;
    TextView tvTelephoneNumber;
    TextView tvHouseVillage;
    TextView tvSubDistrict;
    TextView tvDistrict;
    TextView tvProvince;

    /************
     * Functions
     ************/

    public ProfileFragment() {
        super();
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvFirstName = (TextView) rootView.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) rootView.findViewById(R.id.tvLastName);
        tvGender = (TextView) rootView.findViewById(R.id.tvGender);
        tvBirthDate = (TextView) rootView.findViewById(R.id.tvBirthDate);
        tvAge = (TextView)  rootView.findViewById(R.id.tvAge);
        tvIdentificationNumber = (TextView) rootView.findViewById(R.id.tvIdentificationNumber);
        tvTelephoneNumber = (TextView)  rootView.findViewById(R.id.tvTelephoneNumber);
        tvHouseVillage = (TextView) rootView.findViewById(R.id.tvHouseVillage);
        tvSubDistrict = (TextView)  rootView.findViewById(R.id.tvSubDistrict);
        tvDistrict = (TextView) rootView.findViewById(R.id.tvDistrict);
        tvProvince = (TextView) rootView.findViewById(R.id.tvProvince);

        tvFirstName.setText(DataMemberManager.getInstance().getMemberItemDao().getFirstName());
        tvLastName.setText(DataMemberManager.getInstance().getMemberItemDao().getLastName());
        tvGender.setText(DataMemberManager.getInstance().getMemberItemDao().getGender());
        tvBirthDate.setText(DataMemberManager.getInstance().getMemberItemDao().getBirthDate());
        tvAge.setText("0");
        tvIdentificationNumber.setText(DataMemberManager.getInstance().getMemberItemDao().getIdentificationNumber());
        tvTelephoneNumber.setText(DataMemberManager.getInstance().getMemberItemDao().getTelephoneNumber());
        tvHouseVillage.setText(DataMemberManager.getInstance().getMemberItemDao().getHouseVillage());
        tvSubDistrict.setText(DataMemberManager.getInstance().getMemberItemDao().getSubDistrict());
        tvDistrict.setText(DataMemberManager.getInstance().getMemberItemDao().getDistrict());
        tvProvince.setText(DataMemberManager.getInstance().getMemberItemDao().getProvince());
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


    /***************
     * Inner Class
     **************/
}
