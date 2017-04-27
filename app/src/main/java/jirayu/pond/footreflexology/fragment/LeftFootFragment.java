package jirayu.pond.footreflexology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.manager.StringsManager;
import jirayu.pond.footreflexology.util.ButtonAlertPositionUtils;
import jirayu.pond.footreflexology.util.ButtonAlertUtils;
import jirayu.pond.footreflexology.util.InfoDialogUtils;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class LeftFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ************/

    Spinner spinnerFoot;
    ArrayAdapter<CharSequence> adapter;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    private int lastPosition = -1;
    private final int SIZE = 38 + 14;
    private int position[][] = ButtonAlertPositionUtils.getAlertViewLeftFootPosition();
    private ArrayList<ButtonAlertUtils> btnAlerts = new ArrayList<>();
    private ArrayList<String> organName = new ArrayList<>();

    /************
     * Functions
     ************/

    public LeftFootFragment() {
        super();
    }

    public static LeftFootFragment newInstance() {
        LeftFootFragment fragment = new LeftFootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leftfoot, container, false);
        initInstances(rootView);
        initOrganName();
        initBtnAlert();
        loadDiseaseWithOrgan();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerFoot = (Spinner) rootView.findViewById(R.id.spinnerFoot);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click
        imgBtnInfo.setOnClickListener(this);
    }

    private void initOrganName() {
        organName.add("โพรงอากาศและกระดูกหน้าผาก");
        organName.add("สมอง");
        organName.add("ตา");
        organName.add("หู");
        organName.add("กล้ามเนื้อทราปิเซียส");
        organName.add("ฟัน");
        organName.add("ไหล่ซ้าย");
        organName.add("ลำไส้ใหญ่ส่วนตรง");
        organName.add("ลำไส้ใหญ่ส่วนงอ");
        organName.add("ต่อมหมวกไต");
        organName.add("ลำไส้ใหญ่ขาลง");
        organName.add("หัวใจ");
        organName.add("เข่าซ้าย");
        organName.add("ม้าม");
        organName.add("ต่อมไพเนียล");
        organName.add("ต่อมใต้สมอง");
        organName.add("จมูก");
        organName.add("ขมับศีรษะ");
        organName.add("สมองใหญ่");
        organName.add("สมองเล็กและก้านสมอง");
        organName.add("ศีรษะต้นคอ");
        organName.add("ต่อมธัยรอยด์และต่อมพาราธัยรอยด์");
        organName.add("หลอดลมคอหอย");
        organName.add("ปอดและท่อหลอดลม");
        organName.add("ระบบประสาท");
        organName.add("กระเพาะอาหาร");
        organName.add("เส้นประสาทช่องท้อง");
        organName.add("กระบังลม");
        organName.add("ตับอ่อน");
        organName.add("ลำไส้เล็กส่วนตัว");
        organName.add("ลำไส้ใหญ่ส่วนขวาง");
        organName.add("ไต");
        organName.add("หลอดไต");
        organName.add("ลำไส้เล็กส่วนกลางและปลาย");
        organName.add("กระเพาะปัสสาวะ");
        organName.add("กระดูกเชิงกราน");
        organName.add("สะโพก");
        organName.add("เส้นประสาทกระเบนเหน็บ");
    }

    private void initBtnAlert() {
        for (int i = 0; i < SIZE; i++) {
            btnAlerts.add(new ButtonAlertUtils(getContext(), 4, 38, 38, position[i][0], position[i][1])); // New Object
        }

        // Add OrganName to btnAlert
        for (int i = 0; i < SIZE; i++) {
            if (i >= 38 && i <= 45) {
                for (int j = 38; j <= 45; j++){
                    btnAlerts.get(j).setOrganName(organName.get(0));
                }
            } else if (i == 46){ // 3
                btnAlerts.get(i).setOrganName(organName.get(2));
            } else if (i == 47){ // 4
                btnAlerts.get(i).setOrganName(organName.get(3));
            } else if (i >= 48 && i <= 51){ //6
                for (int j = 48; j <= 51; j++) {
                    btnAlerts.get(j).setOrganName(organName.get(5));
                }
            } else {
                btnAlerts.get(i).setOrganName(organName.get(i));
            }
            layoutAlert.addView(btnAlerts.get(i).getBtnAlert(), btnAlerts.get(i).getParams()); // Add to Layout
            btnAlerts.get(i).hideAlertView(); // Hide
        }
    }

    private void loadDiseaseWithOrgan() {
        // TODO : จะโหลดข้อมูลซ้ำเหรออออออออออ
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
     * Create Adapter of Spinner
     */
    private void createAdapter() {
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.left_foot_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFoot.setAdapter(adapter); // Spinner + Adapter
    }

    private void showAlertView(int position) {
        if (lastPosition != -1) btnAlerts.get(lastPosition).hideAlertView(); // ซ่อน AlertView
        // ซ่อน AlertView ตัวซ้ำ
        switch (lastPosition) {
            case 0:
                hideAlertViewNumberOneExtend();
                break;
            case 2:
                btnAlerts.get(37 + 9).hideAlertView();
                break;
            case 3:
                btnAlerts.get(37 + 10).hideAlertView();
                break;
            case 5:
                hideAlertViewNumberSixExtend();
                break;
        }
        for (int i = 0; i < SIZE; i++) {
            if (i == position) {
                btnAlerts.get(i).showAlertView();
                switch (position) {
                    case 0:
                        showAlertViewNumberOneExtend();
                        break;
                    case 2:
                        btnAlerts.get(37 + 9).showAlertView();
                        break;
                    case 3:
                        btnAlerts.get(37 + 10).showAlertView();
                        break;
                    case 5:
                        showAlertViewNumberSixExtend();
                        break;
                }
                lastPosition = position;
                break;
            }
        }
    }

    private void showAlertViewNumberOneExtend() {
        for (int i = 1; i <= 8; i++) {
            btnAlerts.get(37 + i).showAlertView();
        }
    }

    private void hideAlertViewNumberOneExtend() {
        for (int i = 1; i <= 8; i++) {
            btnAlerts.get(37 + i).hideAlertView();
        }
    }

    private void showAlertViewNumberSixExtend() {
        for (int i = 11; i <= 14; i++) {
            btnAlerts.get(37 + i).showAlertView();
        }
    }

    private void hideAlertViewNumberSixExtend() {
        for (int i = 11; i <= 14; i++) {
            btnAlerts.get(37 + i).hideAlertView();
        }
    }

    /****************
     * Listener Zone
     ****************/

    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showAlertView(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
     * Handle Click Button
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnInfo:
                InfoDialogUtils infoDialog = new InfoDialogUtils(getContext());
                infoDialog.showDialog();
                break;
        }
    }

    /**************
     * Inner Class
     **************/
}
