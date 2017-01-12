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
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.StringsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    StringsManager stringsManager;

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
        stringsManager = new StringsManager();

        createSpinner();
        loadMemberList();

        // Handle Click Button
        btnSave.setOnClickListener(this);
    }

    private void loadMemberList() {
        Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().loadMemberList(
                "members",
                DataMemberManager.getInstance().getMemberItemDao().getIdentificationNumber()
        );
        call.enqueue(loadMemberList);
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
//            Call<StatusDao> call = HttpManager.getInstance().getService().UpdateMember(
//                    DataMemberManager.getInstance().getMemberItemDao().getId(),
//                    editFirstName.getText().toString(),
//                    editLastName.getText().toString(),
//                    editTelephoneNumber.getText().toString(),
//                    editAddress.getText().toString(),
//                    editSubDistrict.getText().toString(),
//                    editDistrict.getText().toString()
//            );
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

    Callback<MemberItemCollectionDao> loadMemberList = new Callback<MemberItemCollectionDao>() {
        @Override
        public void onResponse(Call<MemberItemCollectionDao> call, Response<MemberItemCollectionDao> response) {
            if (response.isSuccessful()) {
                MemberItemCollectionDao dao = response.body();
                if (dao.getData().isEmpty()) {
                    Toast.makeText(getActivity(),
                            "ไม่พบข้อมูลผู้ป่วย",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    editFirstName.setText(dao.getData().get(0).getFirstName());
                    editLastName.setText(dao.getData().get(0).getLastName());
                    if (dao.getData().get(0).getGender().equals("ชาย")) {
                        radioGroup.check(R.id.rbMale);
                    } else {
                       radioGroup.check(R.id.rbFemale);
                    }
                    stringsManager.setWord(dao.getData().get(0).getBirthDate());
                    editDay.setText(stringsManager.getDay());
                    editMonth.setText(stringsManager.getMonth());
                    editYear.setText(stringsManager.getYear());
                    editTelephoneNumber.setText(dao.getData().get(0).getTelephoneNumber());
                    editAddress.setText(dao.getData().get(0).getHouseVillage());
                    editSubDistrict.setText(dao.getData().get(0).getSubDistrict());
                    editDistrict.setText(dao.getData().get(0).getDistrict());
                    // set Spinner here

                }
            } else {
                Toast.makeText(getActivity(),
                        "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onFailure(Call<MemberItemCollectionDao> call, Throwable t) {
            Toast.makeText(getActivity(),
                    "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };


    /**************
     * Inner Class
     **************/

}
