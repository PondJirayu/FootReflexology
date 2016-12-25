package jirayu.pond.footreflexology.manager.http;

import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.DiseaseItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemCollectionDao;
import jirayu.pond.footreflexology.dao.MedicalHistoryItemDao;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import jirayu.pond.footreflexology.dao.OrganItemCollectionDao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

}
