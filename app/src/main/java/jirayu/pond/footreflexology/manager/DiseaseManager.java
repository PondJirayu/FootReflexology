package jirayu.pond.footreflexology.manager;

import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemDao;

/**
 * Created by lp700 on 18/1/2560.
 */

public class DiseaseManager {

    private DiseaseItemCollectionDao dao;
    private String disease;

    public DiseaseManager(DiseaseItemCollectionDao dao) {
        setDao(dao);
    }

    public DiseaseItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(DiseaseItemCollectionDao dao) {
        this.dao = dao;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getDiseaseId() {
        for (int i = 0 ; i < dao.getData().size() ; i++) {
            if (dao.getData().get(i).getDiseaseName().equals(disease))
                return dao.getData().get(i).getId();
        }
        return -1;
    }
}
