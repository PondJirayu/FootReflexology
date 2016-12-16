package jirayu.pond.footreflexology.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import jirayu.pond.footreflexology.view.DetailsListItem;

/**
 * Created by lp700 on 16/12/2559.
 */

public class DetailsListAdapter extends BaseAdapter {

    private int count;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new DetailsListItem(parent.getContext());
    }

    public void setCount(int count){
       this.count = count;
    }

}
