package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lp700 on 20/4/2560.
 */

public class BehaviorOfDiseaseWithOrganItemDao {

    @SerializedName("behavior_id")
    private int behaviorId;
    @SerializedName("list")
    private String list;

    public int getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(int behaviorId) {
        this.behaviorId = behaviorId;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
