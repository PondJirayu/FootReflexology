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

    Button btnSignUp, btnSelectBirthDay;
    TextView tvBirthDay;
    Spinner spinnerProvince;
    ArrayAdapter<CharSequence> adapter;

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
        btnSelectBirthDay = (Button) rootView.findViewById(R.id.btnSelectBirthDay);
        tvBirthDay = (TextView) rootView.findViewById(R.id.tvBirthDay);

        // Create Spinner
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.province_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapter); // สั่งให้ spinner ทำงานคู่กับ adapter
        spinnerProvince.setOnItemSelectedListener(this); // handle click

        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignUp){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
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
        Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}