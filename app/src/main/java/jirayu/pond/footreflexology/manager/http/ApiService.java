package jirayu.pond.footreflexology.manager.http;

import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemDao;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.OrganItemCollectionDao;
import jirayu.pond.footreflexology.dao.StatusDao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lp700 on 9/12/2559.
 */

public interface ApiService {

    @GET("{tableName}/{key}")
    Call<MemberItemCollectionDao>   loadMemberList(@Path("tableName") String tableName, @Path("key") String key);

    @GET("{tableName}/{key}")
    Call<DetailItemCollectionDao>   loadDetailList(@Path("tableName") String tableName, @Path("key") String key);

    @GET("{tableName}/{key}")
    Call<DiseaseItemCollectionDao>  loadDiseaseList(@Path("tableName") String tableName, @Path("key") String key);

    @GET("{tableName}/{key}")
    Call<OrganItemCollectionDao>    loadOrganList(@Path("tableName") String tableName, @Path("key") String key);

    @GET("{tableName}/{key}")
    Call<MedicalHistoryItemCollectionDao>     loadMedicalHistory(@Path("tableName") String tableName, @Path("key") int key);

    @GET("member/none/create")
    Call<StatusDao>    InsertMemberList(@Query("firstname") String firstName,
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
                                        @Query("updated_at") String updatedAt);

//    ?firstname={firstName}&lastname={lastName}&identification_number={identificationNumber}" +
//            "&gender={gender}&birthdate={birthDate}&telephone_number={telephoneNumber}&house_village={houseVillage}&sub_district={subDistrict}" +
//            "&district={district}&province={province}&created_at={createdAt}&updated_at={updatedAt}
}
