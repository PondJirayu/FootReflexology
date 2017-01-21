package jirayu.pond.footreflexology.manager;

import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;

/**
 * Created by lp700 on 21/1/2560.
 */

public class MedicalHistoryManager {

    private MedicalHistoryItemCollectionDao dao;
    private String disease;

    public MedicalHistoryManager(MedicalHistoryItemCollectionDao dao) {
        this.dao = dao;
    }

    public MedicalHistoryItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(MedicalHistoryItemCollectionDao dao) {
        this.dao = dao;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getMedicalHistoryId() {
        if (disease != null) {
            for (int i = 0; i < dao.getData().size(); i++) {
                if (dao.getData().get(i).getDiseaseName().equals(disease))
                    return dao.getData().get(i).getId();
            }
        }
        return -1;
    }
}
