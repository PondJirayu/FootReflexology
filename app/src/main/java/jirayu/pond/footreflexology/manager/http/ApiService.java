package jirayu.pond.footreflexology.manager.http;

import java.sql.Date;
import java.sql.Timestamp;

import jirayu.pond.footreflexology.dao.BehaviorItemCollectionDao;
import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseWithOrganItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryBehaviorItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.OrganItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusItemDao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lp700 on 9/12/2559.
 */

public interface ApiService {

    /**************
     * Select
     **************/

    @GET("{tableName}/{key}")
    Call<MemberItemCollectionDao> loadMemberList(
            @Path("tableName") String tableName,
            @Path("key") String key
    );

    @GET("{tableName}/{key}")
    Call<DetailItemCollectionDao> loadDetailList(
            @Path("tableName") String tableName,
            @Path("key") String key
    );

    @GET("{tableName}/{key}")
    Call<DiseaseWithOrganItemCollectionDao> loadDiseaseWithOrgan(
            @Path("tableName") String tableName,
            @Path("key") int key
    );

    @GET("{tableName}/{key}")
    Call<DiseaseItemCollectionDao> loadDiseaseList(
            @Path("tableName") String tableName,
            @Path("key") String key
    );

    @GET("{tableName}/{key}")
    Call<OrganItemCollectionDao> loadOrganList(
            @Path("tableName") String tableName,
            @Path("key") String key
    );

    @GET("{tableName}/{key}")
    Call<MedicalHistoryItemCollectionDao> loadMedicalHistory(
            @Path("tableName") String tableName,
            @Path("key") int key,
            @Query("sortId") int sortId
    );

    @GET("{tableName}")
    Call<BehaviorItemCollectionDao> loadBehavior(
            @Path("tableName") String tableName
    );

    @GET("{tableName}/{key}")
    Call<MedicalHistoryBehaviorItemCollectionDao> loadMedicalHistoryBehavior(
            @Path("tableName") String tableName,
            @Path("key") int key
    );

    /**************
     * Insert
     **************/

    @GET("member/none/create")
    Call<MemberItemCollectionDao> InsertMemberList(
            @Query("firstname") String firstName,
            @Query("lastname") String lastName,
            @Query("identification_number") String identificationNumber,
            @Query("gender") String gender,
            @Query("birthdate") Date birthDate,
            @Query("telephone_number") String telephoneNumber,
            @Query("house_village") String houseVillage,
            @Query("sub_district") String subDistrict,
            @Query("district") String district,
            @Query("province") String province,
            @Query("created_at") Timestamp createdAt,
            @Query("updated_at") Timestamp updatedAt
    );

    @GET("medicalhistory/none/create")
    Call<StatusItemDao> InsertMedicalHistory(
            @Query("member_id") int memberId,
            @Query("disease_id") int diseaseId,
            @Query("behavior_id") int behaviorId,
            @Query("created_at") Timestamp createdAt,
            @Query("updated_at") Timestamp updatedAt
    );

    /**************
     * Update
     **************/

    @GET("member/{key}/edit")
    Call<StatusItemDao> UpdateMember(
            @Path("key") int key,
            @Query("firstname") String firstName,
            @Query("lastname") String lastName,
            @Query("identification_number") String identificationNumber,
            @Query("gender") String gender,
            @Query("birthdate") Date birthDate,
            @Query("telephone_number") String telephoneNumber,
            @Query("house_village") String houseVillage,
            @Query("sub_district") String subDistrict,
            @Query("district") String district,
            @Query("province") String province,
            @Query("updated_at") Timestamp updatedAt
    );

    @GET("medicalhistory/{key}/edit")
    Call<StatusItemDao> UpdateMedicalHistory(
            @Path("key") int key,
            @Query("behavior_id") int behaviorId,
            @Query("created_at") Timestamp createAt,
            @Query("updated_at") Timestamp updatedAt
    );

    /**************
     * Delete
     **************/

}
