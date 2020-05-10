package com.lionelnkeoua.gifmaker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lionelnkeoua.gifmaker.Utils.BaseActivity;
import com.madx.updatechecker.lib.UpdateRunnable;

/**
 * Created by Chirag on 09-04-2017.
 */
public class SplashScreenActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activty);


        initView();
        initData();
        initClickListenr();
    }


    @Override
    public void initView() {
        new UpdateRunnable(this, new Handler()).start();

    }

    @Override
    public void initData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 2000);
    }

    @Override
    public void initClickListenr() {

    }


}
