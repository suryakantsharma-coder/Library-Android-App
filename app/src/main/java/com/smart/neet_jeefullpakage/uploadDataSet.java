package com.smart.neet_jeefullpakage;

import com.google.firebase.database.IgnoreExtraProperties;

import androidx.annotation.Keep;

@IgnoreExtraProperties
@Keep
public class uploadDataSet {
    String title, description, author, date,thumbnail;

    public uploadDataSet() {
    }

    public uploadDataSet(String title, String description, String author, String date, String thumbnail) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.date = date;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
