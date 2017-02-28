package jirayu.pond.footreflexology.fragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    FloatingActionButton btnFloatingActionSort, btnFloatingActionEdit;

    MedicalHistoryAdapter listAdapter;
    Thread thread;
    Boolean doPullToRefresh;
    int selected = 0;
    MedicalHistoryItemCollectionDao dao;

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
        btnFloatingActionSort = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingActionSort);
        btnFloatingActionSort.setVisibility(Switch.GONE);
        btnFloatingActionEdit = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingActionEdit);
        btnFloatingActionEdit.setVisibility(Switch.GONE);
        doPullToRefresh = false;
        listView = (ListView) rootView.findViewById(R.id.listView); // Create ListView
        listAdapter = new MedicalHistoryAdapter();  // Create Adapter
        listView.setAdapter(listAdapter);           // ListView + Adapter
        listView.setOnItemClickListener(this);

        // Pull to Refresh
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // Handle Click (FAB)
        btnFloatingActionSort.setOnClickListener(this);
        btnFloatingActionEdit.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        loadMedicalHistory();
        super.onStart();
    }

    @Override
    public void onStop() {
        btnFloatingActionEdit.setVisibility(Switch.GONE);
        btnFloatingActionSort.setVisibility(Switch.GONE);
        doPullToRefresh = false;
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
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),
                R.anim.fab_open);
        btnFloatingActionSort.startAnimation(anim);
        btnFloatingActionSort.setVisibility(Switch.VISIBLE);
    }

    private void loadFabEditAnimation() {
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),
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

    /*
     * CreateSingleChoiceDialog
     */
    private void createSingleChoiceDialog() {
        CharSequence[] list = {
                "อาการ: แย่-ปกติ",
                "อาการ: ปกติ-แย่",
                "วันที่เข้ารับการรักษา: ล่าสุด",
                "วันที่เข้ารับการรักษา: หลังสุด",
                "วันที่แก้ไข: ล่าสุด",
                "วันที่แก้ไข: หลังสุด"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("เรียงลำดับ");
        builder.setSingleChoiceItems(list, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected = which; // Set ค่าที่ User เลือกใส่ตัวแปร Selected
            }
        });
        builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == -1) {
                    showToast(String.valueOf(selected));
                }
            }
        });
        builder.setNegativeButton("ยกเลิก", null).create();
        builder.show();
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
        public void onResponse(Call<MedicalHistoryItemCollectionDao> call,
                               Response<MedicalHistoryItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);    // สั่งให้ Pull to Refresh หยุดหมุน
            if (response.isSuccessful()) {
                dao = response.body();
                if (dao.getData().isEmpty()) {
                    showToast("ไม่พบประวัติการรักษา");
                } else { // พบข้อมูล
                    listAdapter.setDao(dao);            // โยน Dao ให้ Adapter
                    listAdapter.notifyDataSetChanged(); // Adapter สั่งให้ ListView Refresh ตัวเอง

                    // เมื่อมีการ Pull to Refresh ไม่ต้องทำ Animation ปุ่ม FAB
                    if (!doPullToRefresh) {
                        // JAVA Thread
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Run in Background Thread
                                try {
                                    Thread.sleep(400);
                                } catch (InterruptedException e) {
                                    return;
                                }

                                // if Activity เป็น null ให้จบการทำงาน
                                if (getActivity() == null)
                                    return;

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Run in UI Thread a.k.a. Main Thread
                                        loadFabEditAnimation();   // FAB Animation
                                    }
                                });

                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (getActivity() == null)
                                    return;

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Run in UI Thread a.k.a. Main Thread
                                        loadFabAddAnimation();    // FAB Animation
                                    }
                                });
                            }
                        });
                        thread.start();
                    }
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
        doPullToRefresh = true;
        loadMedicalHistory();
    }

    /*
     * Handle Click ListView Item
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), DetailsMedicalHistoryActivity.class);
        intent.putExtra("diseaseName", dao.getData().get(position).getDiseaseName()); // ฝากชื่อโรคไปแสดงรายละเอียดในหน้าถัดไป
        startActivity(intent);
    }

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        if (v == btnFloatingActionSort) {
            // Create Single Choice Dialog
            createSingleChoiceDialog();
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
            case R.id.action_add_medical_history:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.from_right, R.anim.to_left,
                                R.anim.from_left, R.anim.to_right
                        )
                        .replace(R.id.contentContainer,
                                AddMedicalHistoryFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**************
     * Inner Class
     **************/
}
