package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lp700 on 13/12/2559.
 */

public class DiseaseItemCollectionDao {

    @SerializedName("success")          private int success;
    @SerializedName("data")             private List<DiseaseItemDao> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<DiseaseItemDao> getData() {
        return data;
    }

    public void setData(List<DiseaseItemDao> data) {
        this.data = data;
    }
}
