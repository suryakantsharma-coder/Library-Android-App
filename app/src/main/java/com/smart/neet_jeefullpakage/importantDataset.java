package com.smart.neet_jeefullpakage;

import com.google.firebase.database.IgnoreExtraProperties;

import androidx.annotation.Keep;

@IgnoreExtraProperties
@Keep
public class importantDataset {
    String title, cover, categories;

    public importantDataset() {
    }

    public importantDataset(String title, String cover, String categories) {
        this.title = title;
        this.cover = cover;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}
