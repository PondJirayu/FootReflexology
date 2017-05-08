package jirayu.pond.footreflexology.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.BehaviorItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusItemDao;
import jirayu.pond.footreflexology.manager.BehaviorManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.MedicalHistoryManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class EditMedicalHistoryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Button btnSave;
    Spinner spinnerDisease, spinnerBehavior;
    List<String> disease, behavior;
    ArrayAdapter<String> adapterDisease, adapterBehavior;
    int rowId, behaviorId, id;
    BehaviorManager behaviorManager;
    MedicalHistoryManager medicalHistoryManager;
    SharedPreferences sharedPreferences;

    /************
     * Functions
     ************/

    public EditMedicalHistoryFragment() {
        super();
    }

    public static EditMedicalHistoryFragment newInstance() {
        EditMedicalHistoryFragment fragment = new EditMedicalHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_medical_history, container, false);
        initOptionsMenu();
        initSharedPreferences();
        initInstances(rootView);
        loadDisease();
        loadBehavior();
        return rootView;
    }

    private void initOptionsMenu() {
        // Edit Title in Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("อัพเดทประวัติการรักษา");
    }

    private void initSharedPreferences() {
        sharedPreferences = getContext().getSharedPreferences("loginMember",
                Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        spinnerDisease = (Spinner) rootView.findViewById(R.id.spinnerDisease);
        spinnerBehavior = (Spinner) rootView.findViewById(R.id.spinnerBehavior);

        // Handle Click
        btnSave.setOnClickListener(this);
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

    private void loadBehavior() {
        Call<BehaviorItemCollectionDao> call = HttpManager.getInstance().getService().loadBehavior(
                "behaviors"
        );
        call.enqueue(loadBehavior);
    }

    private void loadDisease() {
        // โหลดรายชื่อโรคจากตารางประวัติการรักษา
        Call<MedicalHistoryItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistory(
                "medicalhistorys",
                id,
                0
        );
        call.enqueue(loadDisease);
    }

    private void createSpinnerBehavior() {
        // Behavior
        adapterBehavior = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, behavior);
        adapterBehavior.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBehavior.setAdapter(adapterBehavior);
        spinnerBehavior.setOnItemSelectedListener(this);
    }

    private void createSpinnerDisease() {
        // Disease
        adapterDisease = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, disease);
        adapterDisease.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisease.setAdapter(adapterDisease);
        spinnerDisease.setOnItemSelectedListener(this);
    }

    private void updateMedicalHistory() {
        Call<StatusItemDao> call = HttpManager.getInstance().getService().UpdateMedicalHistory(
                rowId,
                behaviorId,
                new Timestamp(System.currentTimeMillis()), // GET เวลาปัจจุบัน
                new Timestamp(System.currentTimeMillis())
        );
        call.enqueue(updateMedicalHistory);
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

    Callback<BehaviorItemCollectionDao> loadBehavior = new Callback<BehaviorItemCollectionDao>() {
        @Override
        public void onResponse(Call<BehaviorItemCollectionDao> call, Response<BehaviorItemCollectionDao> response) {
            if (response.isSuccessful()) {
                BehaviorItemCollectionDao dao = response.body();
                behavior = new ArrayList<>();
                // add json to array list
                for (int i = 0; i < dao.getData().size(); i++) {
                    behavior.add(dao.getData().get(i).getList());
                }
                createSpinnerBehavior();
                behaviorManager = new BehaviorManager(dao);
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<BehaviorItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    Callback<MedicalHistoryItemCollectionDao> loadDisease = new Callback<MedicalHistoryItemCollectionDao>() {
        @Override
        public void onResponse(Call<MedicalHistoryItemCollectionDao> call, Response<MedicalHistoryItemCollectionDao> response) {
            if (response.isSuccessful()) {
                MedicalHistoryItemCollectionDao dao = response.body();
                if (dao.getData().isEmpty()) {
                    showToast("ไม่พบประวัติการรักษา");
                } else {
                    disease = new ArrayList<>();
                    // add json to array list
                    for (int i = 0; i < dao.getData().size(); i++) {
                        disease.add(dao.getData().get(i).getDiseaseName());
                    }
                    createSpinnerDisease();
                    medicalHistoryManager = new MedicalHistoryManager(dao);
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MedicalHistoryItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    Callback<StatusItemDao> updateMedicalHistory = new Callback<StatusItemDao>() {
        @Override
        public void onResponse(Call<StatusItemDao> call, Response<StatusItemDao> response) {
            if (response.isSuccessful()) {
                StatusItemDao dao = response.body();
                if (dao.getSuccess() == 1) {
                    showToast("แก้ไขประวัติการรักษาสำเร็จ");
                    getFragmentManager().popBackStack(); // remove fragment ตัวปัจจุบัน
                }
                else {
                    showToast("แก้ไขประวัติการรักษาไม่สำเร็จ");
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<StatusItemDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerDisease) {
            // โยนค่าที่ user เลือก เข้าไปที่ Manager
            medicalHistoryManager.setDisease(spinnerDisease.getItemAtPosition(position).toString());
            rowId = medicalHistoryManager.getMedicalHistoryId();
        }

        if (parent.getId() == R.id.spinnerBehavior) {
            behaviorManager.setBehavior(spinnerBehavior.getItemAtPosition(position).toString());
            behaviorId = behaviorManager.getBehaviorId();
        }
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
            case R.id.btnSave:
                // Update Data
                updateMedicalHistory();
                break;
        }
    }

    /**************
     * Inner Class
     **************/

}
