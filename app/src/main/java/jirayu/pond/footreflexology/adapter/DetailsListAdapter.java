package jirayu.pond.footreflexology.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.DetailItemDao;
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
        DetailItemDao dao = (DetailItemDao) getItem(position);
        // set ค่าให้ view ของ customViewGroup
        item.setDiseaseName(dao.getDiseaseName());
        item.setDetail(Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ") + dao.getDetail());
        item.setTreatment(Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ") + dao.getTreatMent());
        // ตรวจสอบว่ามีคำแนะนำหรือไม่
        if (dao.getRecommend().isEmpty()) {
            item.setRecommend("ไม่มีคำแนะนำ");
        } else {
            item.setRecommend(Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ") + dao.getRecommend());
        }
        return item;
    }

}
