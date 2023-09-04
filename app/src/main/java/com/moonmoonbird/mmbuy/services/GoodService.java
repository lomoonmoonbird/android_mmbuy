package com.moonmoonbird.mmbuy.services;

import com.moonmoonbird.mmbuy.model.Good;
import com.moonmoonbird.mmbuy.model.GoodList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GoodService {
    @GET("/api/goods/index")
    public Call<GoodList> getGoodList(@Header("apiKey") String apiKey, @Query("page") int id);
}


