package jirayu.pond.footreflexology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.MainActivity;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Button btnSignUp;
    Spinner spinnerProvince, spinnerDays, spinnerMonths, spinnerYears;
    ArrayAdapter<CharSequence> adapterProvince, adapterDays, adapterMonths, adapterYears;

    /************
     * Functions
     ************/

    public RegisterFragment() {
        super();
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        btnSignUp = (Button) rootView.findViewById(R.id.btnSignUp);
        spinnerProvince = (Spinner) rootView.findViewById(R.id.spinnerProvince);
        spinnerDays = (Spinner) rootView.findViewById(R.id.spinnerDays);
        spinnerMonths = (Spinner) rootView.findViewById(R.id.spinnerMonths);
        spinnerYears = (Spinner) rootView.findViewById(R.id.spinnerYears);

        createSpinner();

        // Handle Click Button
        btnSignUp.setOnClickListener(this);
    }

    private void createSpinner() {
        // Create Adapter of Spinner (Province)
        adapterProvince = ArrayAdapter.createFromResource(getActivity(),
                R.array.province_names, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapterProvince); // สั่งให้ spinner ทำงานคู่กับ adapter
        spinnerProvince.setOnItemSelectedListener(this); // Handle Click Spinner

        // Create Adapter of Spinner (Day)
        adapterDays = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_days, android.R.layout.simple_spinner_item);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDays.setAdapter(adapterDays);
        spinnerDays.setOnItemSelectedListener(this);

        // Create Adapter of Spinner (Month)
        adapterMonths = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_months, android.R.layout.simple_spinner_item);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapterMonths);
        spinnerMonths.setOnItemSelectedListener(this);

        // Create Adapter of Spinner (Year)
        adapterYears = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_years, android.R.layout.simple_spinner_item);
        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYears.setAdapter(adapterYears);
        spinnerYears.setOnItemSelectedListener(this);
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /****************
     * Listener Zone
     ****************/

    // Handle Click Button
    @Override
    public void onClick(View v) {
        if (v == btnSignUp) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    // Handle Click Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getActivity(),
//                parent.getItemAtPosition(position).toString(),
//                Toast.LENGTH_SHORT)
//                .show();
    }

    /**************
     * Inner Class
     **************/

}