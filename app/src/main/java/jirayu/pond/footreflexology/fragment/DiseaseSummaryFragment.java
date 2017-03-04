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
public class DiseaseSummaryFragment extends Fragment implements TextToSpeech.OnInitListener, View.OnClickListener {

    /************
     * Variables
     ************/

    FloatingActionButton btnFloatingAction;
    TextView tvDiseaseName, tvDetail, tvTreatment, tvShouldEat, tvShouldNotEat, tvRecommend,
            tvTitleDiseaseName, tvTitleDetail, tvTitleTreatment, tvTitleShouldEat, tvTitleShouldNotEat, tvTitleRecommend;

    private TextToSpeech textToSpeech;
    private boolean isFirstTime = true;
    private String diseaseName;
    private DiseaseItemCollectionDao dao;
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
        return rootView;
    }

    @Override
    public void onDestroyView() {
        textToSpeech.shutdown();
        super.onDestroyView();
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnFloatingAction = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingAction);
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
        tvRecommend = (TextView) rootView.findViewById(R.id.tvRecommend);
        hideView();

        textToSpeech = new TextToSpeech(getContext(), this);    // TextToSpeech

        // Handle Click
        btnFloatingAction.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        loadDiseaseList();
        super.onStart();
    }

    @Override
    public void onStop() {
        textToSpeech.shutdown();
        btnFloatingAction.setVisibility(Switch.GONE);
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
        btnFloatingAction.setVisibility(Switch.GONE);
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

    /*
     * Initialize TextToSpeech
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(new Locale("th"));
            textToSpeech.setSpeechRate(0);  // Speech rate. 1 is the normal speech.
        }
    }

    private void loadAnimation() {
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),
                R.anim.fab_open);
        btnFloatingAction.startAnimation(anim);
        btnFloatingAction.setVisibility(Switch.VISIBLE);
    }

    private void speak(CharSequence message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "");
        } else {
            textToSpeech.speak(message.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private CharSequence getDao() {
        return "โรค" + dao.getData().get(0).getDiseaseName() +
                "รายละเอียดและสาเหตุ" + dao.getData().get(0).getDetail() +
                "การรักษา" + dao.getData().get(0).getTreatment() +
                "อาหารที่ควรรับประทาน" + dao.getData().get(0).getShouldEat() +
                "อาหารที่ควรหลีกเลี่ยง" + dao.getData().get(0).getShouldNotEat() +
                "ตำแนะนำ" + dao.getData().get(0).getRecommend();
    }

    private void setDao(DiseaseItemCollectionDao dao) {
        this.dao = dao;
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
                    setDao(response.body()); // dao ไว้ใช้สำหรับคำสั่งเสียง [TTS]
                    showView();
                    tvDiseaseName.setText(dao.getData().get(0).getDiseaseName());
                    tvDetail.setText((paragraph + dao.getData().get(0).getDetail()));
                    tvTreatment.setText((paragraph + dao.getData().get(0).getTreatment()));
                    tvShouldEat.setText((dao.getData().get(0).getShouldEat().isEmpty()) ? paragraph + "ไม่มี" : dao.getData().get(0).getShouldEat());
                    tvShouldNotEat.setText((dao.getData().get(0).getShouldNotEat().isEmpty()) ? paragraph + "ไม่มี" : dao.getData().get(0).getShouldNotEat());
                    tvRecommend.setText((paragraph + ((dao.getData().get(0).getRecommend().isEmpty() ? "ไม่มี" : dao.getData().get(0).getRecommend()))));
                    loadAnimation();    // FAB Animation
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

    /*
     * Handle Click
     */
    @Override
    public void onClick(View v) {
        if (v == btnFloatingAction) {
            if (isFirstTime) {
                showToast("กำลังประมวลผลคำสั่งอ่านข้อความด้วยเสียง");
                speak(getDao());
                btnFloatingAction.setImageResource(R.drawable.ic_stop_white_36dp);
                isFirstTime = false;
            }
            if (textToSpeech.isSpeaking()) {
                textToSpeech.stop(); // Stop talking
                btnFloatingAction.setImageResource(R.drawable.ic_volume_up_white_36dp);
            } else {
                speak(getDao());
                btnFloatingAction.setImageResource(R.drawable.ic_stop_white_36dp);
            }
        }
    }

    /**************
     * Inner Class
     **************/

}
