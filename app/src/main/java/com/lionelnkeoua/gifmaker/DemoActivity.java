package com.lionelnkeoua.gifmaker;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lionelnkeoua.gifmaker.Utils.BaseActivity;


/**
 * Created by Chirag on 05-01-2018.
 */

public class DemoActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initView();
        initData();
        initClickListenr();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListenr() {

    }
}
