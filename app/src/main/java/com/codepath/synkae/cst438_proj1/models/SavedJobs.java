package com.codepath.synkae.cst438_proj1.models;

import androidx.room.*;

@Entity(tableName = "savedjobs")
public class SavedJobs {

    @PrimaryKey(autoGenerate = true)
    private int mSaveJId;

    @ColumnInfo(name = "userId")
    private String mUserId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "companyName")
    private String mCompanyName;

    @ColumnInfo(name = "jobType")
    private String mJobType;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "salary")
    private String mSalary;



    public SavedJobs(String userId, String title, String companyName, String jobType, String description, String salary){
        mUserId = userId;
        mTitle = title;
        mCompanyName = companyName;
        mJobType = jobType;
        mDescription = description;
        mSalary = salary;
    }

    public int getmSaveJId() {
        return mSaveJId;
    }

    public void setmSaveJId(int mSaveJId) {
        this.mSaveJId = mSaveJId;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public String getmJobType() {
        return mJobType;
    }

    public void setmJobType(String mJobType) {
        this.mJobType = mJobType;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmSalary() {
        return mSalary;
    }

    public void setmSalary(String mSalary) {
        this.mSalary = mSalary;
    }
}
