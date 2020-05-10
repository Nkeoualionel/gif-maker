package com.lionelnkeoua.gifmaker.myAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lionelnkeoua.gifmaker.Model.Data;
import com.lionelnkeoua.gifmaker.Model.Picture;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.ShowGifActivity;
import com.lionelnkeoua.gifmaker.Utils.BaseActivity;
import com.lionelnkeoua.gifmaker.Utils.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Chirag on 17-10-2017.
 */
public class GiphyAdapters extends RecyclerView.Adapter<GiphyAdapters.Holder> {


    private Context mContext;
    private ArrayList<Data> mPictures;
    private BaseActivity baseActivity;


    public GiphyAdapters(Context mContext, ArrayList<Data> mPictures) {
        this.mContext = mContext;
        this.baseActivity = (BaseActivity) mContext;
        this.mPictures = mPictures;
    }

    @Override
    public GiphyAdapters.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.row_my_gif, parent, false));
    }

    @Override
    public void onBindViewHolder(GiphyAdapters.Holder holder, int position) {

        final Data picture = mPictures.get(position);
        Glide.with(mContext).load(picture.getImages().getFixed_height_still().getUrl()).asBitmap().error(R.drawable.image_loader).placeholder(R.drawable.image_loader).into(holder.imgGif);

        Log.e("URL", picture.getImages().getFixed_height_still().getUrl().replace("200_s","giphy"));
        holder.imgGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DownloadFile(picture.getImages().getFixed_height_still().getUrl().replace("200_s","giphy")).execute();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mPictures.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public ImageView imgGif;

        public Holder(View itemView) {
            super(itemView);

            imgGif = (ImageView) itemView.findViewById(R.id.imgGif);
        }
    }

    public void shareGify(Data data) {


    }


    private class DownloadFile extends AsyncTask<String, String, File> {

        String allTones;

        public DownloadFile(String allTones) {

            baseActivity.appDialogue.showProgressDialogue();
            baseActivity.appDialogue.txtStatus.setText("DOWNLOAD FROM GIPHY...");
            this.allTones = allTones;
        }

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            Log.e("ON", "START");
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected File doInBackground(String... all_tones) {
            int count;
            File file;
            try {
                URL url = new URL(allTones);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();

                InputStream input = conection.getInputStream();


                Calendar rightNow = Calendar.getInstance();
                String gifPath = rightNow.getTimeInMillis() + "VMG" + ".gif";
                file = new File(new File("/sdcard" + "/" + Constant.APPNAME + "/"), gifPath);
                if (file.exists()) {
                    file.delete();
                }


                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    output.write(data, 0, count);
                }

                output.flush();

                output.close();
                input.close();

            } catch (Exception e) {
                return null;
            }
            return file;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage

            baseActivity.appDialogue.circle_progress.setProgress(Integer.parseInt(progress[0]));

        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(File file) {
            // dismiss the dialog after the file was downloaded


            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    baseActivity.appDialogue.dismisProgressDialogue();

                    Picture picture = new Picture();
                    picture.setPath(file.getAbsolutePath());
                    baseActivity.prefrences.setPicture(picture);
                    Intent intent = new Intent(mContext, ShowGifActivity.class);
                    mContext.startActivity(intent);

                }
            }, 1000);
        }

    }


}
