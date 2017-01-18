package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.BehaviorCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
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
    DiseaseItemCollectionDao diseaseItemCollectionDao;
    BehaviorCollectionDao behaviorCollectionDao;
    List<String> disease;
    List<String> behavior;
    ArrayAdapter<String> adapterBehavior, adapterDisease;
    Spinner spinnerBehavior, spinnerDisease;
    Boolean successBehavior = false, successDisease = false;
    int diseaseId = -1, behaviorId = -1;
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
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        spinnerBehavior = (Spinner) rootView.findViewById(R.id.spinnerBehavior);
        spinnerDisease = (Spinner) rootView.findViewById(R.id.spinnerDisease);

        // load disease with behavior from Server
        loadDisease();
        loadBehavior();

        btnSave.setOnClickListener(this);
    }

    private void loadBehavior() {
        Call<BehaviorCollectionDao> call = HttpManager.getInstance().getService().loadBehavior("behaviors");
        call.enqueue(loadBehavior);
    }

    private void loadDisease() {
        Call<DiseaseItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseList("diseases", "");
        call.enqueue(loadDisease);
    }

    private void createSpinnerBehavior() {
        // Behavior
        adapterBehavior = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, behavior);
        adapterBehavior.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBehavior.setAdapter(adapterBehavior);
        spinnerBehavior.setOnItemSelectedListener(this);
    }

    private void createSpinnerDisease() {
        // Disease
        adapterDisease = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, disease);
        adapterDisease.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisease.setAdapter(adapterDisease);
        spinnerDisease.setOnItemSelectedListener(this);
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

    private void showToast(String text) {
        Toast.makeText(getActivity(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            if (diseaseId == -1 || behaviorId == -1) {
                showToast("กรุณาเลือกข้อมูลให้ครบถ้วน");
            } else {
                // Insert MedicalHistory Here
                Call<StatusDao> call = HttpManager.getInstance().getService().InsertMedicalHistory(
                        DataMemberManager.getInstance().getMemberItemDao().getId(),
                        diseaseId,
                        behaviorId
                );
                call.enqueue(insertMedicalHistory);
            }
        }
    }

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
                if (dao.getData().isEmpty()) { // ไม่พบข้อมูล

                } else { // พบข้อมูล
                    diseaseItemCollectionDao = dao;
                    disease = new ArrayList<>();
                    // add json to array list
                    for (int i = 0; i < dao.getData().size(); i++) {
                        disease.add(diseaseItemCollectionDao.getData().get(i).getDiseaseName());
                    }
                    successDisease = true;
                    createSpinnerDisease();
                    // เอาข้อมูลไปฝากท่ DiseaseManager
                    diseaseManager = new DiseaseManager(dao);
                }
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
                if (dao.getData().isEmpty()) { // ไม่พบข้อมูล

                } else { // พบข้อมูล
                    behaviorCollectionDao = dao;
                    behavior = new ArrayList<>();
                    // add json to array list
                    for (int i = 0; i < dao.getData().size(); i++) {
                        behavior.add(behaviorCollectionDao.getData().get(i).getList());
                    }
                    successBehavior = true;
                    createSpinnerBehavior();
                    // เอาข้อมูลไปฝากที่ BehaviorManager
                    behaviorManager = new BehaviorManager(dao);
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<BehaviorCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    // Handle Click Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerBehavior && successBehavior) {
            behaviorManager.setBehavior(spinnerBehavior.getItemAtPosition(position).toString()); // โยน String เข้าไปที่ Manager เพื่อหา ID ของ String ดังกล่าว
            behaviorId = behaviorManager.getBehaviorId(); // return Id of String
        }
        if (parent.getId() == R.id.spinnerDisease && successDisease) {
            diseaseManager.setDisease(spinnerDisease.getItemAtPosition(position).toString());
            diseaseId = diseaseManager.getDiseaseId(); // return Id of String
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**************
     * Inner Class
     **************/

}
