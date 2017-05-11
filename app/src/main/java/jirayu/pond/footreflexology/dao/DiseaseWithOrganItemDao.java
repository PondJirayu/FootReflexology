package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lp700 on 20/4/2560.
 */

public class DiseaseWithOrganItemDao {

    @SerializedName("id")
    private int diseaseId;
    @SerializedName("disease_name")
    private String diseaseName;
    @SerializedName("organ_name")
    private String organName;

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}
