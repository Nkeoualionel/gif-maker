package com.lionelnkeoua.gifmaker.myFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.Utils.BaseFragment;

import java.io.File;

/**
 * Created by Chirag on 06-01-2018.
 */
@SuppressLint("ValidFragment")
public class EditImagesFragment extends BaseFragment {


    String path;
    ImageView imgMedia;
    public  LinearLayout lnvFrame;

    public EditImagesFragment(String path) {
        this.path = path;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_frame_fragment, container, false);
        initViews(view);
        initData();
        initClickListner();

        return view;

    }

    @Override
    public void initViews(View view) {
        imgMedia = (ImageView) view.findViewById(R.id.imgMedia);
        lnvFrame = view.findViewById(R.id.lnvFrame);
    }

    @Override
    public void initData() {
        Glide.with(mContext).load(new File(path)).asBitmap().placeholder(R.drawable.rect_load).error(R.drawable.rect_load).into(imgMedia);
    }

    @Override
    public void initClickListner() {

    }
}
