package jirayu.pond.footreflexology.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.text.SimpleDateFormat;
import java.util.Locale;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.StringsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    /************
     * Variables
     ************/

    ImageView ivPerson, ivPlace;
    FloatingActionButton btnFloatingAction;
    TextView tvFirstName,
             tvLastName,
             tvGender,
             tvBirthDate,
             tvAge,
             tvIdentificationNumber,
             tvTelephoneNumber,
             tvHouseVillage,
             tvSubDistrict,
             tvDistrict,
             tvProvince,
             tvTitleFirstLastName,
             tvTitleGender,
             tvTitleBirthDate,
             tvTitleAge,
             tvTitleYear,
             tvTitleIdentificationNumber,
             tvTitleTelephoneNumber,
             tvTitleHouseVillage,
             tvTitleSubDistrict,
             tvTitleDistrict,
             tvTitleProvince;
    StringsManager stringsManager;
    SimpleDateFormat simpleDateFormat;
    SharedPreferences sharedPreferences;
    String identificationNumber;

    /************
     * Functions
     ************/

    public ProfileFragment() {
        super();
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initOptionsMenu();
        initSharedPreferences();
        initInstances(rootView);
        loadProfile();
        return rootView;
    }

    private void initOptionsMenu() {
        // Edit Title in Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ประวัติส่วนตัว");
    }

    private void initSharedPreferences() {
        sharedPreferences = getContext().getSharedPreferences(
                "loginMember",
                Context.MODE_PRIVATE
        );
        identificationNumber = sharedPreferences.getString("identification_number", null);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        ivPerson = (ImageView) rootView.findViewById(R.id.ivPerson);
        ivPlace = (ImageView) rootView.findViewById(R.id.ivPlace);
        btnFloatingAction = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingAction);
        btnFloatingAction.setVisibility(Switch.GONE);
        tvTitleFirstLastName = (TextView) rootView.findViewById(R.id.tvTitleFirstLastName);
        tvFirstName = (TextView) rootView.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) rootView.findViewById(R.id.tvLastName);
        tvTitleGender = (TextView) rootView.findViewById(R.id.tvTitleGender);
        tvGender = (TextView) rootView.findViewById(R.id.tvGender);
        tvTitleBirthDate = (TextView) rootView.findViewById(R.id.tvTitleBirthDate);
        tvBirthDate = (TextView) rootView.findViewById(R.id.tvBirthDate);
        tvTitleAge = (TextView) rootView.findViewById(R.id.tvTitleAge);
        tvAge = (TextView) rootView.findViewById(R.id.tvAge);
        tvTitleYear = (TextView) rootView.findViewById(R.id.tvTitleYear);
        tvTitleIdentificationNumber = (TextView) rootView.findViewById(R.id.tvTitleIdentificationNumber);
        tvIdentificationNumber = (TextView) rootView.findViewById(R.id.tvIdentificationNumber);
        tvTitleTelephoneNumber = (TextView) rootView.findViewById(R.id.tvTitleTelephoneNumber);
        tvTelephoneNumber = (TextView) rootView.findViewById(R.id.tvTelephoneNumber);
        tvTitleHouseVillage = (TextView) rootView.findViewById(R.id.tvTitleHouseVillage);
        tvHouseVillage = (TextView) rootView.findViewById(R.id.tvHouseVillage);
        tvTitleSubDistrict = (TextView) rootView.findViewById(R.id.tvTitleSubDistrict);
        tvSubDistrict = (TextView) rootView.findViewById(R.id.tvSubDistrict);
        tvTitleDistrict = (TextView) rootView.findViewById(R.id.tvTitleDistrict);
        tvDistrict = (TextView) rootView.findViewById(R.id.tvDistrict);
        tvTitleProvince = (TextView) rootView.findViewById(R.id.tvTitleProvince);
        tvProvince = (TextView) rootView.findViewById(R.id.tvProvince);
        hideView();

        stringsManager = new StringsManager();

        // Handle Click (FAB)
        btnFloatingAction.setOnClickListener(this);
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

    private void showView() {
        ivPerson.setVisibility(Switch.VISIBLE);
        ivPlace.setVisibility(Switch.VISIBLE);
        tvTitleFirstLastName.setVisibility(Switch.VISIBLE);
        tvTitleGender.setVisibility(Switch.VISIBLE);
        tvTitleBirthDate.setVisibility(Switch.VISIBLE);
        tvTitleAge.setVisibility(Switch.VISIBLE);
        tvTitleYear.setVisibility(Switch.VISIBLE);
        tvTitleIdentificationNumber.setVisibility(Switch.VISIBLE);
        tvTitleTelephoneNumber.setVisibility(Switch.VISIBLE);
        tvTitleHouseVillage.setVisibility(Switch.VISIBLE);
        tvTitleSubDistrict.setVisibility(Switch.VISIBLE);
        tvTitleDistrict.setVisibility(Switch.VISIBLE);
        tvTitleProvince.setVisibility(Switch.VISIBLE);
    }

    private void hideView() {
        ivPerson.setVisibility(Switch.GONE);
        ivPlace.setVisibility(Switch.GONE);
        tvTitleFirstLastName.setVisibility(Switch.GONE);
        tvTitleGender.setVisibility(Switch.GONE);
        tvTitleBirthDate.setVisibility(Switch.GONE);
        tvTitleAge.setVisibility(Switch.GONE);
        tvTitleYear.setVisibility(Switch.GONE);
        tvTitleIdentificationNumber.setVisibility(Switch.GONE);
        tvTitleTelephoneNumber.setVisibility(Switch.GONE);
        tvTitleHouseVillage.setVisibility(Switch.GONE);
        tvTitleSubDistrict.setVisibility(Switch.GONE);
        tvTitleDistrict.setVisibility(Switch.GONE);
        tvTitleProvince.setVisibility(Switch.GONE);
    }

    private void loadFabAnimation() {
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.fab_open
        );
        btnFloatingAction.startAnimation(anim);
        btnFloatingAction.setVisibility(Switch.VISIBLE);
    }

    private String calAge(MemberItemCollectionDao dao) {
        Years years = Years.yearsBetween(new LocalDate(dao.getData().get(0).getBirthDate()),
                new LocalDate());
        return years.getYears() + "";
    }

    private void loadProfile() {
        Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().loadMemberList(
                "members",
                identificationNumber
        );
        call.enqueue(loadProfile);
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

    Callback<MemberItemCollectionDao> loadProfile = new Callback<MemberItemCollectionDao>() {
        @Override
        public void onResponse(Call<MemberItemCollectionDao> call, Response<MemberItemCollectionDao> response) {
            if (response.isSuccessful()) {
                MemberItemCollectionDao dao = response.body();
                if (dao.getData().isEmpty()) { // ไม่พบข้อมูลผู้ป่วย
                    showToast("ไม่พบข้อมูลผู้ป่วย");
                } else { // พบข้อมูลผู้ป่วย
                    showView();
                    tvFirstName.setText(dao.getData().get(0).getFirstName());
                    tvLastName.setText(dao.getData().get(0).getLastName());
                    tvGender.setText(dao.getData().get(0).getGender());
                    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ROOT); // กำหนด Date Format
                    tvBirthDate.setText(simpleDateFormat.format(dao.getData().get(0).getBirthDate())); // แปลง Date เป็น String
                    tvAge.setText(calAge(dao)); // ส่งวันเกิดไปคำนวณหาอายุในฟังก์ชัน calAge()
                    tvIdentificationNumber.setText(dao.getData().get(0).getIdentificationNumber());
                    tvTelephoneNumber.setText(dao.getData().get(0).getTelephoneNumber());
                    tvHouseVillage.setText(dao.getData().get(0).getHouseVillage());
                    tvSubDistrict.setText(dao.getData().get(0).getSubDistrict());
                    tvDistrict.setText(dao.getData().get(0).getDistrict());
                    tvProvince.setText(dao.getData().get(0).getProvince());
                    loadFabAnimation(); // FAB Animation
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MemberItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFloatingAction:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_left,
                                R.anim.from_left, R.anim.to_right
                        )
                        .replace(R.id.contentContainer, EditProfileFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    /***************
     * Inner Class
     **************/
}
