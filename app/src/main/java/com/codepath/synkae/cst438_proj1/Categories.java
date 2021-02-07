package com.codepath.synkae.cst438_proj1;
import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.Expose;
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

    private class Category{
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("slug")
        private String slug;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getSlug() {
            return slug;
        }
    }
}


