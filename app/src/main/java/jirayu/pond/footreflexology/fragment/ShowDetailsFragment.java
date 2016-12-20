package jirayu.pond.footreflexology.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.adapter.DetailsListAdapter;
import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ShowDetailsFragment extends Fragment {

    /************
     * Variables
     ************/

    ListView listView;
    DetailsListAdapter listAdapter;
    RelativeLayout rootLayout;
    ProgressDialog progressDialog;
    String result;

    /************
     * Functions
     ************/

    public ShowDetailsFragment() {
        super();
    }

    public static ShowDetailsFragment newInstance(String result) {
        ShowDetailsFragment fragment = new ShowDetailsFragment();
        Bundle args = new Bundle();
        args.putString("result", result);   // เอาตัวแปร result เก็บใน Arguments
        fragment.setArguments(args);        // สั่งให้ Arguments ผุกกับ Fragment
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments / อ่านค่าจาก Arguments ใส่ Member Variable
        result = getArguments().getString("result");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView); // Create ListView
        listAdapter = new DetailsListAdapter();     // Create Adapter
        listView.setAdapter(listAdapter);   // สั่งให้ ListView with Adapter ทำงานร่วมกัน

//        reloadData();
    }

    private void reloadData() {
        // Create Dialog
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(true);
//        progressDialog.setTitle("รอสักครู่...");
//        progressDialog.setMessage("กำลังดาวโหลดข้อมูล");
//        progressDialog.show();

        Call<DetailItemCollectionDao> call = HttpManager.getInstance().getService().loadDetailList("details", result);
        call.enqueue(new Callback<DetailItemCollectionDao>() {
            @Override
            public void onResponse(Call<DetailItemCollectionDao> call,
                                   Response<DetailItemCollectionDao> response) {
//                if (response.isSuccessful()) {
//                    DetailItemCollectionDao dao = response.body();
//                    if (dao.getData().isEmpty()) {
////                        progressDialog.dismiss();
//                        Snackbar.make(rootLayout, "ไม่พบข้อมูล", Snackbar.LENGTH_LONG).show();
//                    } else {
//                        // ส่ง dao ให้ Adapter
//                    }
//                } else {
////                    progressDialog.dismiss();
//                    Snackbar.make(rootLayout, "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง", Snackbar.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onFailure(Call<DetailItemCollectionDao> call, Throwable t) {
//                Snackbar.make(rootLayout, "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ", Snackbar.LENGTH_LONG).show();
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
