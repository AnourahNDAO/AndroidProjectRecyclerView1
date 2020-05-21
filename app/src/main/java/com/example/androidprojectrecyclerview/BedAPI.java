package com.example.androidprojectrecyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BedAPI {
    @GET("/api/v2/bedroom")
    static Call<List<RestResponse>> getBedroomResponse() {
        return null;
    }

    @GET("/api/v2/ability")
    Call<List<RestResponse>> getability();
}
