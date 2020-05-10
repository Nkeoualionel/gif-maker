package com.lionelnkeoua.gifmaker.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chirag on 05-01-2018.
 */

public class Image implements Serializable {
    Fixed_height_still  fixed_height_still;

    String id;
    String thumbPath;
    String fileName;
    long Duration=1000;
    String uri;
    String folderName;


    public Fixed_height_still getFixed_height_still() {
        return fixed_height_still;
    }

    public void setFixed_height_still(Fixed_height_still fixed_height_still) {
        this.fixed_height_still = fixed_height_still;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    ArrayList<Image> mImages;
    boolean isSelect = false;
    public ArrayList<Image> getmImages() {
        return mImages;
    }
    public void setmImages(ArrayList<Image> mImages) {
        this.mImages = mImages;
    }
    public String getFolderName() {
        return folderName;
    }
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getThumbPath() {
        return thumbPath;
    }
    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
