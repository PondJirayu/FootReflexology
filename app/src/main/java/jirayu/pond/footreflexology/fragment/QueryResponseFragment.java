package jirayu.pond.footreflexology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.adapter.DiseaseListAdapter;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class QueryResponseFragment extends Fragment {

    /************
     * Variables
     ************/

    String query;
    ListView listView;
    DiseaseListAdapter listAdapter;
    DiseaseItemCollectionDao dao;
    MenuItem menuItem;

    /************
     * Functions
     ************/

    public QueryResponseFragment() {
        super();
    }

    public static QueryResponseFragment newInstance(String query) {
        QueryResponseFragment fragment = new QueryResponseFragment();
        Bundle args = new Bundle();
        args.putString("query", query); // เอาตัวแปร query เก็บใน Arguments
        fragment.setArguments(args);    // สั่งให้ Arguments ผุกกับ Fragment
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments / อ่านค่าจาก Arguments ใส่ Member Variable
        query = getArguments().getString("query");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_query_response, container, false);
        initOptionMenu();
        initInstances(rootView);
        return rootView;
    }

    private void initOptionMenu() {
        // show option menu
        setHasOptionsMenu(true);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView); // create listView
        listAdapter = new DiseaseListAdapter(); // create Adapter
        listView.setAdapter(listAdapter);   // สั่งให้ listView with Adapter ทำงานร่วมกัน

        // ติดต่อกับ Server
        reloadData();
    }

    private void reloadData() {
        Call<DiseaseItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseList("diseases", query);
        call.enqueue(loadDiseaseListener);
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

    Callback<DiseaseItemCollectionDao> loadDiseaseListener = new Callback<DiseaseItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseItemCollectionDao> call,
                               Response<DiseaseItemCollectionDao> response) {
            if (response.isSuccessful()) {
                dao = response.body();
                if (dao.getData().isEmpty()) { // ไม่พบข้อมูล
                    Toast.makeText(getContext(), "ไม่พบโรคที่ค้นหา", Toast.LENGTH_SHORT).show();
                } else { // พบช้อมูล
                    // Share Button
                    ShareActionProvider shareActionProvider =  (ShareActionProvider)
                            MenuItemCompat.getActionProvider(menuItem);
                    shareActionProvider.setShareIntent(getShareIntent());
                    listAdapter.setDao(dao); // โยน dao ให้ adapter
                    listAdapter.notifyDataSetChanged(); // สั่ง refresh list view
                }
            } else { // 404 NOT FOUND
                Toast.makeText(getContext(), "ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<DiseaseItemCollectionDao> call,
                              Throwable t) {
            Toast.makeText(getContext(), "กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ", Toast.LENGTH_SHORT).show();
        }
    };

    // inflate option menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_more_info, menu);
        menuItem = menu.findItem(R.id.action_share); // เข้าถึงปุ่ม share
    }

    private Intent getShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "โรค" + dao.getData().get(0).getDiseaseName());
        intent.putExtra(Intent.EXTRA_TEXT,
                    "รายละเอียด" + "\n"
                            + dao.getData().get(0).getDetail() + "\n\n" +
                    "การรักษา" + "\n"
                            + dao.getData().get(0).getTreatment() + "\n\n" +
                    "คำแนะนำ" + "\n"
                            + dao.getData().get(0).getRecommend());
        return intent;
    }

    /**************
     * Inner Class
     **************/
}
