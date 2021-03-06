package com.codepath.synkae.cst438_proj1;

import com.codepath.synkae.cst438_proj1.models.Categories;
import com.codepath.synkae.cst438_proj1.models.JobSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemotiveAPI {
    @GET("api/remote-jobs/categories")
    Call<Categories> getCategories();

    @GET("api/remote-jobs")
    Call<JobSearch> searchJobsKeyword(
            @Query("search") String search,
            @Query("limit") Integer limit
    );

    @GET("api/remote-jobs")
    Call<JobSearch> searchJobsCompany(
            @Query("company_name") String company,
            @Query("limit") Integer limit
    );

    @GET("api/remote-jobs")
    Call<JobSearch> searchJobsCategory(
            @Query("category") String category,
            @Query("limit") Integer limit
    );


}
