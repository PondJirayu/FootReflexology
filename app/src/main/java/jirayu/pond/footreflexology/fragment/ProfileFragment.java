package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.Date;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.StringsManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static jirayu.pond.footreflexology.R.string.age;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ProfileFragment extends Fragment {

    /************
     * Variables
     ************/

    TextView tvFirstName, tvLastName, tvGender, tvBirthDate, tvAge, tvIdentificationNumber,
            tvTelephoneNumber, tvHouseVillage, tvSubDistrict, tvDistrict, tvProvince;
    StringsManager stringsManager;

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
        init();
        initInstances(rootView);
        return rootView;
    }

    private void init() {
        // สั่งให้ Fragment แสดง Option Menu ของตัวเอง
        setHasOptionsMenu(true);

        // Edit Title in Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ประวัติส่วนตัว");
    }

    private void initInstances(View rootView) {
        // Edit Title in Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ประวัติส่วนตัว");
        // Init 'View' instance(s) with rootView.findViewById here
        tvFirstName = (TextView) rootView.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) rootView.findViewById(R.id.tvLastName);
        tvGender = (TextView) rootView.findViewById(R.id.tvGender);
        tvBirthDate = (TextView) rootView.findViewById(R.id.tvBirthDate);
        tvAge = (TextView)  rootView.findViewById(R.id.tvAge);
        tvIdentificationNumber = (TextView) rootView.findViewById(R.id.tvIdentificationNumber);
        tvTelephoneNumber = (TextView)  rootView.findViewById(R.id.tvTelephoneNumber);
        tvHouseVillage = (TextView) rootView.findViewById(R.id.tvHouseVillage);
        tvSubDistrict = (TextView)  rootView.findViewById(R.id.tvSubDistrict);
        tvDistrict = (TextView) rootView.findViewById(R.id.tvDistrict);
        tvProvince = (TextView) rootView.findViewById(R.id.tvProvince);
        stringsManager = new StringsManager();
    }

    @Override
    public void onStart() {
        loadProfile();
        super.onStart();
    }

    private String calAge(MemberItemCollectionDao dao) {
        String birthDate = dao.getData().get(0).getBirthDate();
        int year = Integer.parseInt(birthDate.substring(0, 4)) - 543; // แปลง พ.ศ. เป็น ค.ศ.
        int month = Integer.parseInt(birthDate.substring(5, 7));
        int day = Integer.parseInt(birthDate.substring(8));

        // คำนวณอายุ
        LocalDate Bd = new LocalDate(year, month, day);
        LocalDate now = new LocalDate();
        Years age = Years.yearsBetween(Bd, now);

        return Integer.toString(age.getYears());
    }


    private void loadProfile() {
        Call<MemberItemCollectionDao> call = HttpManager.getInstance().getService().loadMemberList(
                "members",
                DataMemberManager.getInstance().getMemberItemDao().getIdentificationNumber()
        );
        call.enqueue(loadProfile);
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

    public void showToast(String text) {
        Toast.makeText(getContext(),
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
                    tvFirstName.setText(dao.getData().get(0).getFirstName());
                    tvLastName.setText(dao.getData().get(0).getLastName());
                    tvGender.setText(dao.getData().get(0).getGender());
                    stringsManager.setWord(dao.getData().get(0).getBirthDate());
                    tvBirthDate.setText(stringsManager.getChangeBirthDate());
                    tvAge.setText(calAge(dao));
                    tvIdentificationNumber.setText(dao.getData().get(0).getIdentificationNumber());
                    tvTelephoneNumber.setText(dao.getData().get(0).getTelephoneNumber());
                    tvHouseVillage.setText(dao.getData().get(0).getHouseVillage());
                    tvSubDistrict.setText(dao.getData().get(0).getSubDistrict());
                    tvDistrict.setText(dao.getData().get(0).getDistrict());
                    tvProvince.setText(dao.getData().get(0).getProvince());
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

    // Inflate Options Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    /***************
     * Inner Class
     **************/
}
