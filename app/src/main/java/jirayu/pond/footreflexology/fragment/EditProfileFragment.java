package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import jirayu.pond.footreflexology.R;
import retrofit2.http.Path;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class EditProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    /************
     * Variables
     ************/

    EditText editFirstName, editLastName, editDay, editMonth,
            editYear, editTelephoneNumber, editAddress, editSubDistrict,
            editDistrict;
    RadioGroup radioGroup;
    Spinner spinnerProvince;
    Button btnSave;
    ArrayAdapter<CharSequence> adapterProvince;
    String province;

    /************
     * Functions
     ************/

    public EditProfileFragment() {
        super();
    }

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        editFirstName = (EditText) rootView.findViewById(R.id.edit_first_name);
        editLastName = (EditText) rootView.findViewById(R.id.edit_last_name);
        editDay = (EditText) rootView.findViewById(R.id.edit_day);
        editMonth = (EditText) rootView.findViewById(R.id.edit_month);
        editYear = (EditText) rootView.findViewById(R.id.edit_year);
        editTelephoneNumber = (EditText) rootView.findViewById(R.id.edit_telephone_number);
        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        editSubDistrict = (EditText) rootView.findViewById(R.id.edit_sub_district);
        editDistrict = (EditText) rootView.findViewById(R.id.edit_district);
        btnSave = (Button) rootView.findViewById(R.id.btnSignUp);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.rdGroup);
        spinnerProvince = (Spinner) rootView.findViewById(R.id.spinnerProvince);

        createSpinner();
        createContent();

        // Handle Click Button
        btnSave.setOnClickListener(this);
    }

    private void createContent() {
        editFirstName.setText("");
        editLastName.setText("");
        editDay.setText("");
        editMonth.setText("");
        editYear.setText("");
        editTelephoneNumber.setText("");
        editAddress.setText("");
        editSubDistrict.setText("");
        editDistrict.setText("");
    }

    private void createSpinner() {
        // Create Adapter of Spinner(Province)
        adapterProvince = ArrayAdapter.createFromResource(getActivity(),
                R.array.province_names, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapterProvince); // สั่งให้ spinner กับ adapter ทำงานร่วมกัน
        spinnerProvince.setOnItemSelectedListener(this); // Handle Click Spinner
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

    // Handle Click Button
    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            // UpdateMember Here

            getFragmentManager().popBackStack();
        }
    }

    // Handle Click Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // spinner Province
        province = spinnerProvince.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**************
     * Inner Class
     **************/

}
