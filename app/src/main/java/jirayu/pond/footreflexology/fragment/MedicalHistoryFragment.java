package jirayu.pond.footreflexology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.DetailsMedicalHistoryActivity;
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
public class MedicalHistoryFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    /************
     * Variables
     ************/

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton btnFloatingActionAdd, btnFloatingActionEdit;

    MedicalHistoryAdapter listAdapter;
    Thread thread;

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

    @Override
    public void onDestroyView() {
        thread.interrupt();
        super.onDestroyView();
    }

    private void initOptionsMenu() {
        setHasOptionsMenu(true);    // สั่งให้ Fragment แสดง option menu ของตัวเอง

        // Edit Title in Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ประวัติการรักษา");
        // Edit Subtitle in Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(
                "ของ" +
                        DataMemberManager.getInstance().getMemberItemDao().getFirstName() + " " +
                        DataMemberManager.getInstance().getMemberItemDao().getLastName()
        );
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnFloatingActionAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingActionAdd);
        btnFloatingActionEdit = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingActionEdit);
        listView = (ListView) rootView.findViewById(R.id.listView); // Create ListView
        listAdapter = new MedicalHistoryAdapter();  // Create Adapter
        listView.setAdapter(listAdapter);           // ListView + Adapter
        listView.setOnItemClickListener(this);

        // Pull to Refresh
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // Handle Click (FAB)
        btnFloatingActionAdd.setOnClickListener(this);
        btnFloatingActionEdit.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        btnFloatingActionAdd.setVisibility(Switch.GONE);
        btnFloatingActionEdit.setVisibility(Switch.GONE);
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

    private void loadFabAddAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getContext(),
                R.anim.fab_open);
        btnFloatingActionAdd.startAnimation(anim);
        btnFloatingActionAdd.setVisibility(Switch.VISIBLE);
    }

    private void loadFabEditAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getContext(),
                R.anim.fab_open);
        btnFloatingActionEdit.startAnimation(anim);
        btnFloatingActionEdit.setVisibility(Switch.VISIBLE);
    }

    private void loadMedicalHistory() {
        Call<MedicalHistoryItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistory(
                "medicalhistorys",
                DataMemberManager.getInstance().getMemberItemDao().getId()
        );
        call.enqueue(loadMedicalHistory);
    }

    private void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

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
                    listAdapter.setDao(dao);            // โยน Dao ให้ Adapter
                    listAdapter.notifyDataSetChanged(); // Adapter สั่งให้ ListView Refresh ตัวเอง
                    // Thread
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Run in Background Thread
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                return;
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Run in UI Thread a.k.a. Main Thread
                                    loadFabAddAnimation();    // FAB Animation
                                }
                            });

                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Run in UI Thread a.k.a. Main Thread
                                    loadFabEditAnimation();   // FAB Animation
                                }
                            });
                        }
                    });
                    thread.start();
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

    /*
     * Inflate Options Menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_medical_history, menu);
    }

    /*
     * Handle Pull to Refresh
     */
    @Override
    public void onRefresh() {
        loadMedicalHistory();
    }

    /*
     * Handle Click ListView Item
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), DetailsMedicalHistoryActivity.class);
        startActivity(intent);
    }

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        if (v == btnFloatingActionAdd) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.from_right, R.anim.to_left,
                            R.anim.from_left, R.anim.to_right
                    )
                    .replace(R.id.contentContainer,
                            AddMedicalHistoryFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
        if (v == btnFloatingActionEdit) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.from_right, R.anim.to_left,
                            R.anim.from_left, R.anim.to_right
                    )
                    .replace(R.id.contentContainer,
                            EditMedicalHistoryFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }

    /*
     * Handle Click Options Menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_medical_history:
                showToast("SORT");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**************
     * Inner Class
     **************/
}
