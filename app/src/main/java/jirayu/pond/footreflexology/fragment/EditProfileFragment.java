package jirayu.pond.footreflexology.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import static jirayu.pond.footreflexology.R.string.district;
import static jirayu.pond.footreflexology.R.string.gender;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class EditProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    /************
     * Variables
     ************/

//   EditText editDay, editMonth, editYear,
    EditText editFirstName, editLastName, editTelephoneNumber, editAddress, editSubDistrict,
            editDistrict;
    RadioGroup radioGroup;
    Spinner spinnerProvince;
    Button btnSave;
    ArrayAdapter<CharSequence> adapterProvince;
    StringsManager stringsManager;
    ProgressDialog progressDialog;
    String firstName, lastName, gender, birthDate,
            telephoneNumber, houseVillage, subDistrict, district, province, createdAt = null, updatedAt = null;

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
        // Edit Title in Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("แก้ไขประวัติส่วนตัว");
        // Init 'View' instance(s) with rootView.findViewById here
        editFirstName = (EditText) rootView.findViewById(R.id.edit_first_name);
        editLastName = (EditText) rootView.findViewById(R.id.edit_last_name);
//        editDay = (EditText) rootView.findViewById(R.id.edit_day);
//        editMonth = (EditText) rootView.findViewById(R.id.edit_month);
//        editYear = (EditText) rootView.findViewById(R.id.edit_year);
        editTelephoneNumber = (EditText) rootView.findViewById(R.id.edit_telephone_number);
        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        editSubDistrict = (EditText) rootView.findViewById(R.id.edit_sub_district);
        editDistrict = (EditText) rootView.findViewById(R.id.edit_district);
        btnSave = (Button) rootView.findViewById(R.id.btnSignUp);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.rdGroup);
        spinnerProvince = (Spinner) rootView.findViewById(R.id.spinnerProvince);
        stringsManager = new StringsManager();

        createSpinner();
        loadMemberList(); // โหลดข้อมูลเก่าไปแสดงในหน้าแก้ไขก่อน

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

    private void getTextToVariables() {
        firstName = editFirstName.getText().toString();
        lastName = editLastName.getText().toString();
        telephoneNumber = editTelephoneNumber.getText().toString();
        houseVillage = editAddress.getText().toString();
        subDistrict = editSubDistrict.getText().toString();
        district = editDistrict.getText().toString();
//        birthDate = editYear.getText().toString()
//                + "-" + editMonth.getText().toString()
//                + "-" + editDay.getText().toString();

        // check operator
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbMale:
                gender = "ชาย";
                break;
            case R.id.rbFemale:
                gender = "หญิง";
                break;
        }
    }

//    private boolean checkDay() {
//        int day = Integer.parseInt(editDay.getText().toString());
//        if (day <= 0 || day > 31)
//            return true;
//        return false;
//    }
//
//    private boolean checkMonth() {
//        int month = Integer.parseInt(editMonth.getText().toString());
//        if (month <= 0 || month > 12)
//            return true;
//        return false;
//    }

    /****************
     * Listener Zone
     ****************/

    // Handle Click Button
    @Override
    public void onClick(View v) {
        // create Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("รอสักครู่...");
        progressDialog.setMessage("กำลังบันทึกข้อมูล");

        if (v == btnSave) {
            // getText to variable
            getTextToVariables();

            if (firstName.trim().length() == 0
                    || lastName.trim().length() == 0
                    || telephoneNumber.trim().length() == 0
//                    || editYear.getText().toString().trim().length() == 0
//                    || editMonth.getText().toString().trim().length() == 0
//                    || editDay.getText().toString().trim().length() == 0
                    || houseVillage.trim().length() == 0
                    || subDistrict.trim().length() == 0
                    || district.trim().length() == 0) {
                Toast.makeText(getActivity(),
                        "กรุณาป้อนข้อมูลให้ครบถ้วน",
                        Toast.LENGTH_SHORT)
                        .show();
//            } else if (checkDay()) {
//                Toast.makeText(getActivity(),
//                        "กรุณาป้อนวันที่ให้ถูกต้อง",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            } else if (checkMonth()) {
//                Toast.makeText(getActivity(),
//                        "กรุณาป้อนเดือนให้ถูกต้อง",
//                        Toast.LENGTH_SHORT)
//                        .show();
            } else {
                progressDialog.show();
                // UpdateMember Here
                Call<StatusDao> call = HttpManager.getInstance().getService().UpdateMember(
                        DataMemberManager.getInstance().getMemberItemDao().getId(),
                        firstName,
                        lastName,
                        DataMemberManager.getInstance().getMemberItemDao().getIdentificationNumber(),
                        gender,
                        birthDate,
                        telephoneNumber,
                        houseVillage,
                        subDistrict,
                        district,
                        province,
                        createdAt,
                        updatedAt
                );
                call.enqueue(insertMemberList);
            }
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
//                    editDay.setText(stringsManager.getDay());
//                    editMonth.setText(stringsManager.getMonth());
//                    editYear.setText(stringsManager.getYear());
                    editTelephoneNumber.setText(dao.getData().get(0).getTelephoneNumber());
                    editAddress.setText(dao.getData().get(0).getHouseVillage());
                    editSubDistrict.setText(dao.getData().get(0).getSubDistrict());
                    editDistrict.setText(dao.getData().get(0).getDistrict());
                    stringsManager.setWord(dao.getData().get(0).getProvince());
                    spinnerProvince.setSelection(stringsManager.getProvinceId());
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

    Callback<StatusDao> insertMemberList = new Callback<StatusDao>() {
        @Override
        public void onResponse(Call<StatusDao> call,
                               Response<StatusDao> response) {

            if (response.isSuccessful()) {
                StatusDao dao = response.body();
                if (dao.getSuccess() != 1) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),
                            "ขออภัยแก้ไขข้อมูลไม่สำเร็จ",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),
                            "แก้ไขข้อมูลแล้ว",
                            Toast.LENGTH_SHORT)
                            .show();
                    getFragmentManager().popBackStack();
                }
            } else { // 404
                progressDialog.dismiss();
                Toast.makeText(getActivity(),
                        "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onFailure(Call<StatusDao> call,
                              Throwable t) {

            progressDialog.dismiss();
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
