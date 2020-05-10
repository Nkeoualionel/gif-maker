package com.lionelnkeoua.gifmaker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lionelnkeoua.gifmaker.Utils.BaseActivity;

import java.io.File;
import java.lang.reflect.Method;


public class ShowGifActivity extends BaseActivity {

    public static String path = "";
    private ImageView imgGif;


    private FrameLayout frameLoader;
    public ImageView imgThumbline;
    public ImageView imgGifCircle;


    private LinearLayout lnvFaceBook;
    private LinearLayout lnvWhatsuo;
    private LinearLayout lnvInstagram;
    private LinearLayout lnvTwitter;
    private LinearLayout lnvSnapChat;
    private LinearLayout lnvLinkedIn;
    private LinearLayout lnvSkype;
    private LinearLayout lnvMore;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gif_activity);

        initToolBar("Share GIF");
        imgAdd.setVisibility(View.GONE);
        imgMyGIF.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        initView();
        initData();
        initClickListenr();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequestBottom = new AdRequest
                .Builder()
                .build();
        mAdView.loadAd(adRequestBottom);
    }


    @Override
    public void initView() {
        imgGif = (ImageView) findViewById(R.id.imgGif);
        lnvFaceBook = (LinearLayout) findViewById(R.id.lnvFaceBook);
        lnvWhatsuo = (LinearLayout) findViewById(R.id.lnvWhatsuo);
        lnvInstagram = (LinearLayout) findViewById(R.id.lnvInstagram);
        lnvMore = (LinearLayout) findViewById(R.id.lnvMore);
        imgThumbline = (ImageView) findViewById(R.id.imgThumbline);
        imgGifCircle = (ImageView) findViewById(R.id.imgGifCircle);
        frameLoader = (FrameLayout) findViewById(R.id.frameLoader);


        lnvFaceBook = (LinearLayout) findViewById(R.id.lnvFaceBook);
        lnvWhatsuo = (LinearLayout) findViewById(R.id.lnvWhatsuo);
        lnvInstagram = (LinearLayout) findViewById(R.id.lnvInstagram);
        lnvTwitter = (LinearLayout) findViewById(R.id.lnvTwitter);
        lnvSnapChat = (LinearLayout) findViewById(R.id.lnvSnapChat);
        lnvLinkedIn = (LinearLayout) findViewById(R.id.lnvLinkedIn);
        lnvSkype = (LinearLayout) findViewById(R.id.lnvSkype);
        lnvMore = (LinearLayout) findViewById(R.id.lnvMore);

     // applicationManager.displayBanner((Activity) mContext);
    }

    @Override
    public void initData() {

        applicationManager.showIntrestial();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                applicationManager.showIntrestial();

            }
        },250);


        //diskCacheStrategy(DiskCacheStrategy.ALL)
        path = prefrences.getPicture().getPath();
        File file = new File(prefrences.getPicture().getPath());
        Glide.with(mContext).load(file).asBitmap().into(imgThumbline);
        Glide.with(mContext).load(file).asGif().listener(new RequestListener<File, GifDrawable>() {
            @Override
            public boolean onException(Exception e, File model, Target<GifDrawable> target, boolean isFirstResource) {
                frameLoader.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, File model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                frameLoader.setVisibility(View.GONE);
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.image_loader).into(imgGif);
    }

    @Override
    public void initClickListenr() {


        lnvWhatsuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImage("com.whatsapp");
            }
        });

        lnvFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImage("com.facebook.katana");
            }
        });
        lnvInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImage("com.instagram.android");
            }
        });
        lnvSkype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareImage("com.skype.raider");
            }
        });
        lnvLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage("com.google.android.gm");

            }
        });

        lnvSnapChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage("com.snapchat.android");


            }
        });


        lnvTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareImage("com.twitter.android");

            }
        });



        lnvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Uri uri = Uri.fromFile(new File(path));
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    sharingIntent.setType("image/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));

                } catch (Exception ex) {

                }

            }
        });
    }


    public void shareImage(String pacakage_name) {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                applicationManager.showIntrestial();

            }
        },250);



        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Uri imageUri = Uri.parse(new File(path).getAbsolutePath());
        //Uri uri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName()+"", new File(path));
        Uri uri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //Target whatsapp:
        shareIntent.setPackage(pacakage_name);
        //Add text and then Image URI
        // shareIntent.putExtra(Intent.EXTRA_TEXT,"Hello");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/gif");

        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            //  ToastHelper.MakeShortText("Whatsapp have not been installed.");
        }

    }




       /* try {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            Uri uri = Uri.fromFile(new File(path));
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.setType("image*//*");
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
            }       } catch (Exception ex) {
            Toast.makeText(mContext, pacakage_name + " not Installccced", Toast.LENGTH_SHORT)
                    .show();
        }*/


    @Override
    protected void onResume() {
        super.onResume();
        applicationManager.showIntrestial();
    }
}


