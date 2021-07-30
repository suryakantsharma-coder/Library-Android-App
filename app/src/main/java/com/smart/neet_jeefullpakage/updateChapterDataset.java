package com.smart.neet_jeefullpakage;

public class updateChapterDataset {
    String Name;
    String timestemp;
    int chapterNo;
    String url;

    public updateChapterDataset(String name, String timestemp, int chapterNo, String url) {
        Name = name;
        this.timestemp = timestemp;
        this.chapterNo = chapterNo;
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

    public String getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(String timestemp) {
        this.timestemp = timestemp;
    }
}
