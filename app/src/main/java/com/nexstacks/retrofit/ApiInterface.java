package com.nexstacks.retrofit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    /**
     * 1. Get
     * 2. Post
     * 3. Put
     * 4. Delete
     *
     */

    @GET("everything/")
    Call<String> getAllNews(@QueryMap HashMap<String, Object> queries);

}
