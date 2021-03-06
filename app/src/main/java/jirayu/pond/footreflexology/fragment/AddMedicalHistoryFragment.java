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
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusItemDao;
import jirayu.pond.footreflexology.manager.BehaviorManager;
import jirayu.pond.footreflexology.manager.DiseaseManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedicalHistoryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Button btnSave;
    Spinner spinnerBehavior, spinnerDisease;
    ArrayAdapter<String> adapterBehavior, adapterDisease;
    Boolean successBehavior = false, successDisease = false;
    int diseaseId, behaviorId, id;
    String diseaseName;
    MedicalHistoryItemCollectionDao medicalHistoryItemCollectionDao;
    DiseaseManager diseaseManager;
    BehaviorManager behaviorManager;
    SharedPreferences sharedPreferences;

    /************
     * Functions
     ************/

    public AddMedicalHistoryFragment() {
        super();
    }

    public static AddMedicalHistoryFragment newInstance() {
        AddMedicalHistoryFragment fragment = new AddMedicalHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_medical_history, container, false);
        initOptionsMenu();
        initSharedPreferences();
        initInstances(rootView);
        loadDisease();
        loadBehavior();
        loadMedicalHistory();
        return rootView;
    }

    private void initOptionsMenu() {
        // Edit Title in Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("เพิ่มประวัติการรักษา");
    }

    private void initSharedPreferences() {
        sharedPreferences = getContext().getSharedPreferences("loginMember",
                Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", -1);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        spinnerBehavior = (Spinner) rootView.findViewById(R.id.spinnerBehavior);
        spinnerDisease = (Spinner) rootView.findViewById(R.id.spinnerDisease);

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

    private void loadMedicalHistory() {
        Call<MedicalHistoryItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistory(
                "medicalhistorys",
                id,
                0
        );
        call.enqueue(loadMedicalHistory);
    }

    private void loadBehavior() {
        Call<BehaviorItemCollectionDao> call = HttpManager.getInstance().getService().loadBehavior(
                "behaviors"
        );
        call.enqueue(loadBehavior);
    }

    private void loadDisease() {
        Call<DiseaseItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseList(
                "diseases",
                ""
        );
        call.enqueue(loadDisease);
    }

    private void insertMedicalHistory() {
        Call<StatusItemDao> call = HttpManager.getInstance().getService().InsertMedicalHistory(
                id,
                diseaseId,
                behaviorId,
                new Timestamp(System.currentTimeMillis()), // GET เวลาปัจจุบัน
                new Timestamp(System.currentTimeMillis())  // GET เวลาปัจจุบัน
        );
        call.enqueue(insertMedicalHistory);
    }

    /*
     * Behaviors
     */
    private void createSpinnerBehavior(ArrayList<String> behavior) {
        adapterBehavior = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                behavior
        );
        adapterBehavior.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBehavior.setAdapter(adapterBehavior);
        spinnerBehavior.setOnItemSelectedListener(this);
    }

    /*
     * Diseases
     */
    private void createSpinnerDisease(ArrayList<String> disease) {
        adapterDisease = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                disease
        );
        adapterDisease.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisease.setAdapter(adapterDisease);
        spinnerDisease.setOnItemSelectedListener(this);
    }

    private boolean checkMedicalHistory() {
        if (medicalHistoryItemCollectionDao.getData().isEmpty()) {
            return false;
        } else {
            for (int i = 0 ; i < medicalHistoryItemCollectionDao.getData().size() ; i++){
                if (medicalHistoryItemCollectionDao.getData().get(i).getDiseaseName().equals(diseaseName))
                    return true;
            }
        }
        return false;
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

    Callback<MedicalHistoryItemCollectionDao> loadMedicalHistory = new Callback<MedicalHistoryItemCollectionDao>() {
        @Override
        public void onResponse(Call<MedicalHistoryItemCollectionDao> call, Response<MedicalHistoryItemCollectionDao> response) {
            if (response.isSuccessful()) {
                medicalHistoryItemCollectionDao = response.body();
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MedicalHistoryItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    Callback<BehaviorItemCollectionDao> loadBehavior = new Callback<BehaviorItemCollectionDao>() {
        @Override
        public void onResponse(Call<BehaviorItemCollectionDao> call, Response<BehaviorItemCollectionDao> response) {
            if (response.isSuccessful()) {
                BehaviorItemCollectionDao dao = response.body();
                ArrayList<String> behavior = new ArrayList<>();
                // add json to array list
                for (int i = 0; i < dao.getData().size(); i++) {
                    behavior.add(dao.getData().get(i).getList());
                }
                successBehavior = true;
                createSpinnerBehavior(behavior);
                behaviorManager = new BehaviorManager(dao); // เอาข้อมูลไปฝากที่ BehaviorManager
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<BehaviorItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    Callback<DiseaseItemCollectionDao> loadDisease = new Callback<DiseaseItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseItemCollectionDao> call, Response<DiseaseItemCollectionDao> response) {
            if (response.isSuccessful()) {
                DiseaseItemCollectionDao dao = response.body();
                ArrayList<String> disease = new ArrayList<>();
                // add json to array list
                for (int i = 0; i < dao.getData().size(); i++) {
                    disease.add(dao.getData().get(i).getDiseaseName());
                }
                successDisease = true;
                createSpinnerDisease(disease);
                diseaseManager = new DiseaseManager(dao); // เอาข้อมูลไปฝากท่ DiseaseManager
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<DiseaseItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    Callback<StatusItemDao> insertMedicalHistory = new Callback<StatusItemDao>() {
        @Override
        public void onResponse(Call<StatusItemDao> call, Response<StatusItemDao> response) {
            if (response.isSuccessful()) {
                StatusItemDao dao = response.body();
                if (dao.getSuccess() == 1) {
                    showToast("เพิ่มประวัติการรักษาแล้ว");
                    getFragmentManager().popBackStack(); // remove fragment ตัวปัจจุบันทิ้ง
                } else {
                    showToast("เพิ่มประวัติการรักษาไม่สำเร็จโปรดลองอีกครั้งในภายหลัง");
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

    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerBehavior:
                if (successBehavior) {
                    behaviorManager.setBehavior(spinnerBehavior.getItemAtPosition(position).toString()); // โยน String เข้าไปที่ Manager เพื่อหา ID ของ String ดังกล่าว
                    behaviorId = behaviorManager.getBehaviorId(); // return Id of String
                }
                break;
            case R.id.spinnerDisease:
                if (successDisease) {
                    diseaseName = spinnerDisease.getItemAtPosition(position).toString(); // เอาชื่อโรคไปเก็บในตัวแปร เพื่อเอาไปใช้งานใน func checkMedicalHistory
                    diseaseManager.setDisease(spinnerDisease.getItemAtPosition(position).toString());
                    diseaseId = diseaseManager.getDiseaseId();
                }
                break;
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
                if (checkMedicalHistory()) {
                    showToast("มีประวัติการรักษาอยู่แล้ว");
                } else {
                    insertMedicalHistory(); // insert MedicalHistory here
                }
                break;
        }
    }

    /**************
     * Inner Class
     **************/

}
