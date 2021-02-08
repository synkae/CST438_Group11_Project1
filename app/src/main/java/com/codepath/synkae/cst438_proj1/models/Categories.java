package com.codepath.synkae.cst438_proj1.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Categories {
    @SerializedName("0-legal-notice")
    private String legalNotice;
    @SerializedName("job-count")
    private int jobCount;
    @SerializedName("jobs")
    private ArrayList<Category> categoryList;

    public String getLegalNotice() {
        return legalNotice;
    }

    public int getJobCount() {
        return jobCount;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }


}


