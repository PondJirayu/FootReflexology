package jirayu.pond.footreflexology.manager.http;

import jirayu.pond.footreflexology.dao.MemberItemDao;
import jirayu.pond.footreflexology.dao.OrganItemDao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lp700 on 9/12/2559.
 */

public interface ApiService {

    @GET("{tableName}/{key}")
    Call<MemberItemDao> loadMemberList(@Path("tableName") String tableName, @Path("key") String key);
    Call<OrganItemDao> loadOrganList(@Path("tableName") String tableName, @Path("key") String key);

}
