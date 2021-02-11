package com.codepath.synkae.cst438_proj1.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Job {
    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("tags")
    private ArrayList<String> tags;

    @SerializedName("job_type")
    private String job_type;

    @SerializedName("publication_date")
    private String publication_date;

    @SerializedName("candidate_required_location")
    private String candidate_required_location;

    @SerializedName("salary")
    private String salary;

    @SerializedName("Description")
    private String description;

    @SerializedName("company_logo_url")
    private String company_logo_url;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public String getCandidate_required_location() {
        return candidate_required_location;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany_logo_url() {
        return company_logo_url;
    }
}
