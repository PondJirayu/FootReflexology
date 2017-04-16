package jirayu.pond.footreflexology.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MedicalHistoryBehaviorItemCollectionDao;
import jirayu.pond.footreflexology.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DatesChartSummaryFragment extends Fragment {

    /************
     * Variables
     ************/

    GraphView graphView;
    private int medicalHistoryId;

    /************
     * Functions
     ************/

    public DatesChartSummaryFragment() {
        super();
    }

    public static DatesChartSummaryFragment newInstance(int medicalHistoryId) {
        DatesChartSummaryFragment fragment = new DatesChartSummaryFragment();
        Bundle args = new Bundle();
        args.putInt("medicalHistoryId", medicalHistoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments
        medicalHistoryId = getArguments().getInt("medicalHistoryId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dates_chart_summary, container, false);
        initInstances(rootView);
        loadMedicalHistoryBehavior();
        initGraph();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        graphView = (GraphView) rootView.findViewById(R.id.graphView);
    }

    private void initGraph() {
        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        // เพิ่มข้อมูลใส่กราฟตรงนี้
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 4),
                new DataPoint(d2, 2),
                new DataPoint(d3, 1)
        });
        series.setColor(Color.RED);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);
        series.setThickness(4);
        graphView.addSeries(series);

        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graphView.getGridLabelRenderer().setNumVerticalLabels(4); // กำหนดให้แกนแนวตั้ง(แกน Y)แสดง 4 แถว

        // set manual x bounds to have nice steps
        graphView.getViewport().setMinX(d1.getTime());
        graphView.getViewport().setMaxX(d3.getTime());
        graphView.getViewport().setXAxisBoundsManual(true);
        // set manual y bounds
        graphView.getViewport().setMinY(1); // แกนแนวตั้งค่าต่ำสุดคือ 1 ถ้าต่ำกว่า 1 เส้นจะหลุดกราฟ
        graphView.getViewport().setMaxY(4); // แกนแนวตั้งค่าสูงสุดคือ 4 ถ้ามากกว่า 4 เส้นจะหลุดกราฟ
        graphView.getViewport().setYAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graphView.getGridLabelRenderer().setHumanRounding(false);
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

    private void loadMedicalHistoryBehavior() {
        Call<MedicalHistoryBehaviorItemCollectionDao> call = HttpManager.getInstance().getService().loadMedicalHistoryBehavior(
                "medicalhistorybehavior",
                medicalHistoryId
        );
        call.enqueue(loadMedicalHistoryBehavior);
    }

    private void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT)
                .show();
    }

    /****************
     * Listener Zone
     ****************/

    Callback<MedicalHistoryBehaviorItemCollectionDao> loadMedicalHistoryBehavior = new Callback<MedicalHistoryBehaviorItemCollectionDao>() {
        @Override
        public void onResponse(Call<MedicalHistoryBehaviorItemCollectionDao> call, Response<MedicalHistoryBehaviorItemCollectionDao> response) {
            if (response.isSuccessful()) {
                MedicalHistoryBehaviorItemCollectionDao dao = response.body();
                if (dao.getData().isEmpty()) {
                    showToast("ไม่พบประวัติการรักษา");
                } else {
                    // TODO : เอาประวัติมา ลำดับที่ 0 1 2 ทำ สถิติ
                    showToast(String.valueOf(dao.getData().get(0).getUpdatedAt()));
                }
            } else {
                showToast("ขออภัยเซิร์ฟเวอร์ไม่ตอบสนอง โปรดลองเชื่อมต่ออีกครั้งในภายหลัง");
            }
        }

        @Override
        public void onFailure(Call<MedicalHistoryBehaviorItemCollectionDao> call, Throwable t) {
            showToast("กรุณาตรวจสอบการเชื่อมต่อเครือข่ายของคุณ");
        }
    };


    /**************
     * Inner Class
     **************/

}
