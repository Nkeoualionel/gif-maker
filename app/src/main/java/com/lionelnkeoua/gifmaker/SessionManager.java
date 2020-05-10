package com.lionelnkeoua.gifmaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by mahadev on 1/27/17.
 */
public class SessionManager extends MultiDexApplication {
    public Drawable textDrawable;

    public Bitmap bmpTextSticker;
    Long previousTime;
    InterstitialAd mInterstitialAd;


    private static Tracker sTracker;
    private static GoogleAnalytics sAnalytics;






    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sAnalytics = GoogleAnalytics.getInstance(this);
    }




    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.google_anlystic);
        }

        return sTracker;
    }






    public void setIntrestialAds() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.intrestial_ads));
        loadIntrestialAds();
    }

    public void displayBanner(Activity activity) {
        AdView mAdView = (AdView) activity.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void loadIntrestialAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

    }

    public void showIntrestialWithWait() {

        if (ifAdsShow()){

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Log.e("Ads Error Code",errorCode+"");
                        super.onAdFailedToLoad(errorCode);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();
                    }

                    @Override
                    public void onAdOpened() {
                        Log.e("Ads ","Open");
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        Log.e("Ads ","load");
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdClosed() {
                        Log.e("Ads ","Closeed");
                        loadIntrestialAds();
                    }
                });

            } else {
                loadIntrestialAds();
            }

        }

        }

    public boolean ifAdsShow() {

        if (previousTime == null || previousTime < 1) {
            Long tsLong = System.currentTimeMillis();
            previousTime = tsLong;
            return false;
        }
        Long tsDiffrence = System.currentTimeMillis() - previousTime;
        if ((tsDiffrence / 1000) >= 15) {
            previousTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

public void showIntrestial() {

    if (ifAdsShow()){

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Log.e("Ads Error Code",errorCode+"");
                        super.onAdFailedToLoad(errorCode);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();
                    }

                    @Override
                    public void onAdOpened() {
                        Log.e("Ads ","Open");
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        Log.e("Ads ","load");
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdClosed() {
                        Log.e("Ads ","Closeed");
                        loadIntrestialAds();
                    }
                });

            } else {
                loadIntrestialAds();
            }


        }
        }




}
