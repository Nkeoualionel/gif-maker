package com.lionelnkeoua.gifmaker.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


/**
 * Created by mahadev on 6/22/16.
 */
public abstract class BaseFragment extends Fragment {


    //Abstract
    public abstract void initViews(View view);
    public abstract void initData();
    public abstract void initClickListner();



    //Methods
    public BaseActivity baseActivity;
    public Context mContext;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        logDebug("Fragment",mContext.getClass().getSimpleName());
        baseActivity = (BaseActivity) getActivity();

    }


    public void logDebug(String tag, String message) {
        Log.d(mContext.getClass().getSimpleName(), "TAG " + tag + " - " + message);
    }

    public void logError(String tag, String message) {
        Log.e(mContext.getClass().getSimpleName(), "TAG " + tag + " - " + message);
    }


    public void toast(String message,int duration){
        // 0 - Sort
        // 1 - Long
        Toast.makeText(mContext, message,duration).show();
    }


}
