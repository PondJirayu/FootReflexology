package jirayu.pond.footreflexology.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import jirayu.pond.footreflexology.activity.MainActivity;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Button btnSignUp;
    RadioGroup radioGroup;
    Spinner spinnerProvince;
    ProgressDialog progressDialog;
    ArrayAdapter<CharSequence> adapterProvince;
    EditText editFirstName, editLastName, editTelephoneNumber, editAddress,
            editSubDistrict, editDistrict;
//    EditText editDay, editMonth, editYear;
    String firstName, lastName, identificationNumber, gender, birthDate,
            telephoneNumber, houseVillage, subDistrict, district, province, createdAt = null, updatedAt = null;

    /************
     * Functions
     ************/

    public RegisterFragment() {
        super();
    }

    public static RegisterFragment newInstance(String identificationNumber) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString("identificationNumber", identificationNumber); // เอาตัวแปร identificationNumber เก็บใน Arguments
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments / อ่านค่าจาก Arguments ใส่ Member Variable
        identificationNumber = getArguments().getString("identificationNumber");
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
        radioGroup = (RadioGroup) rootView.findViewById(R.id.rdGroup);
        spinnerProvince = (Spinner) rootView.findViewById(R.id.spinnerProvince);
        editFirstName = (EditText) rootView.findViewById(R.id.edit_first_name);
        editLastName = (EditText) rootView.findViewById(R.id.edit_last_name);
        editTelephoneNumber = (EditText) rootView.findViewById(R.id.edit_telephone_number);
        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        editSubDistrict = (EditText) rootView.findViewById(R.id.edit_sub_district);
        editDistrict = (EditText) rootView.findViewById(R.id.edit_district);
//        editDay = (EditText) rootView.findViewById(R.id.edit_day);
//        editMonth = (EditText) rootView.findViewById(R.id.edit_month);
//        editYear = (EditText) rootView.findViewById(R.id.edit_year);

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

        if (v == btnSignUp) {
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
                progressDialog.show(); // show progressDialog
                Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().InsertMemberList(
                        firstName,
                        lastName,
                        identificationNumber,
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

    Callback<MemberItemCollectionDao> insertMemberList = new Callback<MemberItemCollectionDao>() {
        @Override
        public void onResponse(Call<MemberItemCollectionDao> call,
                               Response<MemberItemCollectionDao> response) {

            if (response.isSuccessful()) {
                MemberItemCollectionDao dao = response.body();
                if (dao.getSuccess() == 1) { // ลงทะเบียนสำเร็จ
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),
                            "ลงทะเบียนสำเร็จ",
                            Toast.LENGTH_SHORT)
                            .show();
                    // เอาข้อมูลสมาชิกไปเก็บไว้ที่ Singleton เพื่อกระจายให้คนอื่นๆ เรียกใช้งาน
                    DataMemberManager.getInstance().setMemberItemDao(dao.getData().get(0));
                    // เข้าสู่หน้าหลัก
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish(); // เรียก Activity ที่ถือครอง Fragment ขึ้นมา แล้วสั่งทำลาย Activity
                } else if (dao.getSuccess() == 0) { // ลงทะเบียนไม่สำเร็จ
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),
                            "ขออภัยลงทะเบียนไม่สำเร็จ",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            } else { // 404 NOT FOUND
                progressDialog.dismiss();
                Toast.makeText(getActivity(),
                        "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลงทะเบียนอีกครั้งในภายหลัง",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onFailure(Call<MemberItemCollectionDao> call,
                              Throwable t) {

            progressDialog.dismiss();
            Toast.makeText(getActivity(),
                    "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

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