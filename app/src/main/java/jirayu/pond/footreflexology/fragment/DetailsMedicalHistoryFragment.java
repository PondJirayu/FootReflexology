package jirayu.pond.footreflexology.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;

import jirayu.pond.footreflexology.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class DetailsMedicalHistoryFragment extends Fragment {

    /************
     * Variables
     ************/

    ViewPager viewPager;
    TabLayout tabLayout;
    private String diseaseName;
    private int medicalHistoryId;

    /************
     * Functions
     ************/

    public DetailsMedicalHistoryFragment() {
        super();
    }

    public static DetailsMedicalHistoryFragment newInstance(String diseaseName, int medicalHistoryId) {
        DetailsMedicalHistoryFragment fragment = new DetailsMedicalHistoryFragment();
        Bundle args = new Bundle();
        args.putString("diseaseName", diseaseName);
        args.putInt("medicalHistoryId", medicalHistoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read from Arguments
        diseaseName = getArguments().getString("diseaseName");
        medicalHistoryId = getArguments().getInt("medicalHistoryId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details_medical_history, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        // Handle ViewPager
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return DiseaseSummaryFragment.newInstance(diseaseName);
                    case 1:
                        return DatesChartSummaryFragment.newInstance(medicalHistoryId);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "รายละเอียดของโรค";
                    case 1:
                        return "สถิติการรักษา";
                    default:
                        return "";
                }
            }
        });

        // ViewPagerIndicator
        tabLayout.setupWithViewPager(viewPager);
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


    /**************
     * Inner Class
     **************/

}
