package jirayu.pond.footreflexology.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.activity.ShowDetailsActivity;
import jirayu.pond.footreflexology.dao.DiseaseWithOrganItemCollectionDao;
import jirayu.pond.footreflexology.manager.DataMemberManager;
import jirayu.pond.footreflexology.manager.HttpManager;
import jirayu.pond.footreflexology.manager.StringsManager;
import jirayu.pond.footreflexology.util.ButtonAlertPositionUtils;
import jirayu.pond.footreflexology.util.ButtonAlertUtils;
import jirayu.pond.footreflexology.util.InfoDialogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RightFootFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    /************
     * Variables
     ***********/

    Spinner spinnerFoot;
    ArrayAdapter<CharSequence> adapter;
    FrameLayout layoutAlert;
    ImageButton imgBtnInfo;
    StringsManager stringsManager;

    private int lastPosition = -1;
    private final int SIZE = 38 + 14;
    private int position[][] = ButtonAlertPositionUtils.getAlertViewRightFootPosition();
    private ButtonAlertUtils buttonAlertUtils[] = new ButtonAlertUtils[SIZE];
    private ArrayList<String> organName = new ArrayList<>();

    /************
     * Functions
     ************/

    public RightFootFragment() {
        super();
    }

    public static RightFootFragment newInstance() {
        RightFootFragment fragment = new RightFootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rightfoot, container, false);
        initInstances(rootView);
        initOrganName();
        initBtnAlert();
        loadDiseaseWithOrgan();
        return rootView;
    }

    private void loadDiseaseWithOrgan() {
        Call<DiseaseWithOrganItemCollectionDao> call = HttpManager.getInstance().getService().loadDiseaseWithOrgan(
                "diseasewithorgan",
                DataMemberManager.getInstance().getMemberItemDao().getId()
        );
        call.enqueue(loadDiseaseWithOrgan);
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinnerFoot = (Spinner) rootView.findViewById(R.id.spinnerFoot);
        layoutAlert = (FrameLayout) rootView.findViewById(R.id.layoutAlert);
        imgBtnInfo = (ImageButton) rootView.findViewById(R.id.imgBtnInfo);

        createAdapter();
        spinnerFoot.setOnItemSelectedListener(this); // Handle Click Spinner

        // Handle Click Button
        imgBtnInfo.setOnClickListener(this);
    }

    private void initOrganName() {
        organName.add("โพรงอากาศและกระดูกหน้าผาก");
        organName.add("สมอง");
        organName.add("ตา");
        organName.add("หู");
        organName.add("กล้ามเนื้อทราปิเซียส");
        organName.add("ฟัน");
        organName.add("ไหล่ขวา");
        organName.add("ถุงน้ำดี");
        organName.add("ตับ");
        organName.add("ต่อมหมวกไต");
        organName.add("ลำไส้ใหญ่ขาขึ้น");
        organName.add("ลำไส้ใหญ่ซีคัม");
        organName.add("เข่าขวา");
        organName.add("ไส้ติ่ง");
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
        // Create btnAlert
        for (int i = 0; i < SIZE; i++) {
            buttonAlertUtils[i] = new ButtonAlertUtils(getContext(), 4, 38, 38, position[i][0], position[i][1]); // Create
        }

        for (int i = 0; i < SIZE; i++) {
            // Add OrganName to btnAlert
            if (i >= 38 && i <= 45) {
                for (int j = 38; j <= 45; j++)
                    buttonAlertUtils[j].setOrganName(organName.get(0));
            } else if (i == 46) { // 3
                buttonAlertUtils[i].setOrganName(organName.get(2));
            } else if (i == 47) { // 4
                buttonAlertUtils[i].setOrganName(organName.get(3));
            } else if (i >= 48 && i <= 51) { // 6
                for (int j = 48; j <= 51; j++)
                    buttonAlertUtils[j].setOrganName(organName.get(5));
            } else {
                buttonAlertUtils[i].setOrganName(organName.get(i));
            }

            layoutAlert.addView(buttonAlertUtils[i].getBtnAlert(), buttonAlertUtils[i].getParams()); // Add to Layout
            buttonAlertUtils[i].hideAlertView(); // Hide
        }

        // Handle Click btnAlert
//        for (int i = 0; i < SIZE; i++) {
//            buttonAlertUtils[i].getBtnAlert().setOnClickListener(this);
//        }
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
        adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.right_foot_names,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFoot.setAdapter(adapter); // Spinner + Adapter
    }

    private void showBtnAlert(int position) {
        if (lastPosition != -1) buttonAlertUtils[lastPosition].hideAlertView(); // ซ่อน AlertView
        // ซ่อน AlertView ตัวซ้ำ
        switch (lastPosition) {
            case 0:
                hideAlertViewNumberOneExtend();
                break;
            case 2:
                buttonAlertUtils[37 + 9].hideAlertView();
                break;
            case 3:
                buttonAlertUtils[37 + 10].hideAlertView();
                break;
            case 5:
                hideAlertViewNumberSixExtend();
                break;
        }
        for (int i = 0; i < SIZE; i++) {
            if (i == position) {
                buttonAlertUtils[i].showAlertView();
                switch (position) {
                    case 0:
                        showAlertViewNumberOneExtend();
                        break;
                    case 2:
                        buttonAlertUtils[37 + 9].showAlertView();
                        break;
                    case 3:
                        buttonAlertUtils[37 + 10].showAlertView();
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
            buttonAlertUtils[37 + i].showAlertView();
        }
    }

    private void hideAlertViewNumberOneExtend() {
        for (int i = 1; i <= 8; i++) {
            buttonAlertUtils[37 + i].hideAlertView();
        }
    }

    private void showAlertViewNumberSixExtend() {
        for (int i = 11; i <= 14; i++) {
            buttonAlertUtils[37 + i].showAlertView();
        }
    }

    private void hideAlertViewNumberSixExtend() {
        for (int i = 11; i <= 14; i++) {
            buttonAlertUtils[37 + i].hideAlertView();
        }
    }

    private void startActivity() {
        Intent intent = new Intent(getContext(), ShowDetailsActivity.class);
        intent.putExtra("result", stringsManager.getWordNoneNumberAndNoneWhiteSpace());
        startActivity(intent);
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

    Callback<DiseaseWithOrganItemCollectionDao> loadDiseaseWithOrgan = new Callback<DiseaseWithOrganItemCollectionDao>() {
        @Override
        public void onResponse(Call<DiseaseWithOrganItemCollectionDao> call, Response<DiseaseWithOrganItemCollectionDao> response) {
            if (response.isSuccessful()) {
                DiseaseWithOrganItemCollectionDao dao = response.body();
                if (dao.getDiseaseWithOrganItemDaos().isEmpty()) {
                    showToast("ไม่พบข้อมูลผู้ป่วย");
                } else {
                    initBehaviors(dao);
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<DiseaseWithOrganItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };

    private void initBehaviors(DiseaseWithOrganItemCollectionDao dao) {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < dao.getDiseaseWithOrganItemDaos().size(); j++)
            {
                for (int k = 0; k < dao.getDiseaseWithOrganItemDaos().get(j).size(); k++)
                {
                    if (buttonAlertUtils[i].getOrganName().equals(dao.getDiseaseWithOrganItemDaos().get(j).get(k).getOrganName()))
                    {
                        buttonAlertUtils[i].setBackgroundView(dao.getBehaviorOfDiseaseWithOrganItemDaos().get(j).getBehaviorId());
                        buttonAlertUtils[i].showAlertView();
                    }
                }
            }
        }
    }

    /*
     * Handle Click Spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stringsManager = new StringsManager();
        stringsManager.setWord(parent.getItemAtPosition(position).toString());
        showBtnAlert(position);
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
//            case R.id.btnAlert:
//                startActivity();
//                break;
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
