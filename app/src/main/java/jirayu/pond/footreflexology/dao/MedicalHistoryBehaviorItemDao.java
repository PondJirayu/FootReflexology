package jirayu.pond.footreflexology.dao;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * Created by lp700 on 13/4/2560.
 */

public class MedicalHistoryBehaviorItemDao {

    @SerializedName("id")
    private int id;
    @SerializedName("medical_history_id")
    private int medicalHistoryId;
    @SerializedName("behavior_id")
    private int behaviorId;
    @SerializedName("created_at")
    private Timestamp createdAt;
    @SerializedName("updated_at")
    private Timestamp updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicalHistoryId() {
        return medicalHistoryId;
    }

    public void setMedicalHistoryId(int medicalHistoryId) {
        this.medicalHistoryId = medicalHistoryId;
    }

    public int getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(int behaviorId) {
        this.behaviorId = behaviorId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
