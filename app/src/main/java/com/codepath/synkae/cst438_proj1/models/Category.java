package com.codepath.synkae.cst438_proj1.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Category {
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

    public Category(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
