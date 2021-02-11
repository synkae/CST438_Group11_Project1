package com.codepath.synkae.cst438_proj1.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JobSearch {
    @SerializedName("0-legal-notice")
    private String legalNotice;
    @SerializedName("job-count")
    private int jobCount;
    @SerializedName("jobs")
    private ArrayList<Job> jobsList;

    public String getLegalNotice() {
        return legalNotice;
    }

    public int getJobCount() {
        return jobCount;
    }

    public ArrayList<Job> getJobsList() {
        return jobsList;
    }
}
