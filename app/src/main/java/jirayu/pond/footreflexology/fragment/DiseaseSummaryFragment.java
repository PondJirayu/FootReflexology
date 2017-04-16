package jirayu.pond.footreflexology.fragment;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.Locale;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DiseaseSummaryFragment extends Fragment {

    /************
     * Variables
     ************/

    TextView tvDiseaseName, tvDetail, tvTreatment, tvShouldEat, tvShouldNotEat, tvRecommend,
            tvTitleDiseaseName, tvTitleDetail, tvTitleTreatment, tvTitleShouldEat, tvTitleShouldNotEat, tvTitleRecommend;

    private String diseaseName;
    private CharSequence paragraph = Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ");

    /************
     * Functions
     ************/

    public DiseaseSummaryFragment() {
        super();
    }

    public static DiseaseSummaryFragment newInstance(String diseaseName) {
        DiseaseSummaryFragment fragment = new DiseaseSummaryFragment();
        Bundle args = new Bundle();
        args.putString("diseaseName", diseaseName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments
        diseaseName = getArguments().getString("diseaseName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_disease_summary, container, false);
        initInstances(rootView);
        loadDiseaseList();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvTitleDiseaseName = (TextView) rootView.findViewById(R.id.tvTitleDiseaseName);
        tvDiseaseName = (TextView) rootView.findViewById(R.id.tvDiseaseName);
        tvTitleDetail = (TextView) rootView.findViewById(R.id.tvTitleDetail);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        tvTitleTreatment = (TextView) rootView.findViewById(R.id.tvTitleTreatment);
        tvTreatment = (TextView) rootView.findViewById(R.id.tvTreatment);
        tvTitleShouldEat = (TextView) rootView.findViewById(R.id.tvTitleShouldEat);
        tvShouldEat = (TextView) rootView.findViewById(R.id.tvShouldEat);
        tvTitleShouldNotEat = (TextView) rootView.findViewById(R.id.tvTitleShouldNotEat);
        tvShouldNotEat = (TextView) rootView.findViewById(R.id.tvShouldNotEat);
        tvTitleRecommend = (TextView) rootView.findViewById(R.id.tvTitleRecommend);
        tvRecommend = (TextView) rootView.findViewById(R.id.tvRecommendation);
        hideView();
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

    private void showView() {
        tvTitleDiseaseName.setVisibility(Switch.VISIBLE);
        tvTitleDetail.setVisibility(Switch.VISIBLE);
        tvTitleTreatment.setVisibility(Switch.VISIBLE);
        tvTitleShouldEat.setVisibility(Switch.VISIBLE);
        tvTitleShouldNotEat.setVisibility(Switch.VISIBLE);
        tvTitleRecommend.setVisibility(Switch.VISIBLE);
    }

    private void hideView() {
        tvTitleDiseaseName.setVisibility(Switch.GONE);
        tvTitleDetail.setVisibility(Switch.GONE);
        tvTitleTreatment.setVisibility(Switch.GONE);
        tvTitleShouldEat.setVisibility(Switch.GONE);
        tvTitleShouldNotEat.setVisibility(Switch.GONE);
        tvTitleRecommend.setVisibility(Switch.GONE);
    }

    private void loadDiseaseList() {
        Call<DiseaseItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseList(
                "diseases",
                diseaseName
        );
        call.enqueue(loadDiseaseList);
    }

    private void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_LONG)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    Callback<DiseaseItemCollectionDao> loadDiseaseList = new Callback<DiseaseItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseItemCollectionDao> call, Response<DiseaseItemCollectionDao> response) {
            if (response.isSuccessful()) {
                DiseaseItemCollectionDao dao = response.body();
                if (dao.getData().isEmpty()) {
                    showToast("ไม่พบข้อมูลโรค");
                } else {
                    showView();
                    tvDiseaseName.setText(dao.getData().get(0).getDiseaseName());
                    tvDetail.setText((paragraph + dao.getData().get(0).getDetail()));
                    tvTreatment.setText((paragraph + dao.getData().get(0).getTreatment()));
                    tvShouldEat.setText((dao.getData().get(0).getShouldEat().isEmpty()) ? paragraph + "ไม่มี" : dao.getData().get(0).getShouldEat());
                    tvShouldNotEat.setText((dao.getData().get(0).getShouldNotEat().isEmpty()) ? paragraph + "ไม่มี" : dao.getData().get(0).getShouldNotEat());
                    tvRecommend.setText((paragraph + ((dao.getData().get(0).getRecommend().isEmpty() ? "ไม่มี" : dao.getData().get(0).getRecommend()))));
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<DiseaseItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    /**************
     * Inner Class
     **************/

}
