package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lp700 on 13/12/2559.
 */

public class DetailItemCollectionDao {

    @SerializedName("success")
    private int success;
    @SerializedName("data")
    private List<DetailItemDao> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<DetailItemDao> getData() {
        return data;
    }

    public void setData(List<DetailItemDao> data) {
        this.data = data;
    }

}
