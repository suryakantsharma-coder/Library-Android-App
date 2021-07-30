package com.smart.neet_jeefullpakage;

public class serverChapterDataset {
    String Name;
    int chapterNo;
    String timeStemp;
    String url;

    public serverChapterDataset(String name, int chapterNo, String timeStemp, String url) {
        Name = name;
        this.chapterNo = chapterNo;
        this.timeStemp = timeStemp;
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeStemp() {
        return timeStemp;
    }

    public void setTimeStemp(String timeStemp) {
        this.timeStemp = timeStemp;
    }
}
