package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lp700 on 13/4/2560.
 */

public class MedicalHistoryBehaviorItemCollectionDao {

    @SerializedName("success")
    private int success;
    @SerializedName("data")
    private List<MedicalHistoryBehaviorItemDao> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<MedicalHistoryBehaviorItemDao> getData() {
        return data;
    }

    public void setData(List<MedicalHistoryBehaviorItemDao> data) {
        this.data = data;
    }
}
