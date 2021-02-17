package com.codepath.synkae.cst438_proj1.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.synkae.cst438_proj1.db.AppDatabase;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = AppDatabase.SAVEDJOBS_TABLE)
public class Job implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int saveJId;

    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    private String category;

    @SerializedName("company_name")
    private String companyName;

    @Ignore
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

    @SerializedName("description")
    private String description;

    @SerializedName("company_logo_url")
    private String company_logo_url;

    private int mUserId;

    public int getMUserId() {
        return mUserId;
    }

    public void setMUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getSaveJId() {
        return saveJId;
    }

    public void setSaveJId(int saveJId) {
        this.saveJId = saveJId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public String getCandidate_required_location() {
        return candidate_required_location;
    }

    public void setCandidate_required_location(String candidate_required_location) {
        this.candidate_required_location = candidate_required_location;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany_logo_url() {
        return company_logo_url;
    }

    public void setCompany_logo_url(String company_logo_url) {
        this.company_logo_url = company_logo_url;
    }

    public Job(int id, String url, String title, String category, String companyName, String job_type, String publication_date, String candidate_required_location, String salary, String description, String company_logo_url) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.category = category;
        this.companyName = companyName;
        this.job_type = job_type;
        this.publication_date = publication_date;
        this.candidate_required_location = candidate_required_location;
        this.salary = salary;
        this.description = description;
        this.company_logo_url = company_logo_url;
    }
}
