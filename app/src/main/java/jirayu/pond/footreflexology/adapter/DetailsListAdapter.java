package jirayu.pond.footreflexology.adapter;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.DetailItemDao;
import jirayu.pond.footreflexology.view.DetailsListItem;

/**
 * Created by lp700 on 16/12/2559.
 */

public class DetailsListAdapter extends BaseAdapter {

    private DetailItemCollectionDao dao;
    private int lastPosition = -1;
    private CharSequence paragraph = Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ");

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

        // Set ค่าให้ View of CustomViewGroup
        item.setPageNumber(position);
        item.setDiseaseName(dao.getDiseaseName());
        item.setDetail(paragraph + dao.getDetail());
        item.setTreatment(paragraph + dao.getTreatMent());
        item.setShouldEat((dao.getShouldEat().isEmpty() ? "ไม่มี" : dao.getShouldEat()));
        item.setShouldNotEat((dao.getShouldNotEat().isEmpty() ? "ไม่มี" : dao.getShouldNotEat()));
        item.setRecommend(paragraph + ((dao.getRecommend().isEmpty() ? "ไม่มี" : dao.getRecommend())));
        // Set UnderLine Colour
        item.setUnderLine(position);

        // Start Animation
        if (position > lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPosition = position;
        }

        return item;
    }

}
