package jirayu.pond.footreflexology.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.MainActivity;
import jirayu.pond.footreflexology.activity.RegisterActivity;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.MemberItemDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
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
    EditText editName;
    Button btnSignUp, btnIntoMainPage;
    Animation anim;
    RelativeLayout rootLayout;
    ProgressDialog progressDialog;
    String identificationNumber;

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
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvAppName = (TextView) rootView.findViewById(R.id.tvAppName);
        editName = (EditText) rootView.findViewById(R.id.editName);
        btnSignUp = (Button) rootView.findViewById(R.id.btnSignUp);
        btnIntoMainPage = (Button) rootView.findViewById(R.id.btnIntoMainPage);
        rootLayout = (RelativeLayout) rootView.findViewById(R.id.rootLayout);

        // Play Animation
        anim.setDuration(500);
        tvAppName.startAnimation(anim);
        anim.setDuration(800);
        editName.startAnimation(anim);
        anim.setDuration(1100);
        btnSignUp.startAnimation(anim);
        anim.setDuration(1400);
        btnIntoMainPage.startAnimation(anim);

        // Handle Click
        btnSignUp.setOnClickListener(this);
        btnIntoMainPage.setOnClickListener(this);
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

    // Function Check Internet Access
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
        progressDialog.setMessage("กำลังตรวจสอบข้อมูล");

        identificationNumber = editName.getText().toString();

        if (v == btnSignUp) {
            if (isOnline()) {   // ตรวจสอบว่าเชื่อมต่ออินเทอร์เน็ตหรือไม่
                if (editName.getText().toString().trim().length() == 0){    // ตรวจสอบว่า editText ว่างหรือไม่
                    Snackbar.make(rootLayout, "กรุณาป้อนหมายเลขบัตรประชาชน 13 หลัก", Snackbar.LENGTH_LONG).show();
                } else if (editName.getText().toString().trim().length() < 13){
                    Snackbar.make(rootLayout, "กรุณาป้อนหมายเลขบัตรประชาชนให้ครบ 13 หลัก", Snackbar.LENGTH_LONG).show();
                } else {
                    progressDialog.show();
                    Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().loadMemberList("members", identificationNumber);
                    call.enqueue(new Callback<MemberItemCollectionDao>() {
                        @Override
                        public void onResponse(Call<MemberItemCollectionDao> call,
                                               Response<MemberItemCollectionDao> response) {

                            if (response.isSuccessful()) {
                                MemberItemCollectionDao dao = response.body();
                                if (dao.getData().isEmpty()) { // ไม่พบข้อมูลผู้ป่วย ให้ลงทะเบียนผู้ป่วย
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                                    intent.putExtra("identificationNumber", identificationNumber);
                                    startActivity(intent);
                                } else { // พบข้อมูลผู้ป่วย เข้าสู่หน้าหลัก
                                    DataMemberManager.getInstance().setMemberItemDao(dao.getData().get(0)); // เอาข้อมูลสมาชิกไปเก็บไว้ที่ Singleton เพื่อกระจายให้คนอื่นๆ เรียกใช้งาน
                                    progressDialog.dismiss();   // ยกเลิก Dialog
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish(); // เรียก Activity ที่ถือครอง Fragment ขึ้นมา แล้วสั่งทำลาย Activity นั้น
                                }
                            } else {
//                                try {
//                                    Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
                                progressDialog.dismiss();
                                Snackbar.make(rootLayout, "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง", Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MemberItemCollectionDao> call,
                                              Throwable t) {
//                            Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Snackbar.make(rootLayout, "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ", Snackbar.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                Snackbar.make(rootLayout, "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ", Snackbar.LENGTH_LONG).show();
            }
        }
        if (v == btnIntoMainPage) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("isShowDrawerMenu", false);
            startActivity(intent);
        }
    }

    /***************
     * Inner Class
     **************/

}
