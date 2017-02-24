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
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusDao;
import jirayu.pond.footreflexology.manager.BehaviorManager;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.DiseaseManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddMedicalHistoryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Button btnSave;
    Spinner spinnerBehavior, spinnerDisease;

    ArrayAdapter<String> adapterBehavior, adapterDisease;
    List<String> disease, behavior;
    Boolean successBehavior = false, successDisease = false;
    int diseaseId, behaviorId;
    String diseaseName;
    MedicalHistoryItemCollectionDao medicalHistoryItemCollectionDao;

    DiseaseManager diseaseManager;
    BehaviorManager behaviorManager;

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
        initInstances(rootView);
        return rootView;
    }

    private void initOptionsMenu() {
        // Edit Title in Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("เพิ่มประวัติการรักษา");
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
        loadDisease();
        loadBehavior();
        loadMedicalHistory();
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
                DataMemberManager.getInstance().getMemberItemDao().getId()
        );
        call.enqueue(loadMedicalHistory);
    }

    private void loadBehavior() {
        Call<BehaviorCollectionDao> call = HttpManager.getInstance().getService().loadBehavior(
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

    Callback<StatusDao> insertMedicalHistory = new Callback<StatusDao>() {
        @Override
        public void onResponse(Call<StatusDao> call, Response<StatusDao> response) {
            if (response.isSuccessful()) {
                StatusDao dao = response.body();
                if (dao.getSuccess() == 1) {
                    showToast("เพิ่มประวัติการรักษาแล้ว");
                    getFragmentManager().popBackStack(); // remove fragment ตัวปัจจุบันทิ้ง
                } else {
                    showToast("เพิ่มประวัติการรักษาไม่สำเร็จ โปรดลองอีกครั้งในภายหลัง");
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

    Callback<DiseaseItemCollectionDao> loadDisease = new Callback<DiseaseItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseItemCollectionDao> call, Response<DiseaseItemCollectionDao> response) {
            if (response.isSuccessful()) {
                DiseaseItemCollectionDao dao = response.body();
                disease = new ArrayList<>();
                // add json to array list
                for (int i = 0; i < dao.getData().size(); i++) {
                    disease.add(dao.getData().get(i).getDiseaseName());
                }
                successDisease = true;
                createSpinnerDisease();
                // เอาข้อมูลไปฝากท่ DiseaseManager
                diseaseManager = new DiseaseManager(dao);
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<DiseaseItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ 2");
        }
    };

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
                successBehavior = true;
                createSpinnerBehavior();
                // เอาข้อมูลไปฝากที่ BehaviorManager
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

    Callback<MedicalHistoryItemCollectionDao> loadMedicalHistory = new Callback<MedicalHistoryItemCollectionDao>() {
        @Override
        public void onResponse(Call<MedicalHistoryItemCollectionDao> call, Response<MedicalHistoryItemCollectionDao> response) {
            if (response.isSuccessful()) {
                medicalHistoryItemCollectionDao = response.body();
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MedicalHistoryItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerBehavior && successBehavior) {
            behaviorManager.setBehavior(spinnerBehavior.getItemAtPosition(position).toString()); // โยน String เข้าไปที่ Manager เพื่อหา ID ของ String ดังกล่าว
            behaviorId = behaviorManager.getBehaviorId(); // return Id of String
        }
        if (parent.getId() == R.id.spinnerDisease && successDisease) {
            diseaseName = spinnerDisease.getItemAtPosition(position).toString(); // เอาชื่อโรคไปเก็บในตัวแปร เพื่อเอาไปใช้งานใน func checkMedicalHistory
            diseaseManager.setDisease(spinnerDisease.getItemAtPosition(position).toString());
            diseaseId = diseaseManager.getDiseaseId();
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
            if (checkMedicalHistory()) {
                showToast("มีประวัติการรักษาอยู่แล้ว");
            } else {
                // Insert MedicalHistory Here
                Call<StatusDao> call = HttpManager.getInstance().getService().InsertMedicalHistory(
                        DataMemberManager.getInstance().getMemberItemDao().getId(),
                        diseaseId,
                        behaviorId,
                        new Timestamp(System.currentTimeMillis()),      // GET เวลาปัจจุบัน
                        new Timestamp(System.currentTimeMillis())       // GET เวลาปัจจุบัน
                );
                call.enqueue(insertMedicalHistory);
            }
        }
    }

    /**************
     * Inner Class
     **************/

}
