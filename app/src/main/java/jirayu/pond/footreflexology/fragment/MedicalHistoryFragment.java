package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.adapter.MedicalHistoryAdapter;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemDao;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MedicalHistoryFragment extends Fragment {

    /************
     * Variables
     ************/

    ListView listView;
    MedicalHistoryAdapter listAdapter;

    /************
     * Functions
     ************/

    public MedicalHistoryFragment() {
        super();
    }

    public static MedicalHistoryFragment newInstance() {
        MedicalHistoryFragment fragment = new MedicalHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medical_history, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView); // create listView
        listAdapter = new MedicalHistoryAdapter();  // create Adapter
        listView.setAdapter(listAdapter);   // listView with Adapter ทำงานร่วมกัน

        // ติดต่อกับ server
        reloadData();
    }

    private void reloadData() {
        Call<MedicalHistoryItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistory("medicalhistorys", DataMemberManager.getInstance().getMemberItemDao().getId());
        call.enqueue(new Callback<MedicalHistoryItemCollectionDao>() {
            @Override
            public void onResponse(Call<MedicalHistoryItemCollectionDao> call, Response<MedicalHistoryItemCollectionDao> response) {
                if (response.isSuccessful()) {
                    MedicalHistoryItemCollectionDao dao = response.body();
                    if (dao.getData().isEmpty()) {
                        Toast.makeText(getContext(), "ไม่พบประวัติการรักษา", Toast.LENGTH_SHORT).show();
                    } else { // พบข้อมูล
                        listAdapter.setDao(dao); // โยน dao ให้ Adapter
                        listAdapter.notifyDataSetChanged(); // adapter สั่งให้ listView refresh ตัวเอง
                    }
                } else { // 404 NOT FOUND
                    Toast.makeText(getContext(), "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MedicalHistoryItemCollectionDao> call, Throwable t) {
                Toast.makeText(getContext(), "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ", Toast.LENGTH_SHORT).show();
            }
        });
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


    /**************
     * Inner Class
     **************/
}
