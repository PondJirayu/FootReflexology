package jirayu.pond.footreflexology.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.MainActivity;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener {

    /************
     * Variables
     ************/

    java.sql.Date birthDate = null;
    EditText editFirstName,
             editLastName,
             editTelephoneNumber,
             editAddress,
             editSubDistrict,
             editDistrict;
    RadioGroup radioGroup;
    TextView tvBirthDate;
    ImageButton btnDatePicker;
    Spinner spinnerProvince;
    ProgressDialog progressDialog;
    FloatingActionButton btnFloatingAction;
    ArrayAdapter<CharSequence> adapterProvince;
    String firstName = null,
           lastName = null,
           identificationNumber = null,
           gender = null,
           telephoneNumber = null,
           houseVillage = null,
           subDistrict = null,
           district = null,
           province = null;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

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
        btnFloatingAction = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingAction);
        tvBirthDate = (TextView) rootView.findViewById(R.id.tvBirthDate);
        btnDatePicker = (ImageButton) rootView.findViewById(R.id.btnDatePicker);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.rdGroup);
        spinnerProvince = (Spinner) rootView.findViewById(R.id.spinnerProvince);
        editFirstName = (EditText) rootView.findViewById(R.id.edit_first_name);
        editLastName = (EditText) rootView.findViewById(R.id.edit_last_name);
        editTelephoneNumber = (EditText) rootView.findViewById(R.id.edit_telephone_number);
        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        editSubDistrict = (EditText) rootView.findViewById(R.id.edit_sub_district);
        editDistrict = (EditText) rootView.findViewById(R.id.edit_district);

        setDate();
        createSpinner();

        // Handle Click Button
        btnFloatingAction.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
    }

    private void setDate() {
        // แสดงเวลาปัจจุบัน
        calendar = Calendar.getInstance();

        datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                false);
    }

    private void createSpinner() {
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

    private void insertMemberList() {
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
                new Timestamp(System.currentTimeMillis()),  // GET เวลาปัจจุบันเก็บในตัวแปร createdAt
                new Timestamp(System.currentTimeMillis())   // GET เวลาปัจจุบันเก็บในตัวแปร updatedAt
        );
        call.enqueue(insertMemberList);
    }

    private void getTextToVariables() {
        firstName = editFirstName.getText().toString();
        lastName = editLastName.getText().toString();
        telephoneNumber = editTelephoneNumber.getText().toString();
        houseVillage = editAddress.getText().toString();
        subDistrict = editSubDistrict.getText().toString();
        district = editDistrict.getText().toString();
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbMale:
                gender = "ชาย";
                break;
            case R.id.rbFemale:
                gender = "หญิง";
                break;
        }
    }

    private void saveLoginMemberToInternalStorage(MemberItemCollectionDao dao) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginMember",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", dao.getData().get(0).getId());
        editor.putString("firstname", dao.getData().get(0).getFirstName());
        editor.putString("lastname", dao.getData().get(0).getLastName());
        editor.putString("identification_number", dao.getData().get(0).getIdentificationNumber());
        editor.apply(); // บันทึกข้อมูลลงไฟล์
    }

    /*
     * ตรวจสอบสิ่งที่ป้อนเข้ามาด้วย RegEx(Regular Expression)
     */
    private boolean validateFirstNameLastName(String firstName, String lastName) {
        Matcher m1, m2;
        String pattern = "[ก-์]+"; // ชื่อนามสกุลประกอบด้วยตัวอักษร ก-์ อย่างน้อย 1 ตัวอักษร

        Pattern p = Pattern.compile(pattern);
        m1 = p.matcher(firstName);
        m2 = p.matcher(lastName);

        return (m1.matches() && m2.matches());
    }

    private boolean validateTelephoneNumber(String telephoneNumber) {
        String pattern = "([0-9]{9,})*"; // เบอร์โทรศัพท์ประกอบด้วยตัวเลข 0-9 อย่างน้อย 9 ตัว (จะมีหรือไม่มีก็ได้)

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(telephoneNumber);

        return m.matches();
    }

    private boolean validateAddress(String houseVillage, String subDistrict, String district) {
        Matcher m1, m2, m3;
        String pattern = ".*"; // ที่อยู่ประกอบด้วยตัวอักษรอะไรก็ได้ มีหรือไม่มีก็ได้

        Pattern p = Pattern.compile(pattern);
        m1 = p.matcher(houseVillage);
        m2 = p.matcher(subDistrict);
        m3 = p.matcher(district);

        return ((m1.matches() && m2.matches()) && m3.matches());
    }

    private void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        // create Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("รอสักครู่...");
        progressDialog.setMessage("กำลังบันทึกข้อมูล");

        switch (v.getId()) {
            case R.id.btnFloatingAction:
                getTextToVariables(); // Get Text to Variable
                if (!validateFirstNameLastName(firstName, lastName)) {
                    showToast("กรุณาระบุชื่อนามสกุลเป็นภาษาไทยให้ถูกต้องและห้ามเว้นว่าง");
                } else if (!validateTelephoneNumber(telephoneNumber)) {
                    showToast("กรุณาระบุเบอร์โทรศัพท์ให้ถูกต้อง");
                } else if (!validateAddress(houseVillage, subDistrict, district)) {
                    showToast("กรุณาระบุที่อยู่ให้ถูกต้อง");
                } else if (birthDate == null) {
                    showToast("กรุณาระบุวันเกิด");
                } else {
                    progressDialog.show();
                    insertMemberList();
                }
                break;
            case R.id.btnDatePicker:
                datePickerDialog.setYearRange(1910, 2017);
                datePickerDialog.show(getFragmentManager(), "datePicker");
                break;
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
                    showToast("ลงทะเบียนสำเร็จ");
                    saveLoginMemberToInternalStorage(dao); // เก็บข้อมูลผู้ป่วยลงไฟล์เพื่อกระจายให้ Activity อื่นๆ เรียกใช้งาน
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish(); // เรียก Activity ที่ถือครอง Fragment ขึ้นมา แล้วสั่งทำลาย Activity
                } else if (dao.getSuccess() == 0) { // ลงทะเบียนไม่สำเร็จ
                    progressDialog.dismiss();
                    showToast("ขออภัยลงทะเบียนไม่สำเร็จ");
                }
            } else { // 404 NOT FOUND
                progressDialog.dismiss();
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลงทะเบียนอีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MemberItemCollectionDao> call,
                              Throwable t) {

            progressDialog.dismiss();
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // spinner Province
        province = spinnerProvince.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT); // กำหนด Date Format
        tvBirthDate.setText(simpleDateFormat.format(date));                 // แปลง Date เป็น String และแสดงใน TextView

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT); // กำหนด Date Format ให้ตรงกับ Format in Server
        birthDate = java.sql.Date.valueOf(simpleDateFormat.format(date));   // แปลง Date เป็น String และส่งให้ฟังก์ชัน valueOf แปลงเป็น Date Sql ไปเก็บไว้ในตัวแปร birthDate เพื่อเตรียมส่งให้ Server
    }


    /**************
     * Inner Class
     **************/

}