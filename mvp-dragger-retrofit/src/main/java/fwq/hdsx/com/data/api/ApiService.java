package fwq.hdsx.com.data.api;

import java.util.List;

import fwq.hdsx.com.model.User;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by lcc on 16/4/28.
 */
public interface ApiService {
    @GET("/users")
    public void getUsers(Callback<List<User>> callback);
}
