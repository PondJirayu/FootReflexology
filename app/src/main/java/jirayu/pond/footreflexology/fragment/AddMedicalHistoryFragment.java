package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.BehaviorCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddMedicalHistoryFragment extends Fragment implements View.OnClickListener {

    /************
     * Variables
     ************/

    Button btnSave;
    DiseaseItemCollectionDao diseaseItemCollectionDao;
    BehaviorCollectionDao behaviorCollectionDao;

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

        loadDisease();
        loadBehavior();
        createSpinner();

        btnSave.setOnClickListener(this);
    }

    private void loadBehavior() {
        Call<BehaviorCollectionDao> call = HttpManager.getInstance().getService().loadBehavior("behaviors");
        call.enqueue(loadBehavior);

    }

    private void loadDisease() {
        Call<DiseaseItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseList("diseases", null);
        call.enqueue(loadDisease);

    }

    private void createSpinner() {
        // Create Adapter of Spinner

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


    /****************
     * Listener Zone
     ****************/

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            // Insert MedicalHistory Here
            Call<StatusDao> call = HttpManager.getInstance().getService().InsertMedicalHistory(
                    DataMemberManager.getInstance().getMemberItemDao().getId(),
                    1,
                    1
            );
            call.enqueue(insertMedicalHistory);
            getFragmentManager().popBackStack();
        }
    }

    Callback<StatusDao> insertMedicalHistory = new Callback<StatusDao>() {
        @Override
        public void onResponse(Call<StatusDao> call, Response<StatusDao> response) {
            if (response.isSuccessful()) {
                StatusDao dao = response.body();
                if (dao.getSuccess() == 1) {
                    Toast.makeText(getActivity(),
                            "เพิ่มประวัติการรักษาแล้ว",
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getActivity(),
                            "เพิ่มประวัติการรักษาไม่สำเร็จ โปรดลองอีกครั้งในภายหลัง",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            } else {
                Toast.makeText(getActivity(),
                        "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onFailure(Call<StatusDao> call, Throwable t) {
            Toast.makeText(getActivity(),
                    "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

    Callback<DiseaseItemCollectionDao> loadDisease = new Callback<DiseaseItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseItemCollectionDao> call, Response<DiseaseItemCollectionDao> response) {
            if (response.isSuccessful()){
                diseaseItemCollectionDao = response.body();
                if (diseaseItemCollectionDao.getData().isEmpty()) {

                } else {

                }
            } else {
                Toast.makeText(getActivity(),
                        "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onFailure(Call<DiseaseItemCollectionDao> call, Throwable t) {
            Toast.makeText(getActivity(),
                    "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

    Callback<BehaviorCollectionDao> loadBehavior = new Callback<BehaviorCollectionDao>() {
        @Override
        public void onResponse(Call<BehaviorCollectionDao> call, Response<BehaviorCollectionDao> response) {
            if (response.isSuccessful()){
                behaviorCollectionDao = response.body();
                if (behaviorCollectionDao.getData().isEmpty()) {

                } else {

                }
            } else {
                Toast.makeText(getActivity(),
                        "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onFailure(Call<BehaviorCollectionDao> call, Throwable t) {
            Toast.makeText(getActivity(),
                    "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };

    /**************
     * Inner Class
     **************/

}
