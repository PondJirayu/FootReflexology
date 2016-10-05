package jirayu.pond.footreflexology.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jirayu.pond.footreflexology.fragment.LeftFootFragment;
import jirayu.pond.footreflexology.fragment.RightFootFragment;

/**
 * Created by lp700 on 5/10/2559.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_NUMBER = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new RightFootFragment();
        } else if (position == 1) {
            return new LeftFootFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }
}
