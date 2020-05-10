package com.lionelnkeoua.gifmaker.myFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lionelnkeoua.gifmaker.Model.Picture;
import com.lionelnkeoua.gifmaker.R;
import com.lionelnkeoua.gifmaker.Utils.BaseFragment;

import java.io.File;


/**
 * Created by Chirag on 23-04-2017.
 */
public class MyGifsFragment extends BaseFragment {

    Picture picture;
    ImageView imgSelectedPIC;
    int position;
    private AdView adView;

    public MyGifsFragment(Picture picture, int position) {
        this.picture = picture;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_gif_fragment,container,false);
        initViews(view);
        initData();
        initClickListner();

        adView = view.findViewById(R.id.adView);
        AdRequest adRequestBottom = new AdRequest
                .Builder()
                .addTestDevice("D33F1EF4FF694C7DBB3938D016ABA4F5")
                .build();
        adView.loadAd(adRequestBottom);

        return view;

    }



    @Override
    public void initViews(View view) {
        imgSelectedPIC = (ImageView) view.findViewById(R.id.imgSelectedPIC);

    }

    @Override
    public void initData() {

        File file = new File(picture.getPath());
        Glide.clear(imgSelectedPIC);
        Glide.with(mContext).load(file).asBitmap().placeholder(R.drawable.image_loader).override(250,250).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgSelectedPIC);


    }

    @Override
    public void initClickListner() {

        File file = new File(picture.getPath());
        Glide.clear(imgSelectedPIC);
        Glide.with(mContext).load(file).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imgSelectedPIC);

    }


}
