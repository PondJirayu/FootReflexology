package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.adapter.MedicalHistoryAdapter;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
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

    SwipeRefreshLayout swipeRefreshLayout;

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
        initOptionsMenu();
        initInstances(rootView);
        return rootView;
    }

    private void initOptionsMenu() {
        // สั่งให้ Fragment แสดง option menu ของตัวเอง
        setHasOptionsMenu(true);

        // Edit Title in Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("ประวัติการรักษา");

        // Edit Subtitle in Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("ของ" +
                DataMemberManager.getInstance().getMemberItemDao().getFirstName() + " " +
                DataMemberManager.getInstance().getMemberItemDao().getLastName());
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView); // create listView
        listAdapter = new MedicalHistoryAdapter();  // create Adapter
        listView.setAdapter(listAdapter);           // สั่งให้ listView with adapter ทำงานร่วมกัน
        listView.setOnItemClickListener(listViewItemClickListener); // เมื่อ user click ที่ listView ให้เรียกคำสั่งนี้
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(pullToRefresh);     // เมื่อ pull to refresh ให้เรียกคำสั่งนี้

        // Load Data
        reloadData();
    }

    private void reloadData() {
        Call<MedicalHistoryItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistory(
                "medicalhistorys",
                DataMemberManager.getInstance().getMemberItemDao().getId()
        );
        call.enqueue(loadMedicalHistory);
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

    public void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    // reload Data
    Callback<MedicalHistoryItemCollectionDao> loadMedicalHistory = new Callback<MedicalHistoryItemCollectionDao>() {
        @Override
        public void onResponse(Call<MedicalHistoryItemCollectionDao> call,
                               Response<MedicalHistoryItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);    // สั่งให้ Pull to Refresh หยุดหมุน
            if (response.isSuccessful()) {
                MedicalHistoryItemCollectionDao dao = response.body();
                if (dao.getData().isEmpty()) {
                    showToast("ไม่พบประวัติการรักษา");
                } else { // พบข้อมูล
                    listAdapter.setDao(dao);            // โยน dao ให้ Adapter
                    listAdapter.notifyDataSetChanged(); // adapter สั่งให้ listView refresh ตัวเอง
                }
            } else { // 404 NOT FOUND
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MedicalHistoryItemCollectionDao> call,
                              Throwable t) {
            swipeRefreshLayout.setRefreshing(false);    // สั่งให้ Pull to Refresh หยุดหมุน
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    // Inflate Options Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_medical_history, menu);
    }

    // Handle Pull to Refresh
    SwipeRefreshLayout.OnRefreshListener pullToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            reloadData();
        }
    };

    // Handle Click ListView
    AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showToast("" + position);
        }
    };

    /**************
     * Inner Class
     **************/
}
