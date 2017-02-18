package jirayu.pond.footreflexology.fragment;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import jirayu.pond.footreflexology.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DiseaseSummaryFragment extends Fragment implements TextToSpeech.OnInitListener, View.OnClickListener {

    /************
     * Variables
     ************/

    FloatingActionButton btnFloatingAction;

    TextView tvDiseaseName, tvDetail, tvTreatment, tvShouldEat, tvShouldNotEat, tvRecommend;

    private TextToSpeech textToSpeech;

    boolean isFirstTime = true;

    /************
     * Functions
     ************/

    public DiseaseSummaryFragment() {
        super();
    }

    public static DiseaseSummaryFragment newInstance() {
        DiseaseSummaryFragment fragment = new DiseaseSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnFloatingAction = (FloatingActionButton) rootView.findViewById(R.id.btnFloatingAction);
        tvDiseaseName = (TextView) rootView.findViewById(R.id.tvDiseaseName);
        tvDetail = (TextView) rootView.findViewById(R.id.tvDetail);
        tvTreatment = (TextView) rootView.findViewById(R.id.tvTreatment);
        tvShouldEat = (TextView) rootView.findViewById(R.id.tvShouldEat);
        tvShouldNotEat = (TextView) rootView.findViewById(R.id.tvShouldNotEat);
        tvRecommend = (TextView) rootView.findViewById(R.id.tvRecommend);

        // TextToSpeech
        textToSpeech = new TextToSpeech(getContext(), this);

        // Handle Click
        btnFloatingAction.setOnClickListener(this);
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

    private void speak(CharSequence message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "");
        } else {
            textToSpeech.speak(message.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_LONG)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    /*
     * Handle Click
     */
    @Override
    public void onClick(View v) {
        if (v == btnFloatingAction) {
            if (isFirstTime) {
                showToast("กำลังประมวลผลคำสั่งอ่านข้อความด้วยเสียง");
                speak("โรคอ้วน รายละเอียดและสาเหตุ การรักษา อาหารที่ควรรับประทาน อาหารที่ควรหลีกเลี่ยง คำแนะนำ");
                btnFloatingAction.setImageResource(R.drawable.ic_stop_white_36dp);
                isFirstTime = false;
            }
            if (textToSpeech.isSpeaking()) {
                textToSpeech.stop(); // Stop talking
                btnFloatingAction.setImageResource(R.drawable.ic_volume_up_white_36dp);
            } else {
                speak("โรคอ้วน รายละเอียดและสาเหตุ การรักษา อาหารที่ควรรับประทาน อาหารที่ควรหลีกเลี่ยง คำแนะนำ");
                btnFloatingAction.setImageResource(R.drawable.ic_stop_white_36dp);
            }
        }
    }

    /**************
     * Inner Class
     **************/

}
