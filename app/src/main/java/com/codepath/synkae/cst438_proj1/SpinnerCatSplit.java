package com.codepath.synkae.cst438_proj1;

public class SpinnerCatSplit {
    public String category;
    public String slug;

    public SpinnerCatSplit(String category, String slug) {
        this.category = category;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return category;
    }
}
