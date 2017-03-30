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
import jirayu.pond.footreflexology.view.DetailsListItem;

/**
 * Created by lp700 on 16/12/2559.
 */

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

        DetailItemDao detailItemDao = (DetailItemDao) getItem(position);

        // Set ค่าให้ View of CustomViewGroup
        item.setPageNumber(position);
        item.setDiseaseName(detailItemDao.getDiseaseName());
        item.setDetail(paragraph + detailItemDao.getDetail());
        item.setTreatment(paragraph + detailItemDao.getTreatMent());
        item.setShouldEat((detailItemDao.getShouldEat().isEmpty() ? paragraph + "ไม่มี" : detailItemDao.getShouldEat()));
        item.setShouldNotEat((detailItemDao.getShouldNotEat().isEmpty() ? paragraph + "ไม่มี" : detailItemDao.getShouldNotEat()));
        item.setRecommendation(paragraph + ((detailItemDao.getRecommend().isEmpty() ? "ไม่มี" : detailItemDao.getRecommend())));
        // TODO: ตรงนี้ทำงี้นะ get ชื่อโรคจาก 2 detailItemCollectionDao (DetailItemCollectionDao, MedicalHistoryItemCollectionDao)ออกมาเช็คเงื่อนไขเท่ากัน ถ้าเป็นจริง ก็เรียก setBehavior โยนอาการเข้าไปแค่นี้แหละ
        if (medicalHistoryItemCollectionDao != null && medicalHistoryItemCollectionDao.getData() != null) {
            for (int i = 0; i < medicalHistoryItemCollectionDao.getData().size(); i++) {
                if (detailItemDao.getDiseaseName().equals(medicalHistoryItemCollectionDao.getData().get(i).getDiseaseName())) {
                    item.setBehavior(medicalHistoryItemCollectionDao.getData().get(i).getList());
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
