package jirayu.pond.footreflexology.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.DiseaseWithOrganItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.util.ButtonAlertPositionUtils;
import jirayu.pond.footreflexology.util.ButtonAlertUtils;
import jirayu.pond.footreflexology.util.InfoDialogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class InTheFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Spinner spinnerFoot;
    ArrayAdapter<CharSequence> adapter;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    private int lastPosition = -1, id;
    private final int SIZE = 14 + 1;
    private int position[][] = ButtonAlertPositionUtils.getAlertViewInTheFootPosition();
    private ArrayList<ButtonAlertUtils> btnAlerts = new ArrayList<>();
    private ArrayList<String> organName = new ArrayList<>();
    SharedPreferences sharedPreferences;

    /************
     * Functions
     ************/

    public InTheFootFragment() {
        super();
    }

    public static InTheFootFragment newInstance() {
        InTheFootFragment fragment = new InTheFootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inthefoot, container, false);
        initSharedPreferences();
        initInstances(rootView);
        initOrganName();
        initBtnAlert();
        loadDiseaseWithOrgan();
        return rootView;
    }

    private void initSharedPreferences() {
        sharedPreferences = getContext().getSharedPreferences("loginMember",
                Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerFoot = (Spinner) rootView.findViewById(R.id.spinnerFoot);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerFoot.setOnItemSelectedListener(this);   // Handle Click Spinner

        // Handle Click
        imgBtnInfo.setOnClickListener(this);
    }

    private void initOrganName() {
        organName.add("ลำไส้ใหญ่ตรง");
        organName.add("ไต");
        organName.add("ข้อสะโพก");
        organName.add("มดลูกและต่อมลูกหมาก");
        organName.add("กระดูกก้นกบ");
        organName.add("ช่องคลอดองคชาติและท่อปัสสาวะ");
        organName.add("กระเพาะปัสสาวะ");
        organName.add("กระดูกสันหลัง");
        organName.add("กระดูกสันหลังอก");
        organName.add("กระดูกสันหลังคอ");
        organName.add("จมูก");
        organName.add("ระบบต่อมไร้ท่อ");
        organName.add("ต่อมน้ำเหลืองบริเวณท่อนล่างร่างกาย");
        organName.add("ต่อมน้ำเหลืองขาหนีบ");
    }

    private void initBtnAlert() {
        for (int i = 0; i < SIZE; i++) {
            btnAlerts.add(new ButtonAlertUtils(getContext(), 4, 38, 38, position[i][0], position[i][1])); // New Object
        }

        // Add OrganName to btnAlert
        for (int i = 0; i < SIZE; i++) {
            if (i != 14) {
                btnAlerts.get(i).setOrganName(organName.get(i));
            } else {
                btnAlerts.get(i).setOrganName(organName.get(4));
            }
            layoutAlert.addView(btnAlerts.get(i).getBtnAlert(), btnAlerts.get(i).getParams()); // Add to Layout
            btnAlerts.get(i).hideAlertView(); // Hide
        }
     }

    private void loadDiseaseWithOrgan() {
        Call<DiseaseWithOrganItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseWithOrgan(
                "diseasewithorgan",
                id
        );
        call.enqueue(loadDiseaseWithOrgan);
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

    private void initBehaviors(DiseaseWithOrganItemCollectionDao dao) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < dao.getDiseaseWithOrganItemDaos().size(); j++) {
                for (int k = 0; k < dao.getDiseaseWithOrganItemDaos().get(j).size(); k++) {
                    if (btnAlerts.get(i).getOrganName().equals(dao.getDiseaseWithOrganItemDaos().get(j).get(k).getOrganName())) {
                        btnAlerts.get(i).setBackgroundView(dao.getBehaviorOfDiseaseWithOrganItemDaos().get(j).getBehaviorId());
                        btnAlerts.get(i).showAlertView();
                    }
                }
            }
        }
    }

    // Create Adapter of Spinner
    private void createAdapter() {
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.in_the_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFoot.setAdapter(adapter); // สั่งให้ spinner ทำงานคู่กับ adapter
    }

    private void showAlertView(int position) {
        if (lastPosition != -1) btnAlerts.get(lastPosition).hideAlertView(); // ซ่อน AlertView ตัวเก่า
        if (lastPosition == 4) btnAlerts.get(13 + 1).hideAlertView(); // ซ่อน AlertView ตัวซ้ำ
        for (int i = 0; i < SIZE; i++) {
            if (i == position) {
                btnAlerts.get(i).showAlertView();
                if (position == 4) btnAlerts.get(13 + 1).showAlertView();
                lastPosition = position;
                break;
            }
        }
    }

    public void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    Callback<DiseaseWithOrganItemCollectionDao> loadDiseaseWithOrgan = new Callback<DiseaseWithOrganItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseWithOrganItemCollectionDao> call, Response<DiseaseWithOrganItemCollectionDao> response) {
            if (response.isSuccessful()) {
                DiseaseWithOrganItemCollectionDao dao = response.body();
                if (dao.getDiseaseWithOrganItemDaos().isEmpty()) {
                    showToast("ไม่พบข้อมูลผู้ป่วย");
                } else {
                    initBehaviors(dao);
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<DiseaseWithOrganItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };


    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showAlertView(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnInfo:
                InfoDialogUtils infoDialog = new InfoDialogUtils(getContext());
                infoDialog.showDialog();
                break;
        }
    }

    /**************
     * Inner Class
     **************/
}
