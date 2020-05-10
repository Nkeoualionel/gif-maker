package com.lionelnkeoua.gifmaker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lionelnkeoua.gifmaker.Utils.BaseActivity;
import com.lionelnkeoua.gifmaker.Utils.Constant;
import com.madx.updatechecker.lib.UpdateRunnable;

/**
 * Created by Chirag on 10-04-2017.
 */
public class SettingActivity extends BaseActivity {

    private LinearLayout lnvSize;
    private TextView txtMaxFrame;
    private LinearLayout lnvmAxframe;
    private LinearLayout lnvFeedBack;
    private LinearLayout lnvLangue;
    private LinearLayout lnvVersion;
    private LinearLayout lnvWebsite;
    private LinearLayout lnvShare;
    private LinearLayout lnvApp;
    private TextView txtMaxSize;
    private TextView txtFolder;
    private TextView txtVersion;
    private SeekBar seekBarFrame;

    private AdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        initView();
        initData();
        initToolBar("Settings");
        imgAdd.setVisibility(View.GONE);
        imgMyGIF.setVisibility(View.GONE);
        imgBack.setVisibility(View.VISIBLE);
        initClickListenr();



    }

    @Override
    public void initView() {
        lnvSize = (LinearLayout) findViewById(R.id.lnvSize);
        lnvFeedBack = (LinearLayout) findViewById(R.id.lnvFeedBack);
        txtMaxFrame = (TextView) findViewById(R.id.txtMaxFrame);
        lnvApp = (LinearLayout) findViewById(R.id.lnvApp);
        lnvVersion = (LinearLayout) findViewById(R.id.lnvVersion);
        lnvWebsite = (LinearLayout) findViewById(R.id.lnvWebsite);
        lnvShare = (LinearLayout) findViewById(R.id.lnvShare);
        lnvLangue = (LinearLayout) findViewById(R.id.lnvLangue);
        txtMaxSize = (TextView) findViewById(R.id.txtMaxSize);
        //txtFolder = (TextView) findViewById(R.id.txtFolder);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        seekBarFrame = (SeekBar) findViewById(R.id.seekBarFrame);

       // applicationManager.displayBanner((Activity) mContext);

        adView = findViewById(R.id.adView);
        AdRequest adRequestBottom = new AdRequest
                .Builder()
                .build();
        adView.loadAd(adRequestBottom);

    }

    @Override
    public void initData() {


        PackageInfo pinfo;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pinfo.versionName;
            txtVersion.setText("Current Version " + versionName);
            //ET2.setText(versionNumber);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    @Override
    public void initClickListenr() {


        lnvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               applicationManager.showIntrestial();
                Intent it0 = new Intent(Intent.ACTION_VIEW);
                it0.setData(Uri.parse("https://twitter.com/lionel_nkeoua"));
                try{
                    startActivity(it0);

                } catch (Exception ex){
                    Toast.makeText(SettingActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lnvFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                  applicationManager.showIntrestial();

                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getPackageName())));
            }
        });

        lnvApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applicationManager.showIntrestial();

                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/dev?id=7454179469226486234" )));
            }
        });


        lnvLangue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

           applicationManager.showIntrestial();

                Intent itP = new Intent(Intent.ACTION_VIEW);
                itP.setData(Uri.parse("http://nkeoualionel.github.io"));
                startActivity(itP);
            }
        });

      /*  lnvmAxframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          applicationManager.showIntrestial();
            }
        });*/


        lnvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               applicationManager.showIntrestial();


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + mContext.getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });
        lnvFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }


            }
        });


        lnvVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        applicationManager.showIntrestial();
                new UpdateRunnable((Activity) mContext, new Handler()).force(true).start();
            }
        });


        lnvSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}
