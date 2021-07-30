package com.smart.neet_jeefullpakage;

import com.google.firebase.database.IgnoreExtraProperties;

import androidx.annotation.Keep;

@IgnoreExtraProperties
@Keep
public class searchDataset {
    String title, collection , document ;

    public searchDataset() {
    }

    public searchDataset(String title, String collection, String document) {
        this.title = title;
        this.collection = collection;
        this.document = document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
