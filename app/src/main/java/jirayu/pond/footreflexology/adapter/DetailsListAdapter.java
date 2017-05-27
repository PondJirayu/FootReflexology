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
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemDao;
import jirayu.pond.footreflexology.view.DetailsListItem;

/**
 * Created by lp700 on 16/12/2559.
 */

// TODO: Capture หน้านี้ด้วย ใส่ folder (4.ขั้นตอนเมื่อมีการคลิกที่วิว....)

public class DetailsListAdapter extends BaseAdapter {

    private DetailItemCollectionDao detailItemCollectionDao;
    private MedicalHistoryItemCollectionDao medicalHistoryItemCollectionDao;
    private int lastPosition = -1;
    private CharSequence paragraph = Html.fromHtml("&nbsp; &nbsp; &nbsp; &nbsp; ");

    public void setDetailItemCollectionDao(DetailItemCollectionDao detailItemCollectionDao) {
        this.detailItemCollectionDao = detailItemCollectionDao;
    }

    public void setMedicalHistoryItemCollectionDao(MedicalHistoryItemCollectionDao medicalHistoryItemCollectionDao) {
        this.medicalHistoryItemCollectionDao = medicalHistoryItemCollectionDao;
    }

    @Override
    public int getCount() {
        if (detailItemCollectionDao == null) {
            return 0;
        }
        if (detailItemCollectionDao.getData() == null) {
            return 0;
        }
        return detailItemCollectionDao.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return detailItemCollectionDao.getData().get(position);
    }

    private MedicalHistoryItemDao getItemMedicalHistory(int position) {
        return medicalHistoryItemCollectionDao.getData().get(position);
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
            // ซ่อน tvBehavior ทุกครั้งที่มีการ Reuse
            item.setVisibilityTextViewBehavior();
        } else {
            // Create
            item = new DetailsListItem(parent.getContext());
        }

        DetailItemDao detailItemDao = (DetailItemDao) getItem(position);

        // Set ค่าให้ View of CustomViewGroup
        item.setPageNumber(position);
        item.setDiseaseName(detailItemDao.getDiseaseName());
        item.setDetail(paragraph + detailItemDao.getDetail());
        item.setTreatment(paragraph + detailItemDao.getTreatMent());
        item.setShouldEat((detailItemDao.getShouldEat().isEmpty() ? paragraph + "ไม่มี" : detailItemDao.getShouldEat()));
        item.setShouldNotEat((detailItemDao.getShouldNotEat().isEmpty() ? paragraph + "ไม่มี" : detailItemDao.getShouldNotEat()));
        item.setRecommendation(paragraph + ((detailItemDao.getRecommend().isEmpty() ? "ไม่มี" : detailItemDao.getRecommend())));
        if (medicalHistoryItemCollectionDao != null && medicalHistoryItemCollectionDao.getData() != null) {
            // ค้นหาว่าใน list มีโรคไหนบ้างที่มีประวัติการรักษา ถ้าพบว่าโรคไหนมีประวัติการรักษาให้แสดงอาการผู้ป่วยที่เป็นโรคนั้นออกมา
            for (int i = 0; i < medicalHistoryItemCollectionDao.getData().size(); i++) {
                if (detailItemDao.getDiseaseName().equals(getItemMedicalHistory(i).getDiseaseName())) {
                    item.setBehavior(getItemMedicalHistory(i).getList());
                    break;
                }
            }
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
