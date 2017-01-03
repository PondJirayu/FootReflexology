package jirayu.pond.footreflexology.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemDao;
import jirayu.pond.footreflexology.view.DetailsListItem;

/**
 * Created by lp700 on 26/12/2559.
 */

public class DiseaseListAdapter extends BaseAdapter {

    DiseaseItemCollectionDao dao;

    public void setDao(DiseaseItemCollectionDao dao) {
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
        DetailsListItem item;

        if (convertView != null) {
            // Reuse
            item = (DetailsListItem) convertView;
        } else {
            // Create
            item = new DetailsListItem(parent.getContext());
        }

        DiseaseItemDao dao = (DiseaseItemDao) getItem(position);
        // set ค่าให้ view ของ customViewGroup
        item.setDiseaseName(dao.getDiseaseName());
        item.setDetail(Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ") + dao.getDetail());
        item.setTreatment(Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ") + dao.getTreatment());
        // ตรวจสอบว่ามีคำแนะนำหรือไม่
        if (dao.getRecommend().isEmpty()) {
            item.setRecommend("");
        } else {
            item.setRecommend(Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ") + dao.getRecommend());
        }

        return item;
    }
}