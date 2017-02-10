package jirayu.pond.footreflexology.fragment;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.StringsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class EditProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener {

    /************
     * Variables
     ************/

    java.sql.Date birthDate;
    Timestamp updatedAt;

    EditText editFirstName, editLastName, editTelephoneNumber, editAddress,
            editSubDistrict, editDistrict;
    RadioGroup radioGroup;
    TextView tvBirthDate;
    ImageButton btnDatePicker;
    Button btnSave;
    Spinner spinnerProvince;
    ProgressDialog progressDialog;

    ArrayAdapter<CharSequence> adapterProvince;

    StringsManager stringsManager;

    String firstName, lastName, gender, telephoneNumber, houseVillage, subDistrict,
            district, province;

    DatePickerDialog datePickerDialog;

    Calendar calendar;

    SimpleDateFormat simpleDateFormat;

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
        initOptionsMenu();
        initInstances(rootView);
        return rootView;
    }

    private void initOptionsMenu() {
        // Edit Title in Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("แก้ไขประวัติส่วนตัว");
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        editFirstName = (EditText) rootView.findViewById(R.id.edit_first_name);
        editLastName = (EditText) rootView.findViewById(R.id.edit_last_name);
        editTelephoneNumber = (EditText) rootView.findViewById(R.id.edit_telephone_number);
        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        editSubDistrict = (EditText) rootView.findViewById(R.id.edit_sub_district);
        editDistrict = (EditText) rootView.findViewById(R.id.edit_district);
        btnSave = (Button) rootView.findViewById(R.id.btnSignUp);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.rdGroup);
        spinnerProvince = (Spinner) rootView.findViewById(R.id.spinnerProvince);
        tvBirthDate = (TextView) rootView.findViewById(R.id.tvBirthDate);
        btnDatePicker = (ImageButton) rootView.findViewById(R.id.btnDatePicker);
        stringsManager = new StringsManager();

        setDate(); // จัดการเรื่องวันที่
        createSpinner();
        loadMemberList(); // โหลดข้อมูลเก่าไปแสดงในหน้าแก้ไขก่อน

        // Handle Click Button
        btnSave.setOnClickListener(this);
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
        // Create Adapter of Spinner(Province)
        adapterProvince = ArrayAdapter.createFromResource(getActivity(),
                R.array.province_names, android.R.layout.simple_spinner_item);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapterProvince);        // สั่งให้ spinner กับ adapter ทำงานร่วมกัน
        spinnerProvince.setOnItemSelectedListener(this);    // Handle Click Spinner
    }

    private void loadMemberList() {
        Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().loadMemberList(
                "members",
                DataMemberManager.getInstance().getMemberItemDao().getIdentificationNumber()
        );
        call.enqueue(loadMemberList);
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
        updatedAt = new Timestamp(System.currentTimeMillis());  // GET เวลาปัจจุบันเก็บในตัวแปร updatedAt
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rbMale:
                gender = "ชาย";
                break;
            case R.id.rbFemale:
                gender = "หญิง";
                break;
        }
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

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
            if (firstName.trim().length() == 0 || lastName.trim().length() == 0
                    || telephoneNumber.trim().length() == 0 || houseVillage.trim().length() == 0
                    || subDistrict.trim().length() == 0 || district.trim().length() == 0) {
                showToast("กรุณาป้อนข้อมูลให้ครบถ้วน");
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
                        updatedAt
                );
                call.enqueue(insertMemberList);
            }
        }
        // Handle DatePicker
        if (v == btnDatePicker) {
            datePickerDialog.setYearRange(1910, 2017);
            datePickerDialog.show(getFragmentManager(), "datePicker");
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
                    showToast("ไม่พบข้อมูลผู้ป่วย");
                } else {
                    editFirstName.setText(dao.getData().get(0).getFirstName());
                    editLastName.setText(dao.getData().get(0).getLastName());
                    if (dao.getData().get(0).getGender().equals("ชาย")) {
                        radioGroup.check(R.id.rbMale);
                    } else {
                       radioGroup.check(R.id.rbFemale);
                    }
                    birthDate = dao.getData().get(0).getBirthDate();            // ส่ง Date ไปเก็บไว้ในตัวแปร กรณีที่ User ไม่ได้เปลี่ยนแปลงวันเกิด
                    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");      // กำหนด Date Format
                    tvBirthDate.setText(simpleDateFormat.format(birthDate));    // แปลง Date เป็น String
                    editTelephoneNumber.setText(dao.getData().get(0).getTelephoneNumber());
                    editAddress.setText(dao.getData().get(0).getHouseVillage());
                    editSubDistrict.setText(dao.getData().get(0).getSubDistrict());
                    editDistrict.setText(dao.getData().get(0).getDistrict());
                    stringsManager.setWord(dao.getData().get(0).getProvince());
                    spinnerProvince.setSelection(stringsManager.getProvinceId());
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MemberItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
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
                    showToast("ขออภัยแก้ไขข้อมูลไม่สำเร็จ");
                } else {
                    progressDialog.dismiss();
                    showToast("แก้ไขข้อมูลแล้ว");
                    getFragmentManager().popBackStack();
                }
            } else { // 404
                progressDialog.dismiss();
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<StatusDao> call,
                              Throwable t) {

            progressDialog.dismiss();
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    // Handle DatePicker
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");  // กำหนด Date Format
        tvBirthDate.setText(simpleDateFormat.format(date));     // แปลง Date เป็น String และแสดงใน TextView

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");              // กำหนด Date Format ให้ตรงกับ Format in Server
        birthDate = java.sql.Date.valueOf(simpleDateFormat.format(date));   // แปลง Date เป็น String และส่งให้ฟังก์ชัน valueOf แปลงเป็น Date Sql ไปเก็บไว้ในตัวแปร birthDate เพื่อเตรียมส่งให้ Server
    }

    /**************
     * Inner Class
     **************/

}
