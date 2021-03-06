package jirayu.pond.footreflexology.manager;

import jirayu.pond.footreflexology.dao.BehaviorItemCollectionDao;

/**
 * Created by lp700 on 18/1/2560.
 */

public class BehaviorManager {

    private BehaviorItemCollectionDao dao;
    private String behavior;

    public BehaviorManager(BehaviorItemCollectionDao dao) {
        setDao(dao);
    }

    public BehaviorItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(BehaviorItemCollectionDao dao) {
        this.dao = dao;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public int getBehaviorId() {
        if (behavior != null) {
            for (int i = 0 ; i < dao.getData().size() ; i++) {
                if (dao.getData().get(i).getList().equals(behavior))
                    return dao.getData().get(i).getId();
            }
        }
        return -1;
    }
}
