package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by lp700 on 13/12/2559.
 */

public class MedicalHistoryItemDao {

    @SerializedName("id")                           private int id;
    @SerializedName("firstname")                    private String firstName;
    @SerializedName("lastname")                     private String lastName;
    @SerializedName("disease_name")                 private String diseaseName;
    @SerializedName("list")                         private String list;
    @SerializedName("created_at")                   private Date createdAt;
    @SerializedName("updated_at")                   private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
