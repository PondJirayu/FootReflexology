package jirayu.pond.footreflexology.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemDao;
import jirayu.pond.footreflexology.view.MedicalHistoryListItem;

/**
 * Created by lp700 on 25/12/2559.
 */

public class MedicalHistoryAdapter extends BaseAdapter {

    MedicalHistoryItemCollectionDao dao;

    public void setDao(MedicalHistoryItemCollectionDao dao) {
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
        return dao.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MedicalHistoryListItem item;

        if (convertView != null) {
            // Reuse
            item = (MedicalHistoryListItem) convertView;
        } else {
            // Create
            item = new MedicalHistoryListItem(parent.getContext());
        }
        MedicalHistoryItemDao dao = (MedicalHistoryItemDao) getItem(position);
        // set ค่าให้ view ของ customViewGroup
        item.setDiseaseName(dao.getDiseaseName());
        item.setBehavior(dao.getList());
        return item;
    }
}
