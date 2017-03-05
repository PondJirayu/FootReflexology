package jirayu.pond.footreflexology.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import jirayu.pond.footreflexology.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DatesChartSummaryFragment extends Fragment {

    /************
     * Variables
     ************/

    RelativeLayout graphContainer;

    /************
     * Functions
     ************/

    public DatesChartSummaryFragment() {
        super();
    }

    public static DatesChartSummaryFragment newInstance() {
        DatesChartSummaryFragment fragment = new DatesChartSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dates_chart_summary, container, false);
        initInstances(rootView);
        initData();
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        graphContainer = (RelativeLayout) rootView.findViewById(R.id.graphContainer);
    }

    private void initData() {
        String[] months = {
                "JAB", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
        };

        int[] index = {1, 2, 3, 4, 5};

        int[] incomeA = {2, 0, 1, 2, 1};

        XYSeries seriesA = new XYSeries("Google");

        int length = index.length;
        for (int i = 0; i < length; i++) {
            seriesA.add(index[i], incomeA[i]);
        }

        XYSeriesRenderer rendererA = new XYSeriesRenderer();
        rendererA.setPointStyle(PointStyle.CIRCLE);
        rendererA.setColor(Color.BLUE);
        rendererA.setLineWidth(2);

        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(seriesA);

        XYMultipleSeriesRenderer multipleSeriesRenderer
                = new XYMultipleSeriesRenderer();

        for (int i = 0; i < length; i++) {
            multipleSeriesRenderer.addXTextLabel(i + 1, months[i]);
        }

        multipleSeriesRenderer.setChartTitle("ตัวอย่างกราฟเส้น (Line Chart)");
        multipleSeriesRenderer.setYTitle("ยอดขายรวม(สตางค์)");
        multipleSeriesRenderer.setXTitle("ปี พ.ศ. 2600");
        multipleSeriesRenderer.setXLabels(0);
        multipleSeriesRenderer.setBackgroundColor(Color.WHITE);
        multipleSeriesRenderer.setApplyBackgroundColor(true);
        multipleSeriesRenderer.setMarginsColor(Color.WHITE);
        multipleSeriesRenderer.setLabelsColor(Color.BLACK);
        multipleSeriesRenderer.setAxesColor(Color.GRAY);
        multipleSeriesRenderer.setYLabelsColor(0, Color.BLACK);
        multipleSeriesRenderer.setXLabelsColor(Color.BLACK);
        multipleSeriesRenderer.setZoomButtonsVisible(true);

        multipleSeriesRenderer.addSeriesRenderer(rendererA);

        drawChart(dataSet, multipleSeriesRenderer);
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

    private void drawChart(XYMultipleSeriesDataset dataSet, XYMultipleSeriesRenderer multipleSeriesRenderer) {
        GraphicalView graphView = ChartFactory.getLineChartView(getContext(), dataSet, multipleSeriesRenderer);
        graphContainer.addView(graphView);
    }

    /****************
     * Listener Zone
     ****************/

    /**************
     * Inner Class
     **************/

}
