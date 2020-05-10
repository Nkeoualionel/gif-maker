package com.lionelnkeoua.gifmaker.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lionelnkeoua.gifmaker.Model.Image;
import com.lionelnkeoua.gifmaker.Model.Picture;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by mahadev on 6/21/16.
 */
public class SharedPrefrences {

    SharedPreferences mPref;
    SharedPreferences.Editor editor;

    public SharedPrefrences(Context mContext) {
        mPref = mContext.getSharedPreferences("PictureGIF", Context.MODE_PRIVATE);
        editor = mPref.edit();
    }



    public void setPicture(Picture user) {

        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("Picture", json);
        editor.commit();
    }

    public Picture getPicture() {
        Gson gson = new Gson();
        String json = mPref.getString("Picture", "");
        Picture user = gson.fromJson(json, Picture.class);
        return user;
    }


    public void setCourseList(ArrayList<Image> mCourseList){
        Gson gson = new Gson();
        String json = gson.toJson(mCourseList);
        editor.putString("CourceList", json);
        editor.commit();

    }
    public ArrayList<Image> getCourseList(){
        String json = mPref.getString("CourceList", null);
        Type type = new TypeToken<ArrayList<Image>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }



}
