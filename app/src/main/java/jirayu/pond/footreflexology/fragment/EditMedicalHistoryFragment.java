package jirayu.pond.footreflexology.fragment;

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
import jirayu.pond.footreflexology.dao.BehaviorCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusDao;
import jirayu.pond.footreflexology.manager.BehaviorManager;
import jirayu.pond.footreflexology.manager.DataMemberManager;
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
    int rowId, behaviorId;

    BehaviorManager behaviorManager;
    MedicalHistoryManager medicalHistoryManager;

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
        initInstances(rootView);
        return rootView;
    }

    private void initOptionsMenu() {
        // Edit Title in Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("แก้ไขประวัติการรักษา");
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
        loadDisease();
        loadBehavior();
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
        Call<BehaviorCollectionDao> call = HttpManager.getInstance().getService().loadBehavior(
                "behaviors"
        );
        call.enqueue(loadBehavior);
    }

    private void loadDisease() {
        // โหลดรายชื่อโรคจากตารางประวัติการรักษา
        Call<MedicalHistoryItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistory(
                "medicalhistorys",
                DataMemberManager.getInstance().getMemberItemDao().getId()
        );
        call.enqueue(loadDisease);
    }

    private void createSpinnerBehavior() {
        // Behavior
        adapterBehavior = new ArrayAdapter<>(Contextor.getInstance().getContext(),
                android.R.layout.simple_spinner_item, behavior);
        adapterBehavior.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBehavior.setAdapter(adapterBehavior);
        spinnerBehavior.setOnItemSelectedListener(this);
    }

    private void createSpinnerDisease() {
        // Disease
        adapterDisease = new ArrayAdapter<>(Contextor.getInstance().getContext(),
                android.R.layout.simple_spinner_item, disease);
        adapterDisease.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisease.setAdapter(adapterDisease);
        spinnerDisease.setOnItemSelectedListener(this);
    }

    private void updateMedicalHistory() {
        Call<StatusDao> call = HttpManager.getInstance().getService().UpdateMedicalHistory(
                rowId,
                behaviorId,
                new Timestamp(System.currentTimeMillis())   // GET เวลาปัจจุบัน
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

    Callback<BehaviorCollectionDao> loadBehavior = new Callback<BehaviorCollectionDao>() {
        @Override
        public void onResponse(Call<BehaviorCollectionDao> call, Response<BehaviorCollectionDao> response) {
            if (response.isSuccessful()) {
                BehaviorCollectionDao dao = response.body();
                behavior = new ArrayList<>();
                // add json to array list
                for (int i = 0; i < dao.getData().size(); i++) {
                    behavior.add(dao.getData().get(i).getList());
                }
                createSpinnerBehavior();
                behaviorManager = new BehaviorManager(dao);
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<BehaviorCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
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
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MedicalHistoryItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    Callback<StatusDao> updateMedicalHistory = new Callback<StatusDao>() {
        @Override
        public void onResponse(Call<StatusDao> call, Response<StatusDao> response) {
            if (response.isSuccessful()) {
                StatusDao dao = response.body();
                if (dao.getSuccess() == 1) {
                    showToast("แก้ไขประวัติการรักษาสำเร็จ");
                    getFragmentManager().popBackStack(); // remove fragment ตัวปัจจุบัน
                }
                else {
                    showToast("แก้ไขประวัติการรักษาไม่สำเร็จ");
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<StatusDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
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
        if (v == btnSave) {
            // Update Data
            updateMedicalHistory();
        }
    }

    /**************
     * Inner Class
     **************/

}
