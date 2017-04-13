package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lp700 on 13/12/2559.
 */

public class DetailItemDao {

    @SerializedName("id")
    private int id;
    @SerializedName("organ_id")
    private int organId;
    @SerializedName("disease_id")
    private int diseaseId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("disease_name")
    private String diseaseName;
    @SerializedName("detail")
    private String detail;
    @SerializedName("treatment")
    private String treatment;
    @SerializedName("shouldeat")
    private String shouldEat;
    @SerializedName("shouldnoteat")
    private String shouldNotEat;
    @SerializedName("recommend")
    private String recommend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTreatMent() {
        return treatment;
    }

    public void setTreatMent(String treatment) {
        this.treatment = treatment;
    }

    public String getShouldEat() {
        return shouldEat;
    }

    public void setShouldEat(String shouldEat) {
        this.shouldEat = shouldEat;
    }

    public String getShouldNotEat() {
        return shouldNotEat;
    }

    public void setShouldNotEat(String shouldNotEat) {
        this.shouldNotEat = shouldNotEat;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

}
