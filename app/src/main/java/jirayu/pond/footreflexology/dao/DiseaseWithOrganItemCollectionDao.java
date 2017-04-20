package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lp700 on 20/4/2560.
 */

public class DiseaseWithOrganItemCollectionDao {

    @SerializedName("success")
    private int success;
    @SerializedName("diseasewithorgan")
    private List<List<DiseaseWithOrganItemDao>> diseaseWithOrganItemDaos;
    @SerializedName("behaviorofdiseasewithorgan")
    private List<BehaviorOfDiseaseWithOrganItemDao> behaviorOfDiseaseWithOrganItemDaos;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<List<DiseaseWithOrganItemDao>> getDiseaseWithOrganItemDaos() {
        return diseaseWithOrganItemDaos;
    }

    public void setDiseaseWithOrganItemDaos(List<List<DiseaseWithOrganItemDao>> diseaseWithOrganItemDaos) {
        this.diseaseWithOrganItemDaos = diseaseWithOrganItemDaos;
    }

    public List<BehaviorOfDiseaseWithOrganItemDao> getBehaviorOfDiseaseWithOrganItemDaos() {
        return behaviorOfDiseaseWithOrganItemDaos;
    }

    public void setBehaviorOfDiseaseWithOrganItemDaos(List<BehaviorOfDiseaseWithOrganItemDao> behaviorOfDiseaseWithOrganItemDaos) {
        this.behaviorOfDiseaseWithOrganItemDaos = behaviorOfDiseaseWithOrganItemDaos;
    }
}
