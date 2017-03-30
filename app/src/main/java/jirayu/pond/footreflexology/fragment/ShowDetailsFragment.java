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
import jirayu.pond.footreflexology.adapter.DetailsListAdapter;
import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
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
    MenuItem menuItem;

    String result;
    DetailItemCollectionDao detailItemCollectionDao;
    MedicalHistoryItemCollectionDao medicalHistoryItemCollectionDao;

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
        // Read from Arguments - อ่านค่าจาก Arguments ใส่ Member Variable
        result = getArguments().getString("result");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);
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
        listAdapter = new DetailsListAdapter();     // Create Adapter
        listView.setAdapter(listAdapter);           // สั่งให้ ListView with Adapter ทำงานร่วมกัน
    }

    @Override
    public void onStart() {
        loadDetailList();
        loadMedicalHistoryList();
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

    private void loadDetailList() {
        Call<DetailItemCollectionDao> call = HttpManager.getInstance().getService().loadDetailList(
                "details",
                result
        );
        call.enqueue(loadDetailList);
    }

    private void loadMedicalHistoryList() {
        Call<MedicalHistoryItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistory(
                "medicalhistorys",
                DataMemberManager.getInstance().getMemberItemDao().getId(),
                0
        );
        call.enqueue(loadMedicalHistory);
    }

    private Intent getShareIntent() {
        String moreContent = null, content2;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "เขตตอบสนองของ" + result);
        // Content
        for (int i = 0; i < detailItemCollectionDao.getData().size(); i++) {
            if (i == 0) {
                moreContent = "โรค" + detailItemCollectionDao.getData().get(i).getDiseaseName() + "\n\n" +
                        "รายละเอียด" + "\n"
                        + "\t\t" + detailItemCollectionDao.getData().get(i).getDetail() + "\n\n" +
                        "การรักษา" + "\n"
                        + "\t\t" + detailItemCollectionDao.getData().get(i).getTreatMent() + "\n\n" +
                        "อาหารที่ควรรับประทาน" + "\n"
                        + detailItemCollectionDao.getData().get(i).getShouldEat() + "\n\n" +
                        "อาหารที่ควรหลีกเลี่ยง" + "\n"
                        + detailItemCollectionDao.getData().get(i).getShouldNotEat() + "\n\n" +
                        "คำแนะนำ" + "\n"
                        + "\t\t" + detailItemCollectionDao.getData().get(i).getRecommend() + "\n\n";
            }
            if (i != 0) {
                content2 = "โรค" + detailItemCollectionDao.getData().get(i).getDiseaseName() + "\n\n" +
                        "รายละเอียด" + "\n"
                        + "\t\t" + detailItemCollectionDao.getData().get(i).getDetail() + "\n\n" +
                        "การรักษา" + "\n"
                        + "\t\t" + detailItemCollectionDao.getData().get(i).getTreatMent() + "\n\n" +
                        "อาหารที่ควรรับประทาน" + "\n"
                        + detailItemCollectionDao.getData().get(i).getShouldEat() + "\n\n" +
                        "อาหารที่ควรหลีกเลี่ยง" + "\n"
                        + detailItemCollectionDao.getData().get(i).getShouldNotEat() + "\n\n" +
                        "คำแนะนำ" + "\n"
                        + "\t\t" + detailItemCollectionDao.getData().get(i).getRecommend() + "\n\n";
                moreContent = moreContent.concat(content2);
            }
        }
        intent.putExtra(Intent.EXTRA_TEXT, moreContent);
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

    Callback<DetailItemCollectionDao> loadDetailList = new Callback<DetailItemCollectionDao>() {
        @Override
        public void onResponse(Call<DetailItemCollectionDao> call,
                               Response<DetailItemCollectionDao> response) {
            if (response.isSuccessful()) {
                detailItemCollectionDao = response.body();
                if (detailItemCollectionDao.getData().isEmpty()) { // ไม่พบข้อมูล
                    showToast("ไม่พบข้อมูลโรคที่เกี่ยวข้องกับอวัยวะดังกล่าว");
                } else { // พบข้อมูล
                    // Share Button
                    ShareActionProvider shareActionProvider = (ShareActionProvider)
                            MenuItemCompat.getActionProvider(menuItem);
                    shareActionProvider.setShareIntent(getShareIntent());
                    listAdapter.setDetailItemCollectionDao(detailItemCollectionDao);  // โยน detailItemCollectionDao ให้ Adapter
                    listAdapter.notifyDataSetChanged(); // Adapter สั่งให้ ListView Refresh ตัวเอง
                }
            } else { // 404 NOT FOUND
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<DetailItemCollectionDao> call,
                              Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    Callback<MedicalHistoryItemCollectionDao> loadMedicalHistory = new Callback<MedicalHistoryItemCollectionDao>() {
        @Override
        public void onResponse(Call<MedicalHistoryItemCollectionDao> call,
                               Response<MedicalHistoryItemCollectionDao> response) {
            if (response.isSuccessful()) {
                medicalHistoryItemCollectionDao = response.body();
                if (medicalHistoryItemCollectionDao.getData().isEmpty()) { // ไม่พบข้อมูล

                } else { // พบข้อมูล
                    listAdapter.setMedicalHistoryItemCollectionDao(medicalHistoryItemCollectionDao); // โยน medicalHistoryItemCollectionDao ให้ Adapter
                    listAdapter.notifyDataSetChanged(); // Adapter สั่งให้ ListView Refresh ตัวเอง
                }
            } else { // 404 NOT FOUND

            }
        }

        @Override
        public void onFailure(Call<MedicalHistoryItemCollectionDao> call, Throwable t) {

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
