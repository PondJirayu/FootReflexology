package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lp700 on 15/1/2560.
 */

public class BehaviorItemCollectionDao {

    @SerializedName("success")
    private int success;
    @SerializedName("data")
    private List<BehaviorItemDao> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<BehaviorItemDao> getData() {
        return data;
    }

    public void setData(List<BehaviorItemDao> data) {
        this.data = data;
    }
}
