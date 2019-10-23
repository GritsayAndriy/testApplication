package com.example.testapplication.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Link {

    @GET("trending")
    public Call<List<AModel>> getObjects();

    @GET("object/{id}")
    public Call<BModel> getObject(@Path("id") int id);
}
