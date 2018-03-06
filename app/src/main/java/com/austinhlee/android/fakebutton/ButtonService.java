package com.austinhlee.android.fakebutton;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Austin Lee on 3/5/2018.
 */

public interface ButtonService {

    @GET("user")
    Call<List<User>> listUser(@Query("candidate") String candidate);

    @POST("user")
    Call<User> createUser(@Body User user);

}
