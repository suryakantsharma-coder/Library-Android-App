package com.smart.neet_jeefullpakage;

import com.google.firebase.database.IgnoreExtraProperties;

import androidx.annotation.Keep;

@IgnoreExtraProperties
@Keep
public class chapterDataset {
    String url, timeStamp;
    int chapterNo;

    public chapterDataset() {
    }

    public chapterDataset(String url, String timeStamp, int chapterNo) {
        this.url = url;
        this.timeStamp = timeStamp;
        this.chapterNo = chapterNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }
}
