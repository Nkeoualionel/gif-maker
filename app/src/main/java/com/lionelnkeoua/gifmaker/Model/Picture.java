package com.lionelnkeoua.gifmaker.Model;

import android.graphics.Bitmap;

/**
 * Created by Chirag on 29-03-2017.
 */
public class
Picture {

    Bitmap bitmap;
    String path;
    int duration=100;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
