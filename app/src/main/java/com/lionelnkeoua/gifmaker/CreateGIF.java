package com.lionelnkeoua.gifmaker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.view.ViewPager;

import com.lionelnkeoua.gifmaker.myInterface.OnGifFinishListnerr;
import com.lionelnkeoua.gifmaker.Model.Image;
import com.lionelnkeoua.gifmaker.Model.Picture;
import com.lionelnkeoua.gifmaker.Utils.BaseActivity;
import com.lionelnkeoua.gifmaker.Utils.Constant;
import com.waynejo.androidndkgif.GifEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class CreateGIF {


    public BaseActivity baseActivity;
    public ArrayList<Image> mPictures;
    public OnGifFinishListnerr onGifFinishListnerr;
    public ArrayList<Bitmap> mBitmaps = new ArrayList<>();





    public CreateGIF(BaseActivity baseActivity,ArrayList<Bitmap> mBitmoPS, ArrayList<Image> mPictures, ViewPager pager, OnGifFinishListnerr onGifFinishListnerr) {
        this.baseActivity = baseActivity;
        this.mPictures = mPictures;
        this.onGifFinishListnerr = onGifFinishListnerr;
        this.mBitmaps = mBitmoPS;
    }

    public void startConverting() {

        new MyTask().execute();

    }

    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected void onPreExecute() {

            baseActivity.appDialogue.showProgressDialogue();
        }

        @Override
        protected String doInBackground(Integer... params) {


            Observable.just(getHeightWeight()).subscribe(new Observer<ArrayList<Integer>>() {
                @Override
                public void onSubscribe(Disposable d) {


                }

                @Override
                public void onNext(ArrayList<Integer> value) {




                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    File direct = new File(Environment.getExternalStorageDirectory() + "/" + Constant.APPNAME + "/");
                    if (!direct.exists()) {
                        File wallpaperDirectory = new File("/sdcard" + "/" + Constant.APPNAME + "/");
                        wallpaperDirectory.mkdirs();
                    }

                    Calendar rightNow = Calendar.getInstance();
                    String gifPath = rightNow.getTimeInMillis() + "VMG" + ".gif";


                    File file = new File(new File("/sdcard" + "/" + Constant.APPNAME + "/"), gifPath);
                    Picture picture = new Picture();
                    picture.setPath(file.getAbsolutePath());
                    baseActivity.prefrences.setPicture(picture);
                    if (file.exists()) {
                        file.delete();
                    }

                    baseActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));







                    GifEncoder gifEncoder = new GifEncoder();

                   // gifEncoder.setDither(true);
                    try {
                        gifEncoder.init(value.get(1), value.get(0), file.getAbsolutePath(), GifEncoder.EncodingType.ENCODING_TYPE_NORMAL_LOW_MEMORY);
                        for (int i = 0; i < mBitmaps.size(); i++) {
                            Bitmap bitmap = mBitmaps.get(i);
                            publishProgress(i);
                            gifEncoder.encodeFrame(bitmap, (int) mPictures.get(i).getDuration());
                            //gifEncoder.setDither();
                        }
                        gifEncoder.close();
                        mPictures.clear();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }











                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });


            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            onGifFinishListnerr.onGifFinish();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

            try {
                baseActivity.appDialogue.txtProgress.setText(values[0] * 100 / mPictures.size() + "");
                baseActivity.appDialogue.circle_progress.setProgress(values[0] * 100 / mPictures.size());
            } catch (Exception e) {

            }
        }

    }





    public ArrayList<Integer> getHeightWeight() {

        List<Integer> listHeight = new ArrayList<>();
        List<Integer> listWeight = new ArrayList<>();
        ArrayList<Integer> mHeightWeight = new ArrayList<>();

        for (int i = 0; i < mBitmaps.size(); i++) {
            Bitmap bitmap= mBitmaps.get(i);
            listHeight.add(bitmap.getHeight());
            listWeight.add(bitmap.getWidth());
        }

        int maxHeight = Collections.max(listHeight);
        int maxWeight = Collections.max(listWeight);

        mHeightWeight.add(maxHeight);
        mHeightWeight.add(maxWeight);
        return mHeightWeight;


    }



}
