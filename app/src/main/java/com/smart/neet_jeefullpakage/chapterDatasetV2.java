package com.smart.neet_jeefullpakage;

public class chapterDatasetV2 {
    String name;
    String path;
    Boolean DownloadOrNot;

//    public chapterDatasetV2(String name, String path, Boolean downloadOrNot) {
//        this.name = name;
//        this.path = path;
//        this.DownloadOrNot = downloadOrNot;
//    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDownloadOrNot() {
        return DownloadOrNot;
    }

    public void setDownloadOrNot(Boolean downloadOrNot) {
        DownloadOrNot = downloadOrNot;
    }
}
