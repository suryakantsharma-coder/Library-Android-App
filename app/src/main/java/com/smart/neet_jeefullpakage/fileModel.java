package com.smart.neet_jeefullpakage;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.File;

import androidx.annotation.Keep;

@IgnoreExtraProperties
@Keep
public class fileModel {
    File file;
    boolean isFolder;

    public fileModel(File file, boolean isFolder) {
        this.file = file;
        this.isFolder = isFolder;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }
}
