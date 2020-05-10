package com.lionelnkeoua.gifmaker.Utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lionelnkeoua.gifmaker.R;


/**
 * Created by Chirag on 23-04-2017.
 */
public class DemoFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,container,false);

        initViews(view);
        initData();
        initClickListner();

        return view;

    }



    @Override
    public void initViews(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListner() {

    }
}
