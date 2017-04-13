package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lp700 on 12/12/2559.
 */

public class MemberItemCollectionDao {

    @SerializedName("success")
    private int success;
    @SerializedName("data")
    private List<MemberItemDao> data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<MemberItemDao> getData() {
        return data;
    }

    public void setData(List<MemberItemDao> data) {
        this.data = data;
    }

}
