package jirayu.pond.footreflexology.manager.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lp700 on 9/12/2559.
 */

public interface ApiService {

    @GET("{table_name}/{key}")
    Call<Object> loadMemberList(@Path("table_name") String table_name, @Path("key") String key);

}
