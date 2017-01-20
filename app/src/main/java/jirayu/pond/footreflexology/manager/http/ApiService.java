package jirayu.pond.footreflexology.manager.http;

import jirayu.pond.footreflexology.dao.BehaviorCollectionDao;
import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.OrganItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusDao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lp700 on 9/12/2559.
 */

public interface ApiService {

    // Select

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
            @Path("key") int key
    );

    @GET("{tableName}")
    Call<BehaviorCollectionDao> loadBehavior(
            @Path("tableName") String tableName
    );

    // Insert

    @GET("member/none/create")
    Call<MemberItemCollectionDao> InsertMemberList(
            @Query("firstname") String firstName,
            @Query("lastname") String lastName,
            @Query("identification_number") String identificationNumber,
            @Query("gender") String gender,
            @Query("birthdate") String birthDate,
            @Query("telephone_number") String telephoneNumber,
            @Query("house_village") String houseVillage,
            @Query("sub_district") String subDistrict,
            @Query("district") String district,
            @Query("province") String province,
            @Query("created_at") String createdAt,
            @Query("updated_at") String updatedAt
    );

    @GET("medicalhistory/none/create")
    Call<StatusDao> InsertMedicalHistory(
            @Query("member_id") int memberId,
            @Query("disease_id") int diseaseId,
            @Query("behavior_id") int behaviorId
    );

    // Update

    @GET("member/{key}/edit")
    Call<StatusDao> UpdateMember(
            @Path("key") int key,
            @Query("firstname") String firstName,
            @Query("lastname") String lastName,
            @Query("identification_number") String identificationNumber,
            @Query("gender") String gender,
            @Query("birthdate") String birthDate,
            @Query("telephone_number") String telephoneNumber,
            @Query("house_village") String houseVillage,
            @Query("sub_district") String subDistrict,
            @Query("district") String district,
            @Query("province") String province,
            @Query("created_at") String createdAt,
            @Query("updated_at") String updatedAt
    );

    @GET("medicalhistory/{key}/edit")
    Call<StatusDao> UpdateMedicalHistory(
            @Path("key") int key,
            @Query("behavior_id") int behaviorId
    );

    // Delete

}
