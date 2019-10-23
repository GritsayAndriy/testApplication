package com.example.testapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestService {

    private static RequestService requestService;
    private static final String BASE_URL = "https://demo0040494.mockable.io/api/v1/";
    private Retrofit retrofit;

    private RequestService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestService getInstance(){
        if (requestService == null){
            requestService = new RequestService();
        }
        return requestService;
    }

    public Link getRequest(){
        return retrofit.create(Link.class);
    }


}
