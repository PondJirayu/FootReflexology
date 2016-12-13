package jirayu.pond.footreflexology.manager.http;

import jirayu.pond.footreflexology.dao.DetailItemCollectionDao;
import jirayu.pond.footreflexology.dao.MemberItemCollectionDao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lp700 on 9/12/2559.
 */

public interface ApiService {

    @GET("{tableName}/{key}")
    Call<MemberItemCollectionDao> loadMemberList(@Path("tableName") String tableName, @Path("key") String key);
    Call<DetailItemCollectionDao>   loadDetailList(@Path("tableName") String tableName, @Path("key") String key);

}
