package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lp700 on 13/12/2559.
 */

public class MedicalHistoryItemCollectionDao {

    @SerializedName("success")                     private int success;
    @SerializedName("data")                        private List<MedicalHistoryItemDao> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<MedicalHistoryItemDao> getData() {
        return data;
    }

    public void setData(List<MedicalHistoryItemDao> data) {
        this.data = data;
    }
}
