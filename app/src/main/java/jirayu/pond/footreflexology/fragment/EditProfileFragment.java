package jirayu.pond.footreflexology.fragment;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusItemDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.StringsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener {

    /************
     * Variables
     ************/

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
    StringsManager stringsManager;
    String firstName,
            lastName,
            gender,
            telephoneNumber,
            houseVillage,
            subDistrict,
            district,
            province,
            identificationNumber;
    java.sql.Date birthDate;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    SharedPreferences sharedPreferences;
    int id;

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
        initSharedPreferences();
        initInstances(rootView);
        loadMemberList(); // โหลดข้อมูลเก่าไปแสดงในหน้าแก้ไขก่อน
        return rootView;
    }

    private void initOptionsMenu() {
        // Edit Title in Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("แก้ไขประวัติส่วนตัว");
    }

    private void initSharedPreferences() {
        sharedPreferences = getContext().getSharedPreferences("loginMember",
                Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);
        identificationNumber = sharedPreferences.getString("identification_number", null);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnFloatingAction = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingAction);
        btnFloatingAction.setVisibility(Switch.GONE);
        editFirstName = (EditText) rootView.findViewById(R.id.edit_first_name);
        editLastName = (EditText) rootView.findViewById(R.id.edit_last_name);
        editTelephoneNumber = (EditText) rootView.findViewById(R.id.edit_telephone_number);
        editAddress = (EditText) rootView.findViewById(R.id.edit_address);
        editSubDistrict = (EditText) rootView.findViewById(R.id.edit_sub_district);
        editDistrict = (EditText) rootView.findViewById(R.id.edit_district);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.rdGroup);
        spinnerProvince = (Spinner) rootView.findViewById(R.id.spinnerProvince);
        tvBirthDate = (TextView) rootView.findViewById(R.id.tvBirthDate);
        btnDatePicker = (ImageButton) rootView.findViewById(R.id.btnDatePicker);
        stringsManager = new StringsManager();
        setDate();
        createSpinner();

        // Handle Click
        btnFloatingAction.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
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
        spinnerProvince.setAdapter(adapterProvince); // สั่งให้ spinner กับ adapter ทำงานร่วมกัน
        spinnerProvince.setOnItemSelectedListener(this); // Handle Click Spinner
    }

    private void loadAnimation() {
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),
                R.anim.fab_open);
        btnFloatingAction.startAnimation(anim);
        btnFloatingAction.setVisibility(Switch.VISIBLE);
    }

    private void loadMemberList() {
        Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().loadMemberList(
                "members",
                identificationNumber
        );
        call.enqueue(loadMemberList);
    }

    private void insertMemberList() {
        Call<StatusItemDao> call = HttpManager.getInstance().getService().UpdateMember(
                id,
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
                new Timestamp(System.currentTimeMillis())  // GET เวลาปัจจุบันเก็บในตัวแปร updatedAt
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
        // Create Dialog
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
                    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);      // กำหนด Date Format
                    tvBirthDate.setText(simpleDateFormat.format(birthDate));    // แปลง Date เป็น String
                    editTelephoneNumber.setText(dao.getData().get(0).getTelephoneNumber());
                    editAddress.setText(dao.getData().get(0).getHouseVillage());
                    editSubDistrict.setText(dao.getData().get(0).getSubDistrict());
                    editDistrict.setText(dao.getData().get(0).getDistrict());
                    stringsManager.setWord(dao.getData().get(0).getProvince());
                    spinnerProvince.setSelection(stringsManager.getProvinceId());
                    loadAnimation();    // FAB Animation
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

    Callback<StatusItemDao> insertMemberList = new Callback<StatusItemDao>() {
        @Override
        public void onResponse(Call<StatusItemDao> call,
                               Response<StatusItemDao> response) {

            if (response.isSuccessful()) {
                StatusItemDao dao = response.body();
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
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<StatusItemDao> call,
                              Throwable t) {

            progressDialog.dismiss();
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    /*
     * Handle DatePicker
     */
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT);  // กำหนด Date Format
        tvBirthDate.setText(simpleDateFormat.format(date));                  // แปลง Date เป็น String และแสดงใน TextView

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);  // กำหนด Date Format ให้ตรงกับ Format in Server
        birthDate = java.sql.Date.valueOf(simpleDateFormat.format(date));   // แปลง Date เป็น String และส่งให้ฟังก์ชัน valueOf แปลงเป็น Date Sql ไปเก็บไว้ในตัวแปร birthDate เพื่อเตรียมส่งให้ Server
    }

    /**************
     * Inner Class
     **************/

}
