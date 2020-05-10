 package com.lionelnkeoua.gifmaker.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lionelnkeoua.gifmaker.myActivities.MyGifActivity;
import com.lionelnkeoua.gifmaker.myCustomText.TextsMediumBold;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.SessionManager;
import com.lionelnkeoua.gifmaker.SettingActivity;

 /**
 * Created by Chirag on 25-09-2017.
 */
public abstract class BaseActivity extends AppCompatActivity {


    public AppDialogue appDialogue;

    //Abstract method
    public abstract void initView();

    public abstract void initData();

    public abstract void initClickListenr();

    public SessionManager applicationManager;

    //DataType
    public Context mContext;
    public Toolbar toolbar;
    public ImageView imgAdd;
    public ImageView imgBack;
    public ImageView imgMyGIF;
    public ImageView imgSettings;
    public TextsMediumBold toolbar_title;


    public SharedPrefrences prefrences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        logDebug("Activity", mContext.getClass().getSimpleName());
        prefrences = new SharedPrefrences(mContext);
        appDialogue = new AppDialogue(mContext);
           applicationManager = (SessionManager) getApplication();
        applicationManager.setIntrestialAds();


        Tracker mTracker = applicationManager.getDefaultTracker();
        Log.i("Tracker", "Setting screen name: " + "Launching");
        mTracker.setScreenName("Classes~" + "" + mContext.getClass());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }


    public void initToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        imgAdd = (ImageView) toolbar.findViewById(R.id.imgAdd);
        imgBack = (ImageView) toolbar.findViewById(R.id.imgBack);
        imgMyGIF = (ImageView) toolbar.findViewById(R.id.imgMyGIF);
        imgSettings = (ImageView) toolbar.findViewById(R.id.imgSettings);
        toolbar_title = (TextsMediumBold) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);

        imgMyGIF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MyGifActivity.class);
                startActivity(intent);
            }
        });

        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           onBackPressed();
                                       }
                                   }
        );

    }


    public void logDebug(String tag, String message) {
        Log.d(mContext.getClass().getSimpleName(), "TAG " + tag + " - " + message);
    }

    public void logError(String tag, String message) {
        Log.e(mContext.getClass().getSimpleName(), "TAG " + tag + " - " + message);
    }


    public void loadImage(String url, ImageView imageView) {
        Glide.with(mContext).load(url).asBitmap().override(70, 70).into(imageView);
    }


    public void toast(String message, int duration) {
        // 0 - Sort
        // 1 - Long
        Toast.makeText(mContext, message, duration).show();
    }


    public void replaceFragmentWithTag(Fragment fragment, String tag) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(tag, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(tag);
            ft.commit();
        }
    }

    public void replaceFragmentWithoutBack(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }
    }


    public void applyTheme(ImageView imageView, int color) {
        imageView.getDrawable().setColorFilter((ContextCompat.getColor(mContext, R.color.colorAccent)), PorterDuff.Mode.SRC_IN);

    }


    public boolean getGalleryPermisipon() {


        if (android.os.Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "Permission is granted");
                //File write logic here
                return true;
            }
            {
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else {
            return true;
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
