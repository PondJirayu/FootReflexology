package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lp700 on 15/1/2560.
 */

public class BehaviorDao {

    @SerializedName("id")
    private int id;
    @SerializedName("list")
    private String list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
