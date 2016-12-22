package jirayu.pond.footreflexology.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.view.DetailsListItem;

/**
 * Created by lp700 on 16/12/2559.
 */

public class DetailsListAdapter extends BaseAdapter {

    DetailItemCollectionDao dao;

    public void setDao(DetailItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        return dao.getData().size() + 1;
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
        DetailsListItem item = new DetailsListItem(parent.getContext());
        item.setDiseaseName(dao.getData().get(0).getDiseaseName());
        item.setDetail(dao.getData().get(0).getDetail());
        item.setTreatment(dao.getData().get(0).getTreatMent());
        item.setRecommend(dao.getData().get(0).getRecommend());
        return item;
    }

}
