package com.codepath.synkae.cst438_proj1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RemotiveAPI {
    @GET("api/remote-jobs/categories")
    Call<Categories> getCategories();
}
