package jirayu.pond.footreflexology.manager;

import jirayu.pond.footreflexology.dao.BehaviorCollectionDao;
import jirayu.pond.footreflexology.dao.BehaviorDao;

/**
 * Created by lp700 on 18/1/2560.
 */

public class BehaviorManager {

    private BehaviorCollectionDao dao;
    private String behavior;

    public BehaviorManager(BehaviorCollectionDao dao) {
        setDao(dao);
    }

    public BehaviorCollectionDao getDao() {
        return dao;
    }

    public void setDao(BehaviorCollectionDao dao) {
        this.dao = dao;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public int getBehaviorId() {
        for (int i = 0 ; i < dao.getData().size() ; i++) {
            if (dao.getData().get(i).getList().equals(behavior))
                return dao.getData().get(i).getId();
        }
        return -1;
    }
}
