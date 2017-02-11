package jirayu.pond.footreflexology.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import jirayu.pond.footreflexology.R;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemDao;
import jirayu.pond.footreflexology.view.MedicalHistoryListItem;

/**
 * Created by lp700 on 25/12/2559.
 */

public class MedicalHistoryAdapter extends BaseAdapter {

    private MedicalHistoryItemCollectionDao dao;

    private int lastPosition = -1;

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
        item.setRank(position + 1);
        item.setDiseaseName(dao.getDiseaseName());
        item.setBehavior(dao.getList());
        item.setCreatedAt(dao.getCreatedAt());
        item.setUpdatedAt(dao.getUpdatedAt());

        // กำหนดสีหัวข้อตามอาการ
        if (dao.getList().equals("แย่ลง")) {
            item.setViewColourItemMedicalHistory(1);
        } else if (dao.getList().equals("ทรงตัว")) {
            item.setViewColourItemMedicalHistory(2);
        } else if (dao.getList().equals("ดีขึ้น")) {
            item.setViewColourItemMedicalHistory(3);
        } else {
            item.setViewColourItemMedicalHistory(4);
        }

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
