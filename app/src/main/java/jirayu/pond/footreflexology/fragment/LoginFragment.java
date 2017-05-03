package jirayu.pond.footreflexology.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.MainActivity;
import jirayu.pond.footreflexology.activity.RegisterActivity;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.database.DatabaseHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    /************
     * Variables
     ************/

    TextView tvAppName;
    AutoCompleteTextView autotvName;
    Button btnSignUp;
    Animation anim;
    ProgressDialog progressDialog;
    String identificationNumber;
    ArrayList<String> arrListIdentificationNumber;

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper dataBaseHelper;
    Cursor cursor;

    /************
     * Functions
     ************/

    public LoginFragment() {
        super();
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_anim);
        initDatabase();
        initInstances(rootView);
        initAutoCompleteTextView();
        return rootView;
    }

    private void initDatabase() {
        // Opening Database
        dataBaseHelper = new DatabaseHelper(Contextor.getInstance().getContext());
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        // Query Data to Cursor
        cursor = sqLiteDatabase.rawQuery("SELECT *  FROM " + DatabaseHelper.tableName, null);

        arrListIdentificationNumber = new ArrayList<>();
        cursor.moveToFirst(); // = 0
        while (!cursor.isAfterLast()) {
            arrListIdentificationNumber.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.columnIdentificationNumber))); // Get เลขบัตรฯ ใส่ ArrayList
            cursor.moveToNext();
        }
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvAppName = (TextView) rootView.findViewById(R.id.tvAppName);
        autotvName = (AutoCompleteTextView) rootView.findViewById(R.id.autotvName);
        btnSignUp = (Button) rootView.findViewById(R.id.btnSignUp);

        // Play Animation
        anim.setDuration(500);
        tvAppName.startAnimation(anim);
        anim.setDuration(800);
        autotvName.startAnimation(anim);
        anim.setDuration(1100);
        btnSignUp.startAnimation(anim);

        // Handle Click
        btnSignUp.setOnClickListener(this);
    }

    private void initAutoCompleteTextView() {
        // Create Adapter of AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, arrListIdentificationNumber);
        autotvName.setAdapter(adapter); // Set Adapter to AutoCompleteTextView
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataBaseHelper.close();
        sqLiteDatabase.close();
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

    private void loadMemberList() {
        Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().loadMemberList(
                "members",
                identificationNumber
        );
        call.enqueue(loadMemberList);
    }

    private void addIdentificationNumberToDatabase() {
        // ตรวจสอบเลขบัตรฯมีในฐานข้อมูลหรือไม่
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHelper.tableName
                + " WHERE " + DatabaseHelper.columnIdentificationNumber
                + "='" + identificationNumber + "'", null);

        // ถ้าใน cursor มีข้อมูล 0 แถว ก็แปลว่าไม่มีเลขบัตรฯในฐานข้อมูล
        if (cursor.getCount() == 0) {
            sqLiteDatabase.execSQL("INSERT INTO " + DatabaseHelper.tableName
                    + " (" + DatabaseHelper.columnIdentificationNumber + ") "
                    + "VALUES ('" + identificationNumber + "');");
        }
    }

    // Check Internet Access
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    Callback<MemberItemCollectionDao> loadMemberList = new Callback<MemberItemCollectionDao>() {
        @Override
        public void onResponse(Call<MemberItemCollectionDao> call,
                               Response<MemberItemCollectionDao> response) {
            if (response.isSuccessful()) {
                MemberItemCollectionDao dao = response.body();
                addIdentificationNumberToDatabase();
                if (dao.getData().isEmpty()) { // ไม่พบข้อมูลผู้ป่วย ให้ลงทะเบียนผู้ป่วย
                    progressDialog.dismiss();
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    intent.putExtra("identificationNumber", identificationNumber);
                    startActivity(intent);
                } else { // พบข้อมูลผู้ป่วย เข้าสู่หน้าหลัก
                    // TODO : เก็บข้อมูลผู้ป่วยลงไฟล์
                    DataMemberManager.getInstance().setMemberItemDao(dao.getData().get(0)); // เอาข้อมูลสมาชิกไปเก็บไว้ที่ Singleton เพื่อกระจายให้คนอื่นๆ เรียกใช้งาน
                    progressDialog.dismiss();   // Cancel Dialog
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish(); // เรียก Activity ที่ถือครอง Fragment ขึ้นมา แล้วสั่งทำลาย Activity นั้น
                }
            } else {
                progressDialog.dismiss();
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MemberItemCollectionDao> call,
                              Throwable t) {
            progressDialog.dismiss();
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        // Create Dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setTitle("รอสักครู่...");
        progressDialog.setMessage("กำลังตรวจสอบข้อมูล");
        identificationNumber = autotvName.getText().toString();

        switch (v.getId()) {
            case R.id.btnSignUp:
                if (isOnline()) {   // ตรวจสอบว่าเชื่อมต่ออินเทอร์เน็ตหรือไม่
                    if (autotvName.getText().toString().trim().length() == 0){    // ตรวจสอบว่า editText ว่างหรือไม่
                        showToast("กรุณาป้อนหมายเลขบัตรประชาชน 13 หลัก");
                    } else if (autotvName.getText().toString().trim().length() < 13){
                        showToast("กรุณาป้อนหมายเลขบัตรประชาชนให้ครบ 13 หลัก");
                    } else {
                        progressDialog.show();
                        loadMemberList();
                    }
                } else {
                    showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
                }
                break;
        }
    }

    /***************
     * Inner Class
     **************/

}
