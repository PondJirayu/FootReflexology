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

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.adapter.DiseaseListAdapter;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryResponseFragment extends Fragment {

    /************
     * Variables
     ************/

    ListView listView;
    DiseaseListAdapter listAdapter;
    MenuItem menuItem;
    String query;
    DiseaseItemCollectionDao dao;

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
        // Read from Arguments - อ่านค่าจาก Arguments ใส่ Member Variable
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
        setHasOptionsMenu(true);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView); // Create ListView
        listAdapter = new DiseaseListAdapter(); // Create Adapter
        listView.setAdapter(listAdapter);   // สั่งให้ ListView with Adapter ทำงานร่วมกัน
    }

    @Override
    public void onStart() {
        loadDisease();
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

    private void loadDisease() {
        Call<DiseaseItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseList(
                "diseases",
                query
        );
        call.enqueue(loadDiseaseListener);
    }

    private Intent getShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "โรค" + dao.getData().get(0).getDiseaseName());
        intent.putExtra(Intent.EXTRA_TEXT,
                "รายละเอียด" + "\n"
                        + "\t\t" + dao.getData().get(0).getDetail() + "\n\n" +
                        "การรักษา" + "\n"
                        + "\t\t" + dao.getData().get(0).getTreatment() + "\n\n" +
                        "อาหารที่ตวรรับประทาน" + "\n"
                        + dao.getData().get(0).getShouldEat() + "\n\n" +
                        "อาหารที่ควรหลีกเลี่ยง" + "\n"
                        + dao.getData().get(0).getShouldNotEat() + "\n\n" +
                        "คำแนะนำ" + "\n"
                        + "\t\t" + dao.getData().get(0).getRecommend()
        );
        return intent;
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

    Callback<DiseaseItemCollectionDao> loadDiseaseListener = new Callback<DiseaseItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseItemCollectionDao> call,
                               Response<DiseaseItemCollectionDao> response) {
            if (response.isSuccessful()) {
                dao = response.body();
                if (dao.getData().isEmpty()) { // ไม่พบข้อมูล
                    showToast("ไม่พบโรคที่ค้นหา");
                } else { // พบช้อมูล
                    // Share Button
                    ShareActionProvider shareActionProvider =  (ShareActionProvider)
                            MenuItemCompat.getActionProvider(menuItem);
                    shareActionProvider.setShareIntent(getShareIntent());
                    listAdapter.setDao(dao);            // โยน detailItemCollectionDao ให้ adapter
                    listAdapter.notifyDataSetChanged(); // Adapter สั่งให้ ListView Refresh ตัวเอง
                }
            } else { // 404 NOT FOUND
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนองโปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<DiseaseItemCollectionDao> call,
                              Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่าย");
        }
    };

    /*
     * Inflate Options Menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_more_info, menu);
        menuItem = menu.findItem(R.id.action_share); // เข้าถึงปุ่ม Share
    }

    /**************
     * Inner Class
     **************/
}
